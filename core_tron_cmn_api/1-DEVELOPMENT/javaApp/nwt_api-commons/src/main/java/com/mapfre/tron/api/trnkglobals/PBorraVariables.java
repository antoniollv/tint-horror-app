package com.mapfre.tron.api.trnkglobals;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

/**
 * The PBorraVariables DL procedure repository.
 *
 * @author BRCHARL
 * @since 1.8
 * @version 12 nov. 2019 15:28:13
 *
 */
@Slf4j
@Repository
public class PBorraVariables extends StoredProcedure {

    /** The constant with the TRN_K_GLOBAL_BORRA_TODAS_VARIABLES qualified name. */
    public static final String TRN_K_GLOBAL_BORRA_TODAS_VARIABLES = "trn_k_global.borra_todas_variables";

    /**
     * Create a new instance of the class.
     * 
     * @param ds the datasource property
     */
    @Autowired
    public PBorraVariables(@Qualifier("dsTwPr") DataSource ds) {
        super(ds, TRN_K_GLOBAL_BORRA_TODAS_VARIABLES);
        compile();
    }

    /**
     * removeGlobals: Borra las variables globales.
     */
    public void removeGlobals() {
        log.debug("Launching trn_k_global.borra_todas_variables...");
        super.execute();
    }

}
