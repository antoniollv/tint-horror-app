package com.mapfre.tron.api.cmn.cmu.thl.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlSPK;

/**
 * The CmuThlS business service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Oct 2021 - 16:32:10
 *
 */
public interface IBlCmuThlS {

    /**
     * Query Third Level List.
     *
     * @param oCmuThlSPK -> Params property
     * @return           -> The third levels list
     */
    List<OCmuThlS> thdLvlQry(OCmuThlSPK oCmuThlSPK);

    /**
     * Query Third Level by PK.
     *
     * @param oCmuThlSPK -> Params property
     * @return           -> The third level
     */
    OCmuThlS get(OCmuThlSPK build);

}
