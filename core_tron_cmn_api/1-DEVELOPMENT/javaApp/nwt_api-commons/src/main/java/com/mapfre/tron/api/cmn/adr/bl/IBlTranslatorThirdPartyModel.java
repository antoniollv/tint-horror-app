package com.mapfre.tron.api.cmn.adr.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;
import com.mapfre.nwt.trn.thp.pcm.bo.OThpPcmS;

/**
 * The translator third party model interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 15 Feb 2022 - 08:03:23
 *
 */
public interface IBlTranslatorThirdPartyModel {

    /**
     * Get the old model adress from the new model.
     *
     * @param oThpAdrST -> The new model address list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The old address model list
     */
    List<OThpAdrS> getAdrOldModelFromNewModel (List<OThpAdrS> oThpAdrST, String usrVal, String 
            lngVal, BigDecimal cmpVal);

    /**
     * Get the new model adress from the old model.
     *
     * @param oThpAdrST -> The old model address list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The new address model list
     */
    List<OThpAdrS> getAdrNewModelFromOldModel (List<OThpAdrS> oThpAdrST, String usrVal, String 
            lngVal, BigDecimal cmpVal);

    /**
     * Get the old model contact from the new one.
     *
     * @param oThpCntST -> The old model contact list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The old model contact list
     */
    List<OThpCntS> getCntOldModelFromNewModel(List<OThpCntS> oThpCntST, String usrVal, String lngVal,
            BigDecimal cmpVal);

    /**
     * Get the new model contact from the old one.
     *
     * @param oThpCntST -> The old model contact list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The new model contact list
     */
    List<OThpCntS> getCntNewModelFromOldModel(List<OThpCntS> oThpCntST, String usrVal, String lngVal,
            BigDecimal cmpVal);

    /**
     * Get the old model payments methods from the new one.
     *
     * @param OThpPcmST -> The new payments methods list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The old payments methods list
     */
    List<OThpPcmS> getPcmOldModelFromNewModel(List<OThpPcmS> oThpPcmST, String usrVal, String lngVal,
            BigDecimal cmpVal);

    /**
     * Get the new model payments methods from the old one.
     *
     * @param OThpPcmST -> The old payments methods list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The new payments methods list
     */
    List<OThpPcmS> getPcmNewModelFromOldModel(List<OThpPcmS> oThpPcmST, String usrVal, String lngVal,
            BigDecimal cmpVal);

}
