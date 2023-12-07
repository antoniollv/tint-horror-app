package com.mapfre.tron.api.cmn.adr.adr.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.adr.adr.bl.IBlAdrAdr;
import com.mapfre.tron.api.cmn.adr.adr.dl.IDlAdrAdrDao;

/**
 * The service implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:50:27
 *
 */
@Service
public class BlAdrAdrImpl implements IBlAdrAdr {

    /** The repository. */
    @Autowired
    protected IDlAdrAdrDao iDlAdrAdrDao;

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    @Override
    public String getFldNamVal(Map<String, Object> map) {

	return iDlAdrAdrDao.getFldNamVal(map);
    }


}
