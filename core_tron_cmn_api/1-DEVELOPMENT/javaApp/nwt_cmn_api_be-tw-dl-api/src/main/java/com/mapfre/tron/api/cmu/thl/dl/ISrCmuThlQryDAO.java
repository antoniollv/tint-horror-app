package com.mapfre.tron.api.cmu.thl.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlP;

/**
 * The CmuThlQry repository.
 *
 * @author USOZALO
 * @since 1.8
 * @version 04 jun. 2020 13:08:36
 *
 */
public interface ISrCmuThlQryDAO {

    OCmuThlP get(BigDecimal cmpVal, String lngVal, String usrVal, BigDecimal thrLvlVal);

}
