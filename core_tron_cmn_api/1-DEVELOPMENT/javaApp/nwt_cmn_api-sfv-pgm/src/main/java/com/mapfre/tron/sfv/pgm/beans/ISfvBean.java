package com.mapfre.tron.sfv.pgm.beans;

import java.math.BigDecimal;
import java.util.Map;

import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

/**
 * The step flow validation Bean interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 11 May 2023 - 15:35:50
 *
 */
public interface ISfvBean {

    SfvOut execute(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params);
}
