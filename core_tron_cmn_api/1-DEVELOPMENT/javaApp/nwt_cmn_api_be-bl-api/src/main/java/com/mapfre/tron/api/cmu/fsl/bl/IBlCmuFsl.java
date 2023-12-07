package com.mapfre.tron.api.cmu.fsl.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmu.fsl.bo.OCmuFslS;

/**
 * The cmu first level service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 Oct 2021 - 09:25:17
 *
 */
public interface IBlCmuFsl {

    /**
     * Query First Level List.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @return          -> The first level list
     */
    List<OCmuFslS> fstLvlQry(Integer cmpVal, String lngVal, String usrVal);

}
