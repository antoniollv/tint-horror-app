package com.mapfre.tron.api.cmn.dcm.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.dcm.fld.dl.IDlDcmDAO;

/**
 * The documentum repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 16 Jul 2021 - 10:48:48
 *
 */
@Repository
public class DlDcmDAOImpl implements IDlDcmDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific third person document number.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param thpDcmVal    -> Third person documentum number
     * @param thpDcmTypVal -> Third person documentum type
     * @return       -> Number of rows for a specific third person document number
     */
    @Override
    public int count(BigDecimal cmpVal, String lngVal, String thpDcmVal, String thpDcmTypVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_docum) ")
                .append("FROM TRON2000.v1001390 t ")
                .append("WHERE t.cod_cia = ? ")
                .append("AND t.cod_docum = ? ")
                .append("AND t.tip_docum = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, thpDcmVal, thpDcmTypVal }, Integer.class);

        return lvVod;
    }

}
