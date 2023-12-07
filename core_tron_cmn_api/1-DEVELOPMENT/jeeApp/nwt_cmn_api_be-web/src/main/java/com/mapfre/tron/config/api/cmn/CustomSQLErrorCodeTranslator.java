package com.mapfre.tron.config.api.cmn;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

/**
 * The custom SQL error code translator.
 *
 * @author pvraul1
 * @since 1.0.0
 * @version 6 ago. 2019 12:56:44
 *
 */
public class CustomSQLErrorCodeTranslator extends SQLErrorCodeSQLExceptionTranslator {

    @Override
    protected DataAccessException customTranslate(String task, String sql, SQLException sqlEx) {
        if (sqlEx.getErrorCode() == 1) {
            return new DuplicateKeyException("Custome Exception translator - Integrity contraint voilation.", sqlEx);
        }
        return null;
    }

}