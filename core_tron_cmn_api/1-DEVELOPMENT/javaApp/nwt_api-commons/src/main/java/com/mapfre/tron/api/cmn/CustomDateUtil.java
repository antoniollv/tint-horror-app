package com.mapfre.tron.api.cmn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * The custom date format utility.
 *
 * @author pvraul1
 * @since 1.8
 * @version 14 nov. 2019 14:45:58
 *
 */
@Slf4j
public final class CustomDateUtil {

    /** reate a new instance of the class.*/
    private CustomDateUtil() {
        super();
    }

    /**
     * Elimina la hora, minutos y segundos de una fecha dada.
     *
     * @param --> pmDate la fecha a truncar.
     * @return -> la fecha truncada
     */
    public static Date getDateWithoutTime(Date pmDate) {
        final SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

        if (pmDate != null) {
            try {
                return formater.parse(formater.format(pmDate));
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }

        return pmDate;
    }

}