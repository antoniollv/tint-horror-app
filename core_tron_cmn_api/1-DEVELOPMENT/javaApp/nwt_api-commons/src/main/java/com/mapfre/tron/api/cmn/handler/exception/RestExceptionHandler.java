package com.mapfre.tron.api.cmn.handler.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.tron.api.bo.Error;
import com.mapfre.tron.api.cmn.CommonManageExceptions;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The default rest exception handler.
 * @author pvraul1
 *
 * @version 17 jun. 2020 16:52:22 
 * @since jdk 1.8
 *
 */
@Data
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

    /** The issue exception manager.*/
    private final CommonManageExceptions manageEx = new CommonManageExceptions();

    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(HttpStatusCodeException.class)
    protected ResponseEntity<Error> handleHttpClientException(HttpStatusCodeException ex, HttpServletRequest request)
            throws Exception {

        log.error("The service rest operation had been failed: " + ex.getMessage(), ex);

        Error currentError = null;
        try {
            currentError = objectMapper.readValue(ex.getResponseBodyAsString(), Error.class);
        } catch (Exception e) {
            log.warn("ERROR:", e);
            throw ex;
        }

        return new ResponseEntity<>(currentError, ex.getStatusCode());
    }

    @ExceptionHandler(NwtException.class)
    protected ResponseEntity<Error> handleNwtException(NwtException ex, HttpServletRequest request) {

        String msgErr = "The service rest operation had been failed";
        if (ex.getMessage() != null && ex.getMessage().trim().length() > 0) {
            msgErr = String.format(msgErr.concat(": %s") , ex.getMessage());
        }
        log.error(msgErr, ex);
        String pmContext = "SR Controller";
        if (ex.getSrvNam() != null && ex.getSrvNam().trim().length() > 0) {
            pmContext = ex.getSrvNam();
        }
        Error currentError = manageEx.getErrorFromNwtEx(ex, "CSS API", pmContext);

        return new ResponseEntity<>(currentError, HttpStatus.BAD_REQUEST);
    }

}
