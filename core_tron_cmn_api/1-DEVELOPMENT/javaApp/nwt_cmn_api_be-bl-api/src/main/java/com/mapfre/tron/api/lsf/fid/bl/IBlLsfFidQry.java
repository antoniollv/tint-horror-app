package com.mapfre.tron.api.lsf.fid.bl;

import com.mapfre.nwt.trn.lsf.fid.bo.OLsfFidS;

/**
 * The IBlLsfFidQry business logic service interface.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 9 feb 2023 - 17:49:28
 *
 */
public interface IBlLsfFidQry {

    /**
     * Query file type definition
     *
     * @author Javier Sangil
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param filTypVal -> File Type Value
     * 
     * @return OLsfFidS -> File type defintion
     */
    OLsfFidS getFileTypeDefinition(Integer cmpVal, String usrVal, String lngVal, String filTypVal);

    
}
