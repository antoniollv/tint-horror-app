package com.mapfre.tron.api.cmn.vld.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DlZonesDAOImpl implements IDlZonesDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific postal code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country value
     * @param pslCodVal -> Postal code
     * @param twnVal    -> Town Value
     * @return          -> Number of rows for a specific postal code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final String cnyVal, final BigDecimal pslCodVal,
            final BigDecimal twnVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_postal) ")
                .append("FROM TRON2000.A1000103 t ")
                .append("WHERE t.cod_pais    = ? ")
                .append("AND t.cod_postal    = ? ")
                .append("AND t.cod_localidad = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cnyVal, pslCodVal, twnVal }, Integer.class);

        return lvVod;
    }

}
