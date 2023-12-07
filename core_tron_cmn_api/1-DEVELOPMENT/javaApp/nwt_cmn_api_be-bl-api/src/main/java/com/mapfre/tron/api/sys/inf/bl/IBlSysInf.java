package com.mapfre.tron.api.sys.inf.bl;

import com.mapfre.tron.api.cmn.model.About;

/**
 * The System info business service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 20 nov. 2020 - 11:15:31
 *
 */
public interface IBlSysInf {

    /**
     * Query Query about application. It will return the The information about the deployed application.
     *
     * @author arquitectura - pvraul1
     *
     * @param cmpVal      -> Company code
     * @param usrVal      -> User code
     * @param lngVal      -> Language code
     *
     * @return            -> The information about the deployed application
     */
    About getAboutQuery(Integer cmpVal, String usrVal, String lngVal);

}
