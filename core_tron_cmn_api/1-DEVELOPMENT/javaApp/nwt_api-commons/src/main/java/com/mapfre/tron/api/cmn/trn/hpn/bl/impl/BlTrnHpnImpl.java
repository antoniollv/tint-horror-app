package com.mapfre.tron.api.cmn.trn.hpn.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.trn.hpn.bl.IBlTrnHpn;
import com.mapfre.tron.api.cmn.trn.hpn.dl.IDlTrnHpnDao;

/**
 * The service implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:50:27
 *
 */
@Service
public class BlTrnHpnImpl implements IBlTrnHpn {

    /** The repository.*/
    @Autowired
    protected IDlTrnHpnDao iDlTrnHpnDao;


    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return          -> La descripción
     */
    @Override
    public String getHpnNam(final Map<String, Object> map) {
        return iDlTrnHpnDao.getHpnNam(map);
    }

}
