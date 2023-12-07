package com.mapfre.tron.api.cmu.thl.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlP;

import lombok.Data;

/**
 * The CmuThlQry repository.
 *
 * @author USOZALO
 * @since 1.8
 * @version 04 jun. 2020 13:08:36
 *
 */
@Data
@Repository
public class SrCmuThlQryDAOImpl implements ISrCmuThlQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public OCmuThlP get(final BigDecimal cmpVal, final String lngVal, final String usrVal, final BigDecimal thrLvlVal) {

        final String query = new StringBuilder()
                .append("SELECT ")
                .append("g.cod_cia,")
                .append("g.cod_nivel3,") 
                .append("g.nom_nivel3, ")
                .append("g.abr_nivel3, ")
                .append("g.cod_nivel1, ")
                .append("h.nom_nivel1, ")
                .append("g.cod_nivel2, ")  
                .append("i.nom_nivel2, ")
                .append("g.obs_nivel3, ")
                .append("g.mca_inh, ")
                .append("g.mca_emision, ")
                .append("g.tip_distribucion, ")
                .append("g.fec_apertura, ")
                .append("g.mca_propia ")
                .append("FROM ")
                .append("a1000702 g ")
                .append("INNER JOIN a1000700 h ON g.cod_cia = h.cod_cia AND g.cod_nivel1 = h.cod_nivel1 ")
                .append("INNER JOIN a1000701 i ON g.cod_cia = i.cod_cia AND g.cod_nivel2 = i.cod_nivel2 ")
                .append("WHERE g.cod_cia  = ? ")
                .append("AND g.cod_nivel3 = ? ")
                .toString();

        OCmuThlP oCmuThlP = jdbcTemplate.queryForObject(query,
                new Object[]{
                        cmpVal,
                        thrLvlVal,
                        },
                new OCmuThlRowMapper());

        return oCmuThlP;
    }

}
