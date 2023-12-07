package com.mapfre.tron.api.cmn.aed.dl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;

import lombok.extern.slf4j.Slf4j;

/**
 * The CmnAed repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 Oct 2023 - 11:45:49
 *
 */
@Slf4j
@Repository
public class DlCmnAedDAOImpl implements IDlCmnAedDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public OCmnAedS get000(final Integer cmpVal, final String cmpUsrVal, final String lngVal, final String usrVal) {

        if (log.isInfoEnabled()) {
            log.info("get000 query called!");
        }

        List<Object> paramsToAdd = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT");
        sql.append("    a.cod_cia,");
        sql.append("    a.cod_usr_cia,");
        sql.append("    d1.nom_usr_cia,");
        sql.append("    a.cod_nivel1,");
        sql.append("    d2.nom_nivel1,");
        sql.append("    a.cod_nivel2,");
        sql.append("    d3.nom_nivel2,");
        sql.append("    a.cod_nivel3,");
        sql.append("    d4.nom_nivel3,");
        sql.append("    a.cod_agt,");
        sql.append("    d5.nom_completo,");
        sql.append("    a.cod_sector,");
        sql.append("    d6.nom_sector,");
        sql.append("    a.cod_subsector,");
        sql.append("    d7.nom_subsector,");
        sql.append("    a.cod_ramo,");
        sql.append("    d8.nom_ramo,");
        sql.append("    a.cod_emp_agt,");
        sql.append("    d9.nom_completo ");
        sql.append("FROM");
        sql.append("    acceso   a,");
        sql.append("    g1002700 d1,");
        sql.append("    a1000700 d2,");
        sql.append("    a1000701 d3,");
        sql.append("    a1000702 d4,");
        sql.append("    a1000200 d6,");
        sql.append("    a1000250 d7,");
        sql.append("    a1001800 d8,");
        sql.append("    v1001390 d5,");
        sql.append("    v1001390 d9 ");
        sql.append("WHERE");

        sql.append("        a.cod_usr_cia = ?");                                                           // cmpUsrVal
        paramsToAdd.add(cmpUsrVal);

        sql.append("    AND a.cod_cia = ?");                                                               // cmpVal
        paramsToAdd.add(cmpVal);

        sql.append("    AND a.cod_usr_cia = d1.cod_usr_cia");
        sql.append("    AND a.cod_cia = d1.cod_cia");
        sql.append("    AND a.cod_cia = d2.cod_cia");
        sql.append("    AND a.cod_nivel1 = d2.cod_nivel1");
        sql.append("    AND a.cod_cia = d3.cod_cia");
        sql.append("    AND a.cod_nivel1 = d3.cod_nivel1");
        sql.append("    AND a.cod_nivel2 = d3.cod_nivel2");
        sql.append("    AND a.cod_cia = d4.cod_cia");
        sql.append("    AND a.cod_nivel1 = d4.cod_nivel1");
        sql.append("    AND a.cod_nivel2 = d4.cod_nivel2");
        sql.append("    AND a.cod_nivel3 = d4.cod_nivel3");
        sql.append("    AND a.cod_cia = d6.cod_cia");
        sql.append("    AND a.cod_sector = d6.cod_sector");
        sql.append("    AND a.cod_cia = d7.cod_cia");
        sql.append("    AND a.cod_sector = d7.cod_sector");
        sql.append("    AND a.cod_sector = d7.cod_subsector");
        sql.append("    AND a.cod_cia = d8.cod_cia");
        sql.append("    AND a.cod_sector = d8.cod_sector");
        sql.append("    AND a.cod_sector = d8.cod_subsector");
        sql.append("    AND a.cod_ramo = d8.cod_ramo");
        sql.append("    AND d5.cod_cia (+) = a.cod_cia");
        sql.append("    AND d5.cod_tercero (+) = a.cod_agt");
        sql.append("    AND d5.cod_act_tercero (+) = 2");
        sql.append("    AND d9.cod_cia (+) = a.cod_cia");
        sql.append("    AND d9.cod_tercero (+) = a.cod_agt");
        sql.append("    AND d9.cod_act_tercero (+) = 37");

        return jdbcTemplate.queryForObject(sql.toString(), paramsToAdd.toArray(), new OCmnAedSRowMapper());
    }

}
