package jp.langstack.interfaces.response;

import java.math.BigInteger;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    // DBからの戻り値の型「java.sql.Date」に合わせている
    private Date postDate;
    // DBからの戻り値の型「java.math.BigInteger」に合わせている
    private BigInteger cards;

    public Activity(Object[] objects) {
        this((Date)objects[0], (BigInteger)objects[1]);
    }
}