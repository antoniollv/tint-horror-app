package com.mapfre.tron.api.cmn.abd.vhu.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.abd.vhu.dl.IDlAbdVhuDao;
import com.mapfre.tron.api.cmn.dl.BaseCaheDao;

/**
 * The AbdVhu repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 17 May 2022 - 12:37:24
 *
 */
@Repository
public class DlAbdVhuDaoImpl extends BaseCaheDao implements IDlAbdVhuDao {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Get the vehicle use description.
     *
     * @param map -> Company code and Vehicle use code
     * @return       -> The vehicle use description
     */
    @Cacheable("PoC-VhuNam")
    @Override
    public String get(final Map<String, Object> map) {
        final String sql = new StringBuilder()
                .append("SELECT")
                .append("    a.nom_uso_vehi ")
                .append("FROM")
                .append("    a2100200 a ")
                .append("WHERE")
                .append("        a.cod_cia = :cmpVal")
                .append("    AND a.mca_inh = 'N'")
                .append("    AND a.cod_uso_vehi = :vhuVal ")
                .append("AND a.fec_validez IN (")
                .append("    SELECT")
                .append("        MAX(b.fec_validez)")
                .append("    FROM")
                .append("        a2100200 b")
                .append("    WHERE")
                .append("            b.cod_cia = a.cod_cia")
                .append("        AND b.cod_uso_vehi = a.cod_uso_vehi")
                .append("        AND a.mca_emi = b.mca_emi")
                .append("        AND a.mca_inh = b.mca_inh")
                .append("        AND b.fec_validez <= nvl(:vldDat, sysdate)")
                .append(")")
                .toString();

        try {
            return jdbc.queryForObject(sql, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public String getVhuNam(Map<String, Object> map) {
        return null;
    }

}
