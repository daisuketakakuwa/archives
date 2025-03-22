package jp.langstack.domain.card;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.langstack.interfaces.response.Activity;

@Repository
public interface CardRepository extends CrudRepository<CardEntity, String> {

    // MEMO："fromData"の型は「LocalDate」のままでいいぽい
    @Query(value = 
            "  SELECT post_date, COUNT(post_date)" +
            "    FROM card c " +
            "   WHERE post_date >= :fromDate " +
            "GROUP BY c.post_date", nativeQuery = true) 
    public List<Object[]> findTheLastFiveDaysActivity(@Param("fromDate") LocalDate fromDate);

    // MEMO：DBからの戻り値が「LocalDate」ではなく「java.sql.Date」だったのでそれに合わせた。
    default List<Activity> getTheLastFiveDaysActivity(LocalDate fromDate) {
        List<Activity> activities = findTheLastFiveDaysActivity(fromDate).stream().map(Activity::new).collect(Collectors.toList());
        // 存在しなかった日付は「日付 | 0」でインスタンス化してあげる
        for (int i = 0; i < 5; i++) {
            Date targetDate = Date.valueOf(fromDate.plusDays(i));
            Optional<Activity> activity = activities.stream().filter(a -> a.getPostDate().equals(targetDate)).findAny();
            if (activity.isEmpty()) {
                activities.add(new Activity(targetDate, BigInteger.valueOf(0)));
            }
        } 
        return activities;
    }

    @Query(value = 
            "SELECT DISTINCT card FROM CardEntity card " +
            "LEFT JOIN FETCH card.genre " +
            "WHERE card.postDate >= :fromDate " +
            "ORDER BY card.postDate DESC"
    )
    public List<CardEntity> findTheLastWeekCards(@Param("fromDate") LocalDate fromDate);

    @Query(value = 
            "SELECT DISTINCT card FROM CardEntity card " +
            "LEFT JOIN FETCH card.genre " +
            "WHERE card.genre.id = :genreId " +
            "ORDER BY card.postDate DESC"
    )    
    public List<CardEntity> findByGenreId(@Param("genreId") String genreId);

}
