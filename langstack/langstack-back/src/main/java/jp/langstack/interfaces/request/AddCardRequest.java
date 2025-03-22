package jp.langstack.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCardRequest {
    private String id;
    private String genreId;
    private String genreName;
    private String title;
    private String content;
    private String imageDataUrl;
}