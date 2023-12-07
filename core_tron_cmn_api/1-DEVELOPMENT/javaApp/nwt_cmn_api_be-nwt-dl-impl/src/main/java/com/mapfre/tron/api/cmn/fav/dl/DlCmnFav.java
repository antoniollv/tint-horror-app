package com.mapfre.tron.api.cmn.fav.dl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.tron.api.cmn.fav.bl.IDlCmnFav;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class DlCmnFav implements IDlCmnFav {

    
    @Autowired @Qualifier("jdbcFetch1000") protected NamedParameterJdbcTemplate jdbcTemplateAll;
    
    @Override
    public boolean delFav(BigDecimal cmpVal, String usrVal, String oprIdnVal) {
	
	    return jdbcTemplateAll.update("DELETE RL_CMN_NWT_XX_MEN_FAV " + 
	    	"WHERE cmp_val     = :cmpVal " + 
	    	"AND usr_val     = :usrVal " + 
	    	"AND opr_idn_val = :oprIdnVal", getParametersMap(cmpVal,usrVal,oprIdnVal)) == 1;
	
    }

    

    @Override
    public List<String> getFav(BigDecimal cmpVal, String usrVal) {
	try {
	    return jdbcTemplateAll.queryForList("SELECT f.OPR_IDN_VAL " + 
	    	"FROM RL_CMN_NWT_XX_MEN_FAV f " + 
	    	"WHERE f.cmp_val = :cmpVal " + 
	    	"AND f.usr_val = :usrVal", getParametersMap(cmpVal, usrVal), String.class);
	} catch (EmptyResultDataAccessException e) {
	    log.error("Error recovering favorites: {}", e);
	    throw new NwtException("Can't recover favorites");
	}
    }

    @Override
    public boolean putFav(BigDecimal cmpVal, String usrVal, String oprIdnVal) {
	return jdbcTemplateAll.update("INSERT INTO RL_CMN_NWT_XX_MEN_FAV (" +
		"CMP_VAL, " + 
		"USR_VAL, " + 
		"OPR_IDN_VAL, " + 
		"MDF_DAT ) " + 
		"VALUES (:cmpVal, " + 
		":usrVal, " + 
		":oprIdnVal, " + 
		"TO_DATE(:mdfDat, 'dd/mm/yyyy hh24:mi:ss'))", getParametersMap(cmpVal,usrVal,oprIdnVal, new Date())) == 1;
	
    }
    
    private Map<String,Object> getParametersMap(BigDecimal cmpVal, String usrVal, String oprIdnVal, Date mdfDat) {
	Map<String,Object> parameters=this.getParametersMap(cmpVal, usrVal, oprIdnVal);
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	parameters.put("mdfDat", dateFormat.format(mdfDat).toString());
	return parameters;
    }



    private Map<String,Object> getParametersMap(BigDecimal cmpVal, String usrVal){
	Map<String,Object> parameters = new HashMap<>();
	parameters.put("cmpVal", cmpVal);
	parameters.put("usrVal", usrVal);
	return parameters;
	
    }
    
    private Map<String,Object> getParametersMap(BigDecimal cmpVal, String usrVal, String oprIdnVal) {
	Map<String,Object> parameters = this.getParametersMap(cmpVal, usrVal);
	parameters.put("oprIdnVal", oprIdnVal);
	return parameters;
    }

}
