package com.mapfre.tron.sfv.pgm.dl.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.sfv.bo.Clausula;
import com.mapfre.tron.sfv.pgm.dl.IDlA9990011DAO;
import com.mapfre.tron.sfv.pgm.dl.mapper.ClausulaRowMapper;

@Repository
public class DlA9990011DAOImpl implements IDlA9990011DAO {
	
	/** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override @Cacheable("Sfv-Pgm-clausulas")
	public List<Clausula> getClausula(BigDecimal cmpVal, String codClausula) {
		final String sql = new StringBuilder()
                .append("SELECT a.TXT_CLAUSULA, a.NUM_SECU FROM A9990011 a WHERE a.COD_CIA = ? AND a.COD_CLAUSULA = ? ORDER BY NUM_SECU ASC")
                .toString();

        return jdbcTemplate.query(sql, new Object[] {
        		cmpVal,
        		codClausula
        }, new ClausulaRowMapper());
	}

}
