package com.mapfre.tron.api.comn.enr.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.enr.dl.IDlEnrDAO;

/**
 * The endorsement respository implementation.
 *
 * @author architecture - majoguam
 * @since 1.8
 *
 */
@Repository
public class DlEnrDAOImpl implements IDlEnrDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Count how many rows exists for a specific endorsement code.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param enrVal       -> Endorsement code
     * @param enrSbdVal    -> Endorsement subcode value
     * @param enrScoTypVal -> Endorsement scope type value
     * @return             -> Number of rows for a specific endorsement code
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal enrVal, final BigDecimal enrSbdVal, final BigDecimal enrScoTypVal) {

        final String sql = new StringBuilder()
                .append("select COUNT(t.cod_spto)  ")
                .append("from TRON2000.A2991800 t ")
                .append("WHERE t.cod_cia = ? ")
                .append("AND t.cod_spto=? ")
                .append("AND t.sub_cod_spto=? ")
                .append("AND t.tip_ambito_spto=? ")
                .toString();

        int lvVod = jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, enrVal, enrSbdVal, enrScoTypVal }, Integer.class);

        return lvVod;
    }

    /**
     * Count how many rows exists for a specific endorsement subcode.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param enrVal       -> Endorsement code
     * @param enrSbdVal    -> Endorsement subcode value
     * @param enrScoTypVal -> Endorsement scope type value
     * @param temVal       -> Termine value
     * @return             -> Number of rows for a specific endorsement subcode
     */
    @Override
    public int count(final BigDecimal cmpVal, final String lngVal, final BigDecimal enrVal, final BigDecimal enrSbdVal,
            final String enrScoTypVal, final String temVal) {

        StringBuilder sql = new StringBuilder();
        if ("SBD".equalsIgnoreCase(temVal)) {
            sql.append("SELECT COUNT(t.sub_cod_spto) ");
        }
        sql.append("FROM TRON2000.A2991800 t ");
        sql.append("WHERE t.cod_cia       = ? ");
        sql.append("AND t.cod_spto        = ? ");
        sql.append("AND t.sub_cod_spto    = ? ");
        sql.append("AND t.tip_ambito_spto = ? ");

        int lvVod = jdbcTemplate.queryForObject(sql.toString(),
                new Object[] { cmpVal, enrVal, enrSbdVal, enrScoTypVal }, Integer.class);

        return lvVod;
    }

}
