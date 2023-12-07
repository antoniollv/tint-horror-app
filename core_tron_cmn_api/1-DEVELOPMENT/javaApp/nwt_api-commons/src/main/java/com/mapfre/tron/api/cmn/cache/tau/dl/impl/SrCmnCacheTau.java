package com.mapfre.tron.api.cmn.cache.tau.dl.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;
import com.mapfre.tron.api.cmn.cache.tau.dl.ISrCacheTauDAO;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;

import lombok.Data;

/**
 * The common Tabla Acceso por Usuario repository implementation.
 *
 * @author magarafr
 * @since 1.8
 * @vesion 16 dic. 2020 - 15:59:05
 *
 */
@Data
@Repository
public class SrCmnCacheTau implements ISrCacheTauDAO {

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
	jdbcTemplate = new JdbcTemplate(dataSource);
	final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
	jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }
    
    @Override
    public List<OCmnAedS> tabAccPorUsu(String usrVal) {
	
	String query = new StringBuilder().append("SELECT ").append("a.cod_cia, ").append("a.cod_usr_cia, ")
		.append("a.cod_nivel1, ").append("a.cod_nivel2, ").append("a.cod_nivel3, ").append("a.cod_agt, ")
		.append("a.cod_sector, ").append("a.cod_subsector, ").append("a.cod_ramo, ").append("a.cod_emp_agt ")
		.append("FROM acceso a ").append("WHERE a.cod_usr_cia = ? ").toString();

	return jdbcTemplate.query(query, new Object[] { usrVal }, new OCmnAedSRowMapper());

    }

}
