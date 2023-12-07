package com.mapfre.tron.api.cmn.trn.fil.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.trn.fil.bl.IBlTrnFilTyp;
import com.mapfre.tron.api.cmn.trn.fil.dl.IDlTrnFilTypDao;

/**
 * The service implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:50:27
 *
 */
@Service
public class BlTrnFilTypImpl implements IBlTrnFilTyp {

    /** The repository.*/
    @Autowired
    protected IDlTrnFilTypDao iDlTrnFilTypDao;


    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return          -> La descripción
     */
    @Override
    public String getFilTypNam(final Map<String, Object> map) {
        return iDlTrnFilTypDao.getFilTypNam(map);
    }

}
