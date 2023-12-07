package com.mapfre.tron.api.cmn.trn.ntf.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;
import com.mapfre.tron.api.cmn.trn.ntf.bl.IBlTrnNtf;
import com.mapfre.tron.api.cmn.trn.ntf.dl.IDlTrnNtfDao;
import com.mapfre.tron.api.cmn.trn.ntf.dl.OTrnNtfPK;

/**
 * The TrnNtf service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 12:50:27
 *
 */
@Service
public class BlTrnNtfImpl implements IBlTrnNtf {

    /** The TrnNtf repository.*/
    @Autowired
    protected IDlTrnNtfDao iDlTrnNtfDao;

    /**
     * Obtiene la informacion de OTrnNtf.
     *
     * @param oTrnNtfPK -> La clave primaria
     * @return          -> La información de OTrnNtf
     */
    @Override
    public OTrnNtfS get(final OTrnNtfPK oTrnNtfPK) {
        return iDlTrnNtfDao.get(oTrnNtfPK);
    }

    /**
     * Obtiene la descripción oprIdnNam.
     *
     * @param oTrnNtfPK -> La clave primaria
     * @return          -> La descripción oprIdnNam
     */
    @Override
    public String getOprIdnNam(final OTrnNtfPK oTrnNtfPK) {
        return iDlTrnNtfDao.getOprIdnNam(oTrnNtfPK);
    }
    
    /**
     * Obtiene la descripción encVal.
     *
     * @param oTrnNtfPK -> La clave primaria
     * @return          -> La descripción oprIdnNam
     */
    @Override
    public String getEncNam(final OTrnNtfPK oTrnNtfPK) {
        return iDlTrnNtfDao.getEncNam(oTrnNtfPK);
    }

}
