package com.gisdev.library.util;

import com.gisdev.library.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.function.Supplier;

public class DisplayHelper {

    public static String formatDate(LocalDate localDate, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDate);
    }

    public static String formatDate(LocalDate localDate) {
        return formatDate(localDate, "dd-MM-yyyy");
    }

    public static String formatDateWithSlashes(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(localDate);
    }

    public static String formatDateWithDots(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return formatter.format(localDate);
    }

    public static String formatDateWithoutYear(LocalDate localDate) {
        return formatDate(localDate, "dd-MM");
    }

    public static String formatDateFromDateTime(LocalDateTime localDatetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formatter.format(localDatetime);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return formatter.format(dateTime);
    }

    public static String formatDateTimeForFiles(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH_mm");
        return formatter.format(dateTime);
    }

    public static String numberFormatForValues(Double value) {
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(value);
    }

    public static Double doubleFormaterWithoutDecimals(Double number) {
        NumberFormat formatter = new DecimalFormat("#0.");
        String afterSecondFormat = formatter.format(number);
        return Double.parseDouble(afterSecondFormat);
    }

    public static Double doubleFormaterWithOneDecimal(Double number) {
        NumberFormat formatter = new DecimalFormat("#0.0");
        String afterSecondFormat = formatter.format(number);
        return Double.parseDouble(afterSecondFormat);
    }

    public static Double formatNumbersWithTwoDecimal(Double value) {
        String format = (new DecimalFormat("##.##")).format(value);
        return Double.parseDouble(format);
    }

    public static String doubleDisplayerWith3Decimals(double nr2Display) {
        DecimalFormat displayFormat = new DecimalFormat("#0.000");
        return displayFormat.format(nr2Display);
    }


    public static String doubleDisplayerWith2Decimals(double nr2Display) {
        DecimalFormat displayFromat = new DecimalFormat("#0.00");
        return displayFromat.format(nr2Display);
    }

    public static LocalDateTime convertFromStringToLocalDateTime(String fieldName, String dateString, boolean required) {
        if (required && StringUtils.isEmpty(dateString)) {
            throw new BadRequestException(String.format("Field %s is required!", fieldName));
        }

        try {
            if (StringUtils.isEmpty(dateString)) {
                return null;
            }
            return LocalDateTime.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new BadRequestException(String.format("Field %s is not valid!", fieldName));
        }
    }

    public static LocalDate convertFromStringToLocalDate(String fieldName, String dateString, boolean required) {
        if (required && StringUtils.isEmpty(dateString)) {
            throw new BadRequestException(String.format("Field %s is required!", fieldName));
        }

        try {
            if (StringUtils.isEmpty(dateString)) {
                return null;
            }
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new BadRequestException(String.format("Field %s is not valid!", fieldName));
        }
    }

    public static <E extends Enum<E>> E convertFromStringToEnum(String source, Class<E> classType) {
        if (source.isEmpty() || source.isBlank()) {
            throw new BadRequestException(classType.getSimpleName() + " is required!");
        }
        try {
            return Enum.valueOf(classType, source.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("This value '" + source + "' is not included in the constant values of ENUM " + classType.getSimpleName() + " !");
        }
    }

    public static int getDaysBetweenInclusive(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public static double round2(double value, int places) {

        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public static Double doubleFormattedWith1Decimal(Double value) {
        return round2(value, 1);
    }

    public static boolean isCurrentMonth(Integer month, Integer year) {
        LocalDate today = LocalDate.now();
        return month.equals(today.getMonth().getValue()) && year.equals(today.getYear());
    }

    public static String integerOrDouble(Double value) {
        String newValue;
        if (value % 1 == 0) {
            newValue = String.valueOf(value.intValue());
        } else
            newValue = value.toString();
        return newValue;
    }

    public static String formatNumberWithApostrophe(Double num) {
        NumberFormat formatter = null;
        if (num % 1.0 == 0.0) {
            formatter = new DecimalFormat("#,##0");
            return formatter.format((long) num.intValue()).replaceAll(",", "'");
        } else {
            formatter = new DecimalFormat("#,##0.00");
            return formatter.format(num).replaceAll(",", "'");
        }
    }

    public static String integerOrDoubleFormatted(Double value) {
        String newValue;
        if (value % 1.0 == 0.0) {
            newValue = String.valueOf(value.intValue());
        } else {
            newValue = doubleDisplayerWith1Decimal(value);
        }

        return newValue;
    }

    public static String doubleDisplayerWith1Decimal(double nr2Display) {
        DecimalFormat displayFormat = new DecimalFormat("#0.0");
        return displayFormat.format(nr2Display);
    }

    public static int getBusinessDaysBetween(LocalDate start, LocalDate end) {
        int count;
        for (count = 0; end.isAfter(start) || end.isEqual(start); start = start.plusDays(1L)) {
            if (!start.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !start.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                ++count;
            }
        }

        return count;
    }

    public static String getDayOfTheWeek(LocalDate localDate) {
        try {
            String dayOfTheWeek = localDate.getDayOfWeek().toString();
            String var10000 = dayOfTheWeek.substring(0, 1).toUpperCase();
            return var10000 + dayOfTheWeek.substring(1).toLowerCase();
        } catch (NullPointerException var2) {
            return "";
        }
    }

    public static String getMonthAndDay(LocalDate localDate) {
        try {
            String month = localDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
            String day = "" + localDate.getDayOfMonth();
            return month + " " + day;
        } catch (NullPointerException var3) {
            return "";
        }
    }


    public static Double getNonNullValue(Supplier<Double> function) {
        Double result = (Double) function.get();
        return result != null ? result : 0.0;
    }

    public static String getNonNullValueString(Supplier<String> function) {
        String result = (String) function.get();
        return result != null ? result : "";
    }
}