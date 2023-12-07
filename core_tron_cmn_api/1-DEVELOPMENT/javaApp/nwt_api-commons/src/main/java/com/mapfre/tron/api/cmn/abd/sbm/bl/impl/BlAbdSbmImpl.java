package com.mapfre.tron.api.cmn.abd.sbm.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.abd.sbm.bl.IBlAbdSbm;
import com.mapfre.tron.api.cmn.abd.sbm.dl.IDlAbdSbmDao;
import com.mapfre.tron.api.cmn.abd.sbm.dl.impl.VhtNamPK;

/**
 * The service implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:50:27
 *
 */
@Service
public class BlAbdSbmImpl implements IBlAbdSbm {

    /** The repository. */
    @Autowired
    protected IDlAbdSbmDao iDlAbdSbmDao;

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    @Override
    public String getMakNam(Map<String, Object> map) {

	return iDlAbdSbmDao.getMakNam(map);
    }

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    @Override
    public String getMdlNam(Map<String, Object> map) {

	return iDlAbdSbmDao.getMdlNam(map);
    }

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    @Override
    public String getVhtNam(VhtNamPK map) {

	return iDlAbdSbmDao.getVhtNam(map);
    }

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    @Override
    public String getTctNam(Map<String, Object> map) {

	return iDlAbdSbmDao.getTctNam(map);
    }

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    @Override
    public String getVhcCtgNam(Map<String, Object> map) {

	return iDlAbdSbmDao.getVhcCtgNam(map);
    }

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    @Override
    public String getVhcCrbNam(Map<String, Object> map) {

	return iDlAbdSbmDao.getVhcCrbNam(map);
    }

    /**
     * Obtiene la descripcion SbmNam.
     *
     * @param map -> El mapa de parametros
     * @return    -> La descripcion sbmNam
     */
    @Override
    public String getSbmNam(final Map<String, Object> map) {
        return iDlAbdSbmDao.getSbmNam(map);
    }

}
