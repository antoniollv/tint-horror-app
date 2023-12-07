package com.mapfre.tron.api.sys.inf.bl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.mapfre.tron.api.bo.NewTronAccess;
import com.mapfre.tron.api.cmn.model.About;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The newtron system information business logic implementation service.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 nov. 2020 - 8:31:06
 *
 */
@Data
@Slf4j
@NewTronAccess
public class NwtBlSysInfImpl implements IBlSysInf {

    /** The application version.*/
    @Value("${app.system.info.project.version}")
    private String projectVersion;

    /** The application name and version.*/
    @Value("${app.system.info.be.version.dependency}")
    private String beVersion;

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
    @Override
    public About getAboutQuery(Integer cmpVal, String usrVal, String lngVal) {
        log.info("Newtron NwtBlSysInfImpl.getAboutQuery implementation service has been called...");

        About lvAbout = new About();
        // setting application name
        lvAbout.setName("nwt_cmn_api_be:".concat(projectVersion));

        // setting profile
        lvAbout.setProfile("NewTronAccess");

        // setting dependencies
        List<String> dependencies = new ArrayList<>();
        dependencies.add("nwt_be:".concat(beVersion));
        lvAbout.setDependencies(dependencies);

        return lvAbout;
    }

}
