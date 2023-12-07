package com.mapfre.tron.api.tcd.tce.dl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.tcd.tce.bo.OTcdTceS;

/**
 * The TpdPrf jdbc repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 Mar 2023 - 10:14:10
 *
 */
@Repository
public class DlTcdTceDAOImpl implements IDlTcdTceDAO {

 /** The spring jdbc template. */
 @Qualifier("jdbcTemplate")
 @Autowired
 protected JdbcTemplate jdbcTemplate;

    /**
     * Query Technical Control Definition
     *
     * @author Javier Sangil
     * 
     * 
     * @param cmpVal     -> company code
     * @param usrVal     -> user value
     * @param lngVal     -> language value
     * @param errValList -> Error List Value
     * 
     * @return List<OTcdTceS>
     * 
     */
    @Override
    public List<OTcdTceS> getTechnicalControlDefinition(BigDecimal pmCmpVal, String usrVal, String lngVal,
	    List<String> errValList) {

	final String inMvmIdnSql = String.join(",", Collections.nCopies(errValList.size(), "?"));

        	final String sql = new StringBuilder().append("SELECT ")
    		.append("a.cod_cia, ")
    		.append("a.cod_error, ")
    		.append("b.nom_error, ")
    		.append("a.tip_rechazo, ")
    		.append("a.cod_nivel_autorizacion, ")
    		.append("a.mca_spto, ")
    		.append("a.mca_error_spto, ")
    		.append("a.mca_rf, ")
    		.append("a.mca_poliza, ")
    		.append("a.nom_prg_autoriza, ")
    		.append("a.nom_prg_rechaza, ")
    		.append("a.tip_accion_rechazo, ")
    		.append("a.tip_autoriza_error, ")
    		.append("a.cod_sist_aut, ")
    		.append("a.mca_obs_obligatorias ")
    		.append("from g2000210 a, g2000211 b ")
    		.append("WHERE a.cod_cia = ? ")
    		.append(" and a.cod_error in (" + inMvmIdnSql + ") ")
    		.append(" and a.cod_cia = b.cod_cia ")
    		.append(" and a.cod_error = b.cod_error ")
    		.append(" and b.cod_idioma = ? ")
		.toString();

	List<Object> objects = new ArrayList<>();
	objects.add(pmCmpVal);
	errValList.stream().forEach(idn -> objects.add(idn));
	objects.add(lngVal);

	return jdbcTemplate.query(sql, objects.toArray(), new OTcdTceSRowMapper());

    }

}
