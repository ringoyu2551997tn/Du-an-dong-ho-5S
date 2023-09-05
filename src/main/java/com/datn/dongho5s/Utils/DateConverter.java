package com.datn.dongho5s.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static final String DISPLAY_FORMAT = "dd-MM-yyyy";
    private static final String DATABASE_FORMAT = "yyyy-MM-dd";

    public static String formatDateForDisplay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DISPLAY_FORMAT);
        return formatter.format(date);
    }

    public static Date parseDateFromDisplay(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat(DISPLAY_FORMAT);
        try {
            return formatter.parse(dateString);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi chuyển đổi ngày tháng từ chuỗi hiển thị.", e);
        }
    }

    public static String formatDateForDatabase(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATABASE_FORMAT);
        return formatter.format(date);
    }

    public static Date parseDateFromDatabase(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATABASE_FORMAT);
        try {
            return formatter.parse(dateString);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi chuyển đổi ngày tháng từ chuỗi cơ sở dữ liệu.", e);
        }
    }
}
