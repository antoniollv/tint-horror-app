package com.mapfre.tron.api.cmn.scn.lvl.dl;

import java.util.List;

import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The OCmuSnlS respository interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 21 Oct 2021 - 12:41:54
 *
 */
public interface OCmuSnlSDao extends NewtronDao<OCmuSnlS, OCmuSnlSPK> {

    /**
     * Query Second Level List.
     *
     * @param oCmuSnlSPK    -> Params property
     * @return              -> The second levels list
     */
    List<OCmuSnlS> scnLvlQry(OCmuSnlSPK oCmuSnlSPK);

}
