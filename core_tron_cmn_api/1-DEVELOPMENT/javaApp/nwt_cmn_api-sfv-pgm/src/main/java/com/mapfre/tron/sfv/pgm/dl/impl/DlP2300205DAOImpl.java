package com.mapfre.tron.sfv.pgm.dl.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.sfv.bo.OFldNamValDocQueryS;
import com.mapfre.tron.sfv.pgm.dl.IDlP2300205DAO;
import com.mapfre.tron.sfv.pgm.dl.mapper.FldDocQryPkDataQryRowMapper;
@Repository
public class DlP2300205DAOImpl implements IDlP2300205DAO {
	
	/** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
   	public List<OFldNamValDocQueryS> getDocNam(BigDecimal cmpVal, 
		       		              String     plyVal,
		       		              BigDecimal enrSqn, 
		       		              BigDecimal rskVal,
		       		              BigDecimal frlVal) {

           final String query = "SELECT P.COD_CAMPO, P.VAL_CAMPO as VAL_CAMPO  FROM P2300205 P WHERE P.COD_CIA= ? AND P.NUM_POLIZA= ? AND P.NUM_SPTO = ? AND P.NUM_RIESGO= ? AND P.COD_FORMULARIO= ?";
           
           return jdbcTemplate.query(
           		query, 
           		new Object[] {
           				cmpVal,
           				plyVal,
           				enrSqn,
           				rskVal,
           				frlVal}, 
           		new FldDocQryPkDataQryRowMapper());
   	}

	@Override
	public Integer getCountDocNam(BigDecimal cmpVal, String plyVal, BigDecimal enrSqn, BigDecimal rskVal,
			BigDecimal frlVal) {
		final String query = "SELECT SUM(1) FROM P2300205 P WHERE P.COD_CIA= ? AND P.NUM_POLIZA= ? AND P.NUM_SPTO = ? AND P.NUM_RIESGO= ? AND P.COD_FORMULARIO= ?";
        
        return jdbcTemplate.queryForObject(
        		query, 
        		new Object[] {
        				cmpVal,
        				plyVal,
        				enrSqn,
        				rskVal,
        				frlVal}, Integer.class);
	}

}
