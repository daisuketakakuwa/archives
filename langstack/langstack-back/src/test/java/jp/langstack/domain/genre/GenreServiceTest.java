package jp.langstack.domain.genre;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.langstack.interfaces.response.GenreResponse;

@ExtendWith(SpringExtension.class)
// @Configurationがついているクラスを自動検出してくれる
@SpringBootTest
public class GenreServiceTest {

    @Autowired GenreService genreService;

    @Test
    public void getAllGenres() {
        List<GenreResponse> genres = genreService.getAllGenres();
        genres.stream().forEach(g -> System.out.println("id：" + g.getId() + ", name：" + g.getName()));
        System.out.println(genres.size());
    }

    @Test
    public void getGenreByName() {
        Optional<GenreEntity> genre = genreService.getGenreByName("GENRE001");
        assertTrue(genre.isPresent());
    }
    
}
