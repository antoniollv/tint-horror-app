package com.mapfre.tron.api.cmn.cmu.fsl.dl;

import java.util.List;

import com.mapfre.nwt.trn.cmu.fsl.bo.OCmuFslS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The OCmuFslS repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 Oct 2021 - 16:17:47
 *
 */
public interface OCmuFslSDao extends NewtronDao<OCmuFslS, OCmuFslSPK> {

    /**
     * Query First Level List.
     *
     * @param oCmuFslSPK    -> Params property
     * @return              -> The first level list
     */
    List<OCmuFslS> fstLvlQry(OCmuFslSPK oCmuFslSPK);

}
