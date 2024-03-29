package com.flavio.android.megasena.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
    private static final String diaMesAnoFormat = "dd/MM/yyyy";
    private static final String diaMesAnoHoraMinutoSegundoFormat = "dd/MM/yyyy HH:mm:ss";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date toDataBr(String data) throws ParseException {
        return new SimpleDateFormat(diaMesAnoFormat).parse(data);
    }

    public static Date toDataHoraBr(String data) throws ParseException {
        return new SimpleDateFormat(diaMesAnoHoraMinutoSegundoFormat).parse(data);
    }

    public static String toString(Date date){
        return simpleDateFormat.format(date);
    }
}
