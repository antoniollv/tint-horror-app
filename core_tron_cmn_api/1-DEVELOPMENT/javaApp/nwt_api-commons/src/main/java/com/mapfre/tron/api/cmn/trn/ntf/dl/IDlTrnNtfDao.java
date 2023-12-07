package com.mapfre.tron.api.cmn.trn.ntf.dl;

import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The TrnNtf repository interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 12:10:07
 *
 */
public interface IDlTrnNtfDao extends NewtronDao<OTrnNtfS, OTrnNtfPK> {

    /**
     * Obtiene la informacion de OTrnNtf.
     *
     * @param oTrnNtfPK -> La clave primaria
     * @return          -> La información de OTrnNtf
     */
    OTrnNtfS get(OTrnNtfPK oTrnNtfPK);

    /**
     * Obtiene la descripción oprIdnNam.
     *
     * @param oTrnNtfPK -> La clave primaria
     * @return          -> La descripción oprIdnNam
     */
    String getOprIdnNam(OTrnNtfPK oTrnNtfPK);
    
    /**
     * Obtiene la descripción encVal.
     *
     * @param oTrnNtfPK -> La clave primaria
     * @return          -> La descripción oprIdnNam
     */
    String getEncNam(OTrnNtfPK oTrnNtfPK);

}
