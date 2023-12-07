package com.mapfre.tron.api.cmn.ply.gni.dl;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniP;

/**
 * The PlyGniQry repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 dic. 2020 - 15:32:50
 *
 */
public interface ISrPlyGniQryDAO {

    /**
     * Query policy general information valid for a date.
     *
     * @author arquitectura - pvraul1
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param plyVal    -> Policy value
     * @param aplVal    -> Application value
     * @param qryDat    -> Query date
     * @param exlPrvVal    -> Exclude provisional values
     * @return OPlyGniP -> General information response
     */
    OPlyGniP opvDat(BigDecimal cmpVal, String plyVal, BigDecimal aplVal, Date qryDat, String getNamAll,
            String reaOrg, String lngVal, String exlPrvVal);

}
