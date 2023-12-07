package com.mapfre.tron.api.cmn.sr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.tron.api.cmn.model.About;
import com.mapfre.tron.api.sys.inf.bl.IBlSysInf;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The about implementation controller.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 20 nov. 2020 - 10:45:15
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Settings", })
public class AboutController implements AboutApi {

    /** The system info business service.*/
    @Autowired
    private IBlSysInf iBlSysInf;

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
    public ResponseEntity<About> getAboutQuery(
            @ApiParam(value = "Company code", required = false) @RequestHeader(value = "cmpVal", required = false) final Integer cmpVal,
            @ApiParam(value = "User code", required = false) @RequestHeader(value = "usrVal", required = false) final String usrVal,
            @ApiParam(value = "Language code", required = false) @RequestHeader(value = "lngVal", required = false) final String lngVal) {

        log.info("The getAboutQuery rest operation had been called...");

        try {
            About lvAbout = iBlSysInf.getAboutQuery(cmpVal, usrVal, lngVal);
            
            if (lvAbout != null) {
                return new ResponseEntity<>(lvAbout, HttpStatus.OK);
            } else {
                String message = String.format(
                        "TST-20001: Entity with cmpVal: %s, usrVal: %s, lngVal: %s: not found", cmpVal,
                        usrVal, lngVal);
                throw new NwtException(message);
            }

        } catch (IncorrectResultSizeDataAccessException e) {
            String message = String.format(
                    "TST-20001: Entity with cmpVal: %s, usrVal: %s, lngVal: %s: not found", cmpVal,
                    usrVal, lngVal);
            throw new NwtException(message);

        } catch (NwtException e) {
            throw e;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);

        }

    }

}
