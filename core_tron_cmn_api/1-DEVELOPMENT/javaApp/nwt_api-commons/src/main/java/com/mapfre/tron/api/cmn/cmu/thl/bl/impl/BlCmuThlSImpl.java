package com.mapfre.tron.api.cmn.cmu.thl.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.tron.api.cmn.cmu.thl.bl.IBlCmuThlS;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlSDao;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlSPK;

/**
 * The CmuThlS business service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Oct 2021 - 16:57:19
 *
 */
@Service
public class BlCmuThlSImpl implements IBlCmuThlS {

    /** The OCmuThl repository.*/
    @Autowired
    protected OCmuThlSDao oCmuThlSDao;

    /**
     * Query Third Level List.
     *
     * @param oCmuThlSPK -> Params property
     * @return           -> The third levels list
     */
    @Override
    public List<OCmuThlS> thdLvlQry(OCmuThlSPK oCmuThlSPK) {
        return oCmuThlSDao.getThirdLevels(oCmuThlSPK);
    }

    /**
     * Query Third Level by PK.
     *
     * @param oCmuThlSPK -> Params property
     * @return           -> The third level
     */
    @Override
    public OCmuThlS get(OCmuThlSPK oCmuThlSPK) {
        return oCmuThlSDao.get(oCmuThlSPK);
    }

}
