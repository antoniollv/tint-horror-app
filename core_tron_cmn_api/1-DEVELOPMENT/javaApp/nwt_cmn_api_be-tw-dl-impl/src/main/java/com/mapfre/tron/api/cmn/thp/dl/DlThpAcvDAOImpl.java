package com.mapfre.tron.api.cmn.thp.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The thirdpart activity repository implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 2 Jul 2021 - 12:12:57
 *
 */
@Repository
public class DlThpAcvDAOImpl implements IDlThpAcvDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific thirdpart activity code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpAcvVal -> Thirdpart activity code
     * @return          -> The number of rows
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal thpAcvVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_act_tercero) ")
                .append("FROM TRON2000.A1002200 t ")
                .append("WHERE t.cod_act_tercero = ? ")
                .append("AND t.cod_cia           = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { thpAcvVal, cmpVal }, Integer.class);

        return lvVod;
    }

    /**
     * Count how many rows exists for a specific thirdpart agent code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param thpVal -> Thirdpart agent code
     * @param vldDat -> Validation date
     * @return       -> Number of rows for a specific thirdpart agent code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal thpVal, final Date vldDat) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(t.cod_agt) ");
        sql.append("FROM TRON2000.A1001332 t ");
        sql.append("WHERE t.cod_cia    = ? ");
        sql.append("AND t.cod_agt      = ? ");
        if (vldDat != null) {
            sql.append("AND t.fec_validez <= ? ");
        }

        Object[] params = null;
        if (vldDat != null) {
            params = new Object[] { cmpVal, thpVal, vldDat };
        } else {
            params = new Object[] { cmpVal, thpVal };
        }

        int lvVod = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);

        return lvVod;
    }

    /**
     * Count how many rows exists for a specific thirdpart code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpVal    -> Thirdpart code
     * @param thpAcvVal -> Thirdpart activity code
     * @return          -> Number of rows for a specific thirdpart code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal thpVal, final BigDecimal thpAcvVal) {

        final String sql = new StringBuilder()
                .append("SELECT COUNT(t.cod_tercero) ")
                .append("FROM TRON2000.A1001390 t ")
                .append("WHERE t.cod_cia       = ? ")
                .append("AND t.cod_tercero     = ? ")
                .append("AND t.cod_act_tercero = ? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, thpVal, thpAcvVal }, Integer.class);

        return lvVod;
    }

    
    /**
     * Count how many rows exists for a specific thirdpart code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpVal    -> Thirdpart code
     * @return          -> Number of rows for a specific thirdpart code
     */
	@Override
	public int countTramitador(BigDecimal cmpVal, String lngVal, BigDecimal thpVal) {
        final String sql = new StringBuilder()
                .append("select COUNT(t.cod_tramitador)  ")
                .append("from TRON2000.A1001339 t ")
                .append("WHERE t.cod_cia = ? ")
                .append("AND t.cod_tramitador=? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, thpVal }, Integer.class);

        return lvVod;
	}

}
