package com.mapfre.tron.api.cmn.scn.lvl.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;
import com.mapfre.tron.api.cmn.scn.lvl.bl.IBlOCmuSnlS;
import com.mapfre.tron.api.cmn.scn.lvl.dl.OCmuSnlSDao;
import com.mapfre.tron.api.cmn.scn.lvl.dl.OCmuSnlSPK;

/**
 * The OCmuSnlS business service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 21 Oct 2021 - 12:35:52
 *
 */
@Service
public class BlOCmuSnlSImpl implements IBlOCmuSnlS {

    /** The OCmuSnlS repository.*/
    @Autowired
    protected OCmuSnlSDao oCmuSnlSDao;

    /**
     * Query Second Level List.
     *
     * @param oCmuSnlSPK    -> Params property
     * @return              -> The second levels list
     */
    @Override
    public List<OCmuSnlS> scnLvlQry(final OCmuSnlSPK oCmuSnlSPK) {
        return oCmuSnlSDao.scnLvlQry(oCmuSnlSPK);
    }

    /**
     * Query Second Level by PK.
     *
     * @param oCmuSnlSPK    -> Params property
     * @return              -> The second level
     */
    @Override
    public OCmuSnlS get(final OCmuSnlSPK oCmuSnlSPK) {
        return oCmuSnlSDao.get(oCmuSnlSPK);
    }

}
