package com.mapfre.tron.api.cmn.cmu.fsl.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmu.fsl.bo.OCmuFslS;
import com.mapfre.tron.api.cmn.cmu.fsl.dl.OCmuFslSPK;

/**
 * The Cmu first level.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 Oct 2021 - 14:00:39
 *
 */
public interface IBlCmuFslS {

    /**
     * Query First Level List.
     *
     * @param oCmuFslSPK    -> Params property
     * @return              -> The first level list
     */
    List<OCmuFslS> fstLvlQry(OCmuFslSPK oCmuFslSPK);

    /**
     * Query First Level List.
     *
     * @param oCmuFslSPK    -> Params property
     * @return              -> The first level by PK
     */
    OCmuFslS get(OCmuFslSPK oCmuFslSPK);

}
