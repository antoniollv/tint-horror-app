package com.mapfre.tron.api.cmn.cmu.fsl.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmu.fsl.bo.OCmuFslS;
import com.mapfre.tron.api.cmn.cmu.fsl.bl.IBlCmuFslS;
import com.mapfre.tron.api.cmn.cmu.fsl.dl.OCmuFslSDao;
import com.mapfre.tron.api.cmn.cmu.fsl.dl.OCmuFslSPK;

/**
 * The Cmu first level service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 Oct 2021 - 16:22:42
 *
 */
@Service
public class BlCmuFslSImpl implements IBlCmuFslS {

    /** The Cmu first level repository.*/
    @Autowired
    protected OCmuFslSDao oCmuFslSDao;

    /**
     * Query First Level List.
     *
     * @param oCmuFslSPK    -> Params property
     * @return              -> The first level list
     */
    @Override
    public List<OCmuFslS> fstLvlQry(OCmuFslSPK oCmuFslSPK) {
        return oCmuFslSDao.fstLvlQry(oCmuFslSPK);
    }

    /**
     * Query First Level List.
     *
     * @param oCmuFslSPK    -> Params property
     * @return              -> The first level by PK
     */
    @Override
    public OCmuFslS get(OCmuFslSPK oCmuFslSPK) {
        return oCmuFslSDao.get(oCmuFslSPK);
    }

}
