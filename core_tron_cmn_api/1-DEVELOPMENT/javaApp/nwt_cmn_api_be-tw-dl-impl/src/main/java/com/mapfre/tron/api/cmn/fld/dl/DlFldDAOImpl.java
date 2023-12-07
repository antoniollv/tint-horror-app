package com.mapfre.tron.api.cmn.fld.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The field validation repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 08:32:29
 *
 */
@Repository
public class DlFldDAOImpl implements IDlFldDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific field name.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Business line code
     * @param mdtVal -> Modality code
     * @param fldNam -> Field name
     * @return       -> Number of rows for a specific field name
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal lobVal, final BigDecimal mdtVal,
            final String fldNam) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_campo) ")
                .append("FROM TRON2000.G2000020 t ")
                .append("WHERE t.cod_cia     = ? ")
                .append("AND t.cod_ramo      = ? ")
                .append("AND t.cod_modalidad = ? ")
                .append("AND t.cod_campo     = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, lobVal, mdtVal, fldNam }, Integer.class);

        return lvVod;
    }

}
