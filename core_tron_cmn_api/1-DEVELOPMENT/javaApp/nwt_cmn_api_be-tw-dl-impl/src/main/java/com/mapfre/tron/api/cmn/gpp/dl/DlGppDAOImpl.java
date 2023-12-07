package com.mapfre.tron.api.cmn.gpp.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The account respository implementation.
 *
 * @author architecture - majoguam
 * @since 1.8
 *
 */
@Repository
public class DlGppDAOImpl implements IDlGppDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Validate Supplement value exists.
     *
     * @param cmpVal    	-> Company code
     * @param lngVal    	-> Language code
     * @param enrVal 		-> Endorsement code
     * @param enrSbdVal 	-> Endorsement subcode
     * @param enrScoTypVal 	-> Endorsement scope type value
     * @return          	-> An error if the Supplement value does not exit
     */

    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final String gppVal) {

        final String sql = new StringBuilder()
                .append("select COUNT(t.num_poliza)   ")
                .append("from TRON2000.G2990017 t ")
                .append("WHERE t.cod_cia = ? ")
                .append("AND t.num_poliza=? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, gppVal }, Integer.class);

        return lvVod;
    }

}
