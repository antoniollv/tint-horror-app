package com.mapfre.tron.api.trnkglobals;


import java.math.BigDecimal;
import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

/**
 * The PAsignaVARCHAR DL procedure repository.
 *
 * @author BRCHARL
 * @since 1.8
 * @version 11 dic. 2019 09:28:13
 *
 */
@Slf4j
@Repository
public class PSacaNomGestor extends StoredProcedure {

    /** The constant with the GC_P_SACA_NOM_GESTOR qualified name.*/
    public static final String GC_P_SACA_NOM_GESTOR = "gc_p_saca_nom_gestor";

    /**
     * Create a new instance of the class.
     * @param ds the datasource property
     */
    @Autowired
    public PSacaNomGestor(@Qualifier("dsTwPr") DataSource ds) {
        super(ds, GC_P_SACA_NOM_GESTOR);
        declareParameter(new SqlParameter("p_cod_cia", Types.BIGINT));
        declareParameter(new SqlParameter("p_tip_gestor", Types.NVARCHAR));
        declareParameter(new SqlParameter("p_cod_gestor", Types.NVARCHAR));
        declareParameter(new SqlInOutParameter("p_nom_gestor", Types.NVARCHAR));
        compile();
    }

    /**
     * Get MnrNam from gc_p_saca_nom_gestor procedure.
     *
     * @param cmpVal    -> company code
     * @param usrVal    -> user code
     * @param lngVal    -> language code
     * @param mnrTypVal -> Tip gestor
     * @param mnrVal    -> gestor code
     * @return -> mnrNam name gestor
     */
    @Cacheable("PoC-getMnrNam")
    public String getMnrNam(BigDecimal cmpVal, String usrVal, String mnrTypVal, String mnrVal) {
	Map<String, Object> out = super.execute(cmpVal, mnrTypVal, mnrVal, new String(""));

	String mnrNam = null;
	mnrNam = (String) (out.get("p_nom_gestor"));
	return mnrNam;
    }

}
