package com.mapfre.tron.api.tpd.prf.dl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.tpd.prf.bo.OTpdPrfS;

/**
 * The TpdPrf jdbc repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 Mar 2023 - 10:14:10
 *
 */
@Repository
public class DlTpdPrfDAOImpl implements IDlTpdPrfDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Query Profession. It will return the information of Profession.
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param prfVal -> Profession
     * @param cmpVal -> Company code
     * @return       -> The information of Profession
     */
    @Override
    public OTpdPrfS getProfession(final String usrVal, final String lngVal, final Integer prfVal, final Integer cmpVal) {

        final String sql = new StringBuilder()
                .append("SELECT")
                .append("    t.cod_profesion,")
                .append("    t.nom_profesion,")
                .append("    t.cod_usr,")
                .append("    t.fec_actu,")
                .append("    t.cod_idioma,")
                .append("    t.cod_cia ")
                .append("FROM")
                .append("    g1000100 t ")
                .append("WHERE")
                .append("        t.cod_cia = ?")                                                              // cmpVal
                .append("    AND t.cod_profesion = ?")                                                        // prfVal
                .append("    AND t.cod_idioma = ?")                                                           // lngVal
                .toString();

        return jdbcTemplate.queryForObject(sql,
                new Object[] {
                        cmpVal,
                        prfVal,
                        lngVal
                },
                new OTpdPrfSRowMapper());

    }

}
