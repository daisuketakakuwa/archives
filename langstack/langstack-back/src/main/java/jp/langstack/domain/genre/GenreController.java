package jp.langstack.domain.genre;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.langstack.interfaces.response.GenreResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("genre")
@CrossOrigin
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/allGenres")
    public List<GenreResponse> allGenres() {
        return genreService.getAllGenres();
    }
    
}
