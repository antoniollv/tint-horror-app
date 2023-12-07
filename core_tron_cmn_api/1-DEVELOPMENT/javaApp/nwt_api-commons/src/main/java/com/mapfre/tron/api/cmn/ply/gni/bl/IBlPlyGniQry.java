package com.mapfre.tron.api.cmn.ply.gni.bl;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniP;

/**
 * The PlyGniQry commons business service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 dic. 2020 - 12:07:53
 *
 */
public interface IBlPlyGniQry {

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
    OPlyGniP opvDat(BigDecimal cmpVal, String usrVal, String lngVal, String plyVal, BigDecimal aplVal, Date qryDat, String exlPrvVal);
    
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
     * @param cache	-> Cache
     * @return OPlyGniP -> General information response
     */
    OPlyGniP opvDatBool(BigDecimal cmpVal, String usrVal, String lngVal, String plyVal, BigDecimal aplVal, Date qryDat, boolean cache);

}
