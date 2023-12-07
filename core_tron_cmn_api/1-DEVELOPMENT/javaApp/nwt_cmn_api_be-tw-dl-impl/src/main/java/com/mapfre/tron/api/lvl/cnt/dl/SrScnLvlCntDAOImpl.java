package com.mapfre.tron.api.lvl.cnt.dl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

import lombok.Data;

/**
 * SrScnLvlCntDAO comment of the class.
 *
 * @author magarafr
 * @since 1.8
 * @version 09 feb. 2021 11:08:33
 *
 */
@Data
@Repository
public class SrScnLvlCntDAOImpl implements ISrScnLvlCntDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Query Contact From Second Level
     *
     * @author magarafr
     * @purpose Query the second level contact information. It will return the
     *          contact of the second level of the commercial structure . It will be
     *          mandatory send the parameters defined in the service and the service
     *          will response with the output object defined.
     *
     * @param cmpVal    -> Company code
     * @param scnLvlVal -> Second Level
     * @return -> OThpCntP
     */
    @Override
    public OThpCntS scnLvlCnt(final Integer cmpVal, final Integer scnLvlVal) {

        OThpCntS lvOThpCntP = null;

        final String query = new StringBuilder()
                .append("SELECT ")
                .append("a.cod_cia, ")
                .append("a.tlf_pais, ")
                .append("a.tlf_zona, ")
                .append("a.tlf_numero, ")
                .append("a.fax_numero, ")
                .append("a.email, ")
                .append("a.nom_resp, ")
                .append("a.ape_resp ")
                .append("FROM a1000701 a ")
                .append("WHERE a.cod_cia = ? ")
                .append("AND a.cod_nivel2 = ? ").toString();

        try {
            lvOThpCntP = jdbcTemplate.queryForObject(query, new Object[] { cmpVal, scnLvlVal }, new OScnLvlRowMapper());
        } catch (EmptyResultDataAccessException e) {
            lvOThpCntP = null;
        }

        return lvOThpCntP;

    }

}
