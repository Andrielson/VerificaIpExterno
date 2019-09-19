package tk.andrielson.verificaipexterno.room;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DatetimeToStringConverter {

    /**
     * Formatador de data-hora: 2019-18-09 23:01:23.
     */
    private static final DateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    /**
     * Converte uma String no formato yyyy-MM-dd para Date.
     *
     * @param value a String a ser convertida.
     * @return a data obtida da conversão da String.
     */
    @Nullable
    @TypeConverter
    public static Date dateFromString(String value) {
        try {
            return value == null ? null : formato.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converte um Date em uma String no formato yyyy-MM-dd.
     *
     * @param value o Date a ser convertido.
     * @return a String obtida a partir da conversão.
     */
    @Nullable
    @TypeConverter
    public static String stringFromDate(Date value) {
        return value == null ? null : formato.format(value);
    }
}
