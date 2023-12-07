package com.mapfre.tron.api.trnkglobals;

import java.sql.Types;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

/**
 * The PAsignaDATE DL procedure repository.
 *
 * @author pvraul1
 * @since 1.8
 * @version 12 nov. 2019 15:28:13
 *
 */
@Slf4j
@Repository
public class PAsignaDATE  extends StoredProcedure {

    /** The constant with the TRN_K_GLOBAL_P_ASIGNA qualified name.*/
    public static final String TRN_K_GLOBAL_P_ASIGNA = "trn_k_global.p_asigna";

    /**
     * Create a new instance of the class.
     * @param ds the datasource property
     */
    @Autowired
    public PAsignaDATE(@Qualifier("dsTwPr") DataSource ds) {
        super(ds, TRN_K_GLOBAL_P_ASIGNA);
        declareParameter(new SqlParameter("p_variable", Types.NVARCHAR));
        declareParameter(new SqlParameter("p_valor", Types.DATE));
        compile();
    }

    /**
     * asigna: Asigna el valor de la variable.
     *
     * @param pmVariable -> El nombre de la variable a asignar
     * @param pmValor    -> El valor de la variable a asignar
     */
    public void asigna(final String pmVariable, final Date pmValor) {
        log.debug("Launching trn_k_global.p_asigna...");
        super.execute(pmVariable, pmValor);
    }

}