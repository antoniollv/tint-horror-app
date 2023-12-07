package com.mapfre.tron.api.cmn.del.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.enr.dl.IDlEnrDAO;

/**
 * The account respository implementation.
 *
 * @author architecture - majoguam
 * @since 1.8
 *
 */
@Repository
public class DlDelDAOImpl implements IDlDelDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Validate del_val value exists.
     *
     * @param cmpVal    	-> Company code
     * @param lngVal    	-> Language code
     * @param lobVal 		-> Line of Business
     * @param vldDat 		-> Validation Date
     * @param delVal 		-> 
     * @return          	-> An error if the Supplement value does not exit
     */
	@Override
	public int count(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, Date vldDat, BigDecimal delVal) {
        final String sql = new StringBuilder()
                .append("select COUNT(t.num_contrato)  ")
                .append("from TRON2000.G2990027 t ")
                .append("WHERE t.cod_cia = ? ")
                .append("AND t.cod_ramo=? ")
                .append("AND t.fec_validez <=? ")
                .append("AND t.num_contrato=? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, lobVal, vldDat, delVal }, Integer.class);

        return lvVod;
	}

}
