package com.mapfre.tron.api.cmn.typ.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:38:34
 *
 */
@Repository
public class DlTypDAOImpl implements IDlTypDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Ramo
     * @param valVal -> Valor
     * 
     * @return       -> Number of rows for a specific state code
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, String valVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_campo) ")
                .append("FROM TRON2000.G1010031 t ")
                .append("WHERE t.cod_cia = ? ")
                .append("AND t.cod_campo = 'TIP_EMISION' ")
                .append("AND t.cod_ramo  = ? ")
                .append("AND t.cod_valor = ? ")
                .append("AND t.cod_idioma= ? ")
                .toString();        

        Object[] params = new Object[] { cmpVal, lobVal, valVal, lngVal };

        int lvVod = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return lvVod;
    }

    
    /**
     * Validate type document  exists.
     *
     * @param cmpVal 		-> Company code
     * @param lngVal 		-> Language code
     * @param thpDcmTypVal 	-> third party type document value
     * 
     * @return       		-> An error if the third level value does not exit
     */
	@Override
	public int countDoc(BigDecimal cmpVal, String lngVal, String thpDcmTypVal) {
		 final String sql = new StringBuilder()
	                .append("select COUNT(t.tip_docum) ")
	                .append("FROM TRON2000.A1002300 t ")
	                .append("WHERE t.cod_cia = ? ")
	                .append("AND t.tip_docum=? ")
	                .toString();        


	        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, thpDcmTypVal }, Integer.class);

	        return lvVod;
	}


	
	/**
     * Validate Endorsement exists.
     *
     * @param cmpVal 		-> Company code
     * @param lngVal 		-> Language code
     * @param lobVal 		-> cod ramo
     * @param valVal 		-> value
     * 
     * @return       		-> An error if the third level value does not exit
     */
	@Override
	public int countEnr(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, String valVal) {
		 final String sql = new StringBuilder()
	                .append("select COUNT(t.cod_campo)  ")
	                .append("from TRON2000.G1010031 t ")
	                .append("WHERE t.cod_cia = ? ")
	                .append("AND t.cod_campo='TIP_SPTO' ")
	                .append("AND t.cod_ramo=? ")
	                .append("AND t.cod_valor=? ")
	                .append("AND t.cod_idioma=? ")
	                .toString();        


	        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, lobVal, valVal, lngVal }, Integer.class);

	        return lvVod;
	}


}
