package com.mapfre.tron.api.cmu.cmp.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;

import lombok.Data;

@Repository
@Data
public class OCmuCmpDaoImpl implements OCmuCmpDao {

    private static final String QUERY2 = "SELECT " + 
    					"a.nom_cia " + 
    					"FROM a1000900 a ";
    private static final String RESTRICTION2 =
    				 "WHERE cod_cia = :cmpVal";
    
    private static final String QUERY = "SELECT  a.cod_cia " + 
    						
    					"FROM g1002700 a ";
    private static final String RESTRICTION =
    					"WHERE a.cod_usr_cia = :usrVal";
    
    private static final OCmuCmpRowMapper MAPPER = new OCmuCmpRowMapper();
    
    @Autowired @Qualifier("jdbcFetch1") protected NamedParameterJdbcTemplate jdbcTemplateOne;
    @Autowired @Qualifier("jdbcFetch1000") protected NamedParameterJdbcTemplate jdbcTemplateAll;
    
    @Override
    public OCmuCmpS get(OCmuCmpPK o) {
	throw new UnsupportedOperationException();
    }


    @Override
    public String getDescription(OCmuCmpS o) {
	return jdbcTemplateOne.queryForObject(QUERY2 + RESTRICTION2, this.getParamsDesc(o.getCmpVal()), String.class);
    }


    private Map<String,Object> getParamsDesc(BigDecimal cmpVal) {
	Map<String,Object> parametersMap = new HashMap<>();
	parametersMap.put("cmpVal", cmpVal);
	return parametersMap;
    }


    @Override
    public String getAbr(OCmuCmpS o) {
	throw new UnsupportedOperationException();
    }


    @Override
    public List<OCmuCmpS> getAll() {
	throw new UnsupportedOperationException();
    }


    @Override
    public OCmuCmpS save(OCmuCmpS o) {
	throw new UnsupportedOperationException();
    }


    @Override
    public int delete(OCmuCmpPK o) {
	throw new UnsupportedOperationException();
    }


    @Override
    public OCmuCmpS update(OCmuCmpS o) {
	throw new UnsupportedOperationException();
    }


    @Override
    public List<OCmuCmpS> getAll(String usrVal) {
	return jdbcTemplateAll.query(QUERY + RESTRICTION, this.getParams(usrVal), MAPPER);
    }


    private Map<String,Object> getParams(String usrVal) {
	Map<String,Object> parametersMap = new HashMap<>();
	parametersMap.put("usrVal", usrVal);
	return parametersMap;
    }
}
