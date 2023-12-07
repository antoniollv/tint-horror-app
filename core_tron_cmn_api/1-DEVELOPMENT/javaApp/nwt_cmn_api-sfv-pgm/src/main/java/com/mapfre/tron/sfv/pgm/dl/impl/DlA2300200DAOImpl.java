package com.mapfre.tron.sfv.pgm.dl.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.sfv.bo.OFlrDfn;
import com.mapfre.tron.sfv.pgm.dl.IDlA2300200DAO;

@Repository
public class DlA2300200DAOImpl implements IDlA2300200DAO {

	/** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
	@Override @Cacheable("Sfv-Pgm-CuestionarioDef")
	public OFlrDfn getFrlDfn(BigDecimal cmpVal, BigDecimal frlVal) {
		final String query = "SELECT COD_CIA, COD_FORMULARIO, NOM_FORMULARIO, NOM_CORT_FORMULARIO, NOM_PRG_PRE_FORMULARIO FROM A2300200 P WHERE P.COD_CIA= ? AND P.COD_FORMULARIO= ?";
        
        return jdbcTemplate.queryForObject(
        		query, 
        		new Object[] {cmpVal, frlVal}, new RowMapper<OFlrDfn>() {

					@Override
					public OFlrDfn mapRow(ResultSet rs, int rowNum) throws SQLException {
						OFlrDfn o = new OFlrDfn();
						o.setCmpVal(rs.getBigDecimal("COD_CIA"));
						o.setFrlVal(rs.getBigDecimal("COD_FORMULARIO"));
						o.setFrlNam(rs.getString("NOM_FORMULARIO"));
						o.setFrlShtNam(rs.getString("NOM_CORT_FORMULARIO"));
						o.setPgmNam(rs.getString("NOM_PRG_PRE_FORMULARIO"));
						return o;
					}
        			
        		});
	}

}
