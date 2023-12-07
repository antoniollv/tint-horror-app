package com.mapfre.tron.api.cmn.trn.pcs.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.trn.pcs.bl.IBlTrnPcs;
import com.mapfre.tron.api.cmn.trn.pcs.dl.IDlTrnPcsDao;

/**
 * The service implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:50:27
 *
 */
@Service
public class BlTrnPcsImpl implements IBlTrnPcs {

    /** The repository.*/
    @Autowired
    protected IDlTrnPcsDao iDlTrnPcsDao;


    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return          -> La descripción
     */
    @Override
    public String getPcsNam(final Map<String, Object> map) {
        return iDlTrnPcsDao.getPcsNam(map);
    }

}
