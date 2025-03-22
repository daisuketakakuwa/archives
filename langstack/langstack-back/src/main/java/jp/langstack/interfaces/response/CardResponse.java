package jp.langstack.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {
    private String id;
    private String title;
    private String content;
    private String imageUrl;
    private String postDate;
    private GenreResponse genre;
}
