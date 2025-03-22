package jp.langstack.domain.card;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.langstack.domain.genre.GenreEntity;
import jp.langstack.interfaces.response.Activity;
import jp.langstack.interfaces.response.CardResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CardServiceTest {
    
    @Autowired CardService cardService;

    @Test
    public void getTheLastFiveCards() {
        List<CardResponse> cards = cardService.getTheLastFiveCards();
        assertEquals(5, cards.size());
        cards.stream().forEach(c -> System.out.println("date：" + c.getPostDate() + ", id：" + c.getId()));
    }

    @Test
    public void getTheLastFiveDaysActivity() {
        Set<Activity> activities = cardService.getTheLastFiveDaysActivity();
        assertEquals(5, activities.size());
        activities.stream().forEach(a -> System.out.println("date：" + a.getPostDate() + ", cards：" + a.getCards()));
    }

    @Test
    public void getAllCardCount() {
        int count = cardService.getAllCardCount();
        assertEquals(23, count);
    }

    @Test
    public void search() {
        List<CardResponse> cards = cardService.search("aiueo");
        System.out.println("検索件数：" + cards.size());
        cards.stream().forEach(c -> {
            System.out.println("--------------------------");
            System.out.println("TITLE：" + c.getTitle());
            System.out.println("CONTENT：" + c.getContent());
            System.out.println("POST DATE：" + c.getPostDate());
        });
    }

    @Test
    public void getRecentCards() {
        List<CardResponse> cards = cardService.getRecentCards();
        System.out.println("検索件数：" + cards.size());
        cards.stream().forEach(c -> {
            System.out.println("--------------------------");
            System.out.println("TITLE：" + c.getTitle());
            System.out.println("POST DATE：" + c.getPostDate());
        });

    }
        
    @Test
    public void getCardsByGenre() {
        List<CardResponse> cards = cardService.getCardsByGenre("genreId001");
        assertEquals(23, cards.size());
        System.out.println("検索件数：" + cards.size());
        cards.stream().forEach(c -> {
            System.out.println("--------------------------");
            System.out.println("TITLE：" + c.getTitle());
            System.out.println("GENRE ID" + c.getGenre().getId());
            System.out.println("POST DATE：" + c.getPostDate());
        });
    }

    @Test
    public void addCard() {
        // NEW GENRE
        CardEntity card1 = new CardEntity();
        card1.setTitle("テスト用タイトル1");
        card1.setContent("テスト用コンテント1");
        GenreEntity newGenre = new GenreEntity();
        newGenre.setName("新しいGENRE");
        card1.setGenre(newGenre);
        cardService.addCard(card1);

        // EXIST GENRE
        // NEW GENRE
        CardEntity card2 = new CardEntity();
        card2.setTitle("テスト用タイトル2");
        card2.setContent("テスト用コンテント2");
        GenreEntity existGenre = new GenreEntity();
        existGenre.setName("GENRE001");
        card2.setGenre(existGenre);
        cardService.addCard(card2);

    }

    @Test
    public void countCardsByGenreId() {
        Integer count = cardService.countCardsByGenreId("genreId001");
        System.out.println(count);
    }

}
