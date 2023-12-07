/**
 * 
 */
package com.mapfre.tron.api.cmn;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;

/**
 * The ResetPackage service.
 *
 * @author pvraul1
 * @since 1.8
 * @version 23 sept. 2019 9:41:06
 *
 */
@Service
public class ResetPacakge extends StoredProcedure {

    /** The constant with the DBMS_SESSION.RESET_PACKAGE qualified name.*/
    public static final String PROC_RESET_PACKAGE = "DBMS_SESSION.RESET_PACKAGE";

    /**
     * Create a new instance of the class.
     * @param ds the datasource property
     */
    @Autowired
    public ResetPacakge(@Qualifier("dataSource") DataSource ds) {
        super(ds, PROC_RESET_PACKAGE);
        compile();
    }

    /**
     * The execute method.
     */
    public void executeRP() {
        execute(new HashMap<String, Object>());
    }

}
