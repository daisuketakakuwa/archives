package jp.langstack.interfaces.response;

import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InitResponse {
 
    // postDateが最新5つのカード(CardService)
    private List<CardResponse> theLastFiveCards;
    // 今日から5日前までの投稿履歴(CardService)
    private Set<Activity> theLastFiveDaysActivity;
    // OK GenreService
    private List<GenreResponse> allGenres;
    // CardService
    private int allCardCount;

}
