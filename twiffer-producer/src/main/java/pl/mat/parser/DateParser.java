package pl.mat.parser;

import java.time.OffsetDateTime;
import java.util.Date;


public class DateParser {

    public static Date parseFromString(String stringDate) {
        return  Date.from( OffsetDateTime.parse( stringDate ).toInstant());
    }

}
