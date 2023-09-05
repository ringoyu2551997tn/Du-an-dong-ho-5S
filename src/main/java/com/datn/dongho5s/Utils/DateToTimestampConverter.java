package com.datn.dongho5s.Utils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import java.sql.Timestamp;
import java.util.Date;
@Component
public class DateToTimestampConverter implements Converter<Date, Timestamp> {
    @Override
    public Timestamp convert(Date source) {
        return source == null ? null : new Timestamp(source.getTime());
    }
}
