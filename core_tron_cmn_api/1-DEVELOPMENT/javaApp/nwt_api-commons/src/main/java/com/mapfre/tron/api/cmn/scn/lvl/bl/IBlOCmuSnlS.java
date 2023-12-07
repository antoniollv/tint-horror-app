package com.mapfre.tron.api.cmn.scn.lvl.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;
import com.mapfre.tron.api.cmn.scn.lvl.dl.OCmuSnlSPK;

/**
 * The OCmuSnl business interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 21 Oct 2021 - 12:20:35
 *
 */
public interface IBlOCmuSnlS {

    /**
     * Query Second Level List.
     *
     * @param oCmuSnlSPK    -> Params property
     * @return              -> The second levels list
     */
    List<OCmuSnlS> scnLvlQry(OCmuSnlSPK oCmuSnlSPK);

    /**
     * Query Second Level by PK.
     *
     * @param oCmuSnlSPK    -> Params property
     * @return              -> The second level
     */
    OCmuSnlS get(OCmuSnlSPK build);

}
