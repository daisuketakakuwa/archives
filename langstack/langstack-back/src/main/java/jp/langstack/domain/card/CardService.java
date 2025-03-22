package jp.langstack.domain.card;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jp.langstack.domain.genre.GenreEntity;
import jp.langstack.domain.genre.GenreService;
import jp.langstack.infra.s3.S3Service;
import jp.langstack.interfaces.response.Activity;
import jp.langstack.interfaces.response.CardResponse;
import jp.langstack.interfaces.response.GenreResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {

    private final EntityManager entityManager;
    private final CardRepository cardRepo;
    private final GenreService genreServ;
    private final S3Service s3Service;

    private static final String QUERY_GET_NEW_CARDS = "SELECT DISTINCT card FROM CardEntity card LEFT JOIN FETCH card.genre ORDER BY card.postDate DESC";

    private static final String QUERY_SEARCH = "SELECT DISTINCT card FROM CardEntity card LEFT JOIN FETCH card.genre WHERE card.title LIKE :keyword OR card.content LIKE :keyword ORDER BY card.postDate DESC";

    // MEMO：JPQLでは直接「LIMIT句」はクエリに書かない。entityManager経由で「setMaxResult()」で指定するのだそう。
    // https://www.baeldung.com/jpa-limit-query-results
    public List<CardResponse> getTheLastFiveCards() {
        List<CardEntity> cardEntities = entityManager.createQuery(QUERY_GET_NEW_CARDS, CardEntity.class)
                .setMaxResults(5).getResultList();
        return convertEntityToResponse(cardEntities);
    }

    // MEMO：「COUNT() × GROUP BY」はNativeQueryで実行してあげる必要がある。
    // https://kumagoro-95.hatenablog.com/entry/2021/01/05/170711
    public Set<Activity> getTheLastFiveDaysActivity() {
        Set<Activity> activities = new TreeSet<>(Comparator.comparing(Activity::getPostDate).reversed());
        activities.addAll(cardRepo.getTheLastFiveDaysActivity(LocalDate.now().minusDays(4)));
        return activities;
    }

    public int getAllCardCount() {
        return (int) cardRepo.count();
    }

    public List<CardResponse> search(String keyword) {
        // TODO：specificationで置き換える
        // TODO：大文字小文字関係なく検索ひっかかるように
        TypedQuery<CardEntity> query = entityManager.createQuery(QUERY_SEARCH, CardEntity.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return convertEntityToResponse(query.getResultList());
    }

    public List<CardResponse> getRecentCards() {
        List<CardEntity> cardEntities = cardRepo.findTheLastWeekCards(LocalDate.now().minusDays(6));
        return convertEntityToResponse(cardEntities);
    }

    // TODO：ModelMapper・TypeReferenceらへん使う
    private List<CardResponse> convertEntityToResponse(List<CardEntity> entities) {
        List<CardResponse> cardResponses = new ArrayList<>();
        entities.stream().forEach(entity -> {
            GenreResponse genre = new GenreResponse(entity.getGenre().getId(), entity.getGenre().getName());
            CardResponse card = new CardResponse(entity.getId(),entity.getTitle(), entity.getContent(), entity.getImageUrl(),
                    entity.getPostDate().toString(), genre);
            cardResponses.add(card);
        });
        return cardResponses;
    } 

    public List<CardResponse> getCardsByGenre(String genreId) {
        List<CardEntity> cardEntities = cardRepo.findByGenreId(genreId);
        return convertEntityToResponse(cardEntities);
    }

    // modelMapperでCardRequest → CardEntityに変換
    public CardEntity addCard(CardEntity card) {
        card.setId(Long.toString(UUID.randomUUID().getMostSignificantBits(), 22));
        card.setPostDate(LocalDate.now());
        // GENRE
        if (StringUtils.isBlank(card.getGenre().getId())) {
            GenreEntity genre = genreServ.getGenreByName(card.getGenre().getName())
                    .orElseGet(() -> genreServ.addGenre(Long.toString(UUID.randomUUID().getMostSignificantBits(), 22),
                            card.getGenre().getName()));
            card.setGenre(genre);
        }
        // IMAGE
        if (StringUtils.isNotBlank(card.getImageUrl())) {
            String extension = getImageExtension(card.getImageUrl());
            String objectUrl = s3Service.uploadObjectIfNotExists(card.getId() + "." + extension, card.getImageUrl().replaceAll("data:image/[a-zA-Z]*;base64,", ""));
            card.setImageUrl(objectUrl);
        }

        return cardRepo.save(card);
    }

    private String getImageExtension(String base64str) {
        final String dataPrefix = "data:image/";
        if (!StringUtils.contains(base64str, dataPrefix)) {
          throw new IllegalArgumentException("NOT IMAGE FILE REQUESTED");
        }
        int endIndex = base64str.indexOf(";base64");
        return base64str.substring(dataPrefix.length(), endIndex);
    }
    
    public CardEntity updateCard(String id, String title, String content) {
        CardEntity card = cardRepo.findById(id).orElseThrow(RuntimeException::new);
        card.setTitle(title);
        card.setContent(content);
        return cardRepo.save(card);
    }

    public void deleteCard(String id) {
        CardEntity card = cardRepo.findById(id).orElseThrow(RuntimeException::new);
        String genreId = card.getGenre().getId();
        boolean deleteGenre = countCardsByGenreId(genreId) == 1;
        s3Service.deleteObjectIfExists(new File(card.getImageUrl()).getName());
        cardRepo.deleteById(id);
        if (deleteGenre) {
            genreServ.deleteGenre(genreId);
        }
    }

    public Integer countCardsByGenreId(String genreId) {
        return cardRepo.findByGenreId(genreId).size();
    }
    

}
