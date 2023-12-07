package com.mapfre.tron.api.cmn.cmu.thl.dl;

import java.util.List;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The OCmuThlS repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Oct 2021 - 15:15:32
 *
 */
public interface OCmuThlSDao extends NewtronDao<OCmuThlS, OCmuThlSPK> {

    /**
     * Query Third Level List.
     *
     * @param oCmuThlSPK -> Params property
     * @return           -> The third levels list
     */
    List<OCmuThlS> getThirdLevels(OCmuThlSPK oCmuThlSPK);

}
