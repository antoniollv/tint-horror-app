package com.mapfre.tron.api.crn.exr.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.crn.exr.bo.OCrnExrP;

import lombok.Data;


/**
 * The CrnExrQry repository.
 *
 * @author pvraul1
 * @since 1.8
 * @version 23 abril. 2020 13:08:36
 *
 */
@Data
@Repository
public class SrCrnExrQryDAOImpl implements ISrCrnExrQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Query currencies. It will return the list of currencies.
     *
     * @author pvraul1
     *
     * @return Currency List
     */
    @Override
    public OCrnExrP chnDat(final String lngVal, final String usrVal, final BigDecimal crnVal, final Date exrDat, final BigDecimal cmpVal) {

        final String query = new StringBuilder()
                .append("SELECT ")
                .append("a.cod_mon,")
                .append("a.fec_cambio,")
                .append("a.val_cambio ")
                .append("FROM ")
                .append("a1000500 a ")
                .append("WHERE cod_mon  = ? ")
                .append("AND cod_cia = ? ")
                .append("AND fec_cambio = ")
                .append("(SELECT MAX(fec_cambio)")
                .append(" FROM a1000500 ")
                .append(" WHERE cod_mon = ?")
                .append("AND cod_cia = ? ")
                .append(" AND fec_cambio   <= ? )")
                .toString();

        OCrnExrP oCrnExrP = jdbcTemplate.queryForObject(query,
                new Object[]{
                        crnVal,
                        cmpVal,
                        crnVal,
                        cmpVal,
                        exrDat,
                        },
                new OCrnExrRowMapper());

        return oCrnExrP;
    }

}
