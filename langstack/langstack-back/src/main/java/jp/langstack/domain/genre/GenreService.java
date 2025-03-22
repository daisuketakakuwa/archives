package jp.langstack.domain.genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import jp.langstack.interfaces.response.GenreResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepo;

    public List<GenreResponse> getAllGenres() {
        List<GenreEntity> genreEntities = StreamSupport.stream(genreRepo.findAll().spliterator(),false).collect(Collectors.toList());
        // TODO：ModelMapper・TypeReferenceらへんを使う
        List<GenreResponse> genreResponses = new ArrayList<>();
        genreEntities.stream().forEach(entity -> {
            genreResponses.add(new GenreResponse(entity.getId(),entity.getName()));
        });
        return genreResponses;
    }

    public Optional<GenreEntity> getGenreByName(String name) {
        return genreRepo.findByName(name);
    }

    public GenreEntity addGenre(String id, String name) {
        GenreEntity genre = new GenreEntity();
        genre.setId(id);
        genre.setName(name);
        return genreRepo.save(genre);
    }

    public void deleteGenre(String id) {
        genreRepo.deleteById(id);
    }
    
}
