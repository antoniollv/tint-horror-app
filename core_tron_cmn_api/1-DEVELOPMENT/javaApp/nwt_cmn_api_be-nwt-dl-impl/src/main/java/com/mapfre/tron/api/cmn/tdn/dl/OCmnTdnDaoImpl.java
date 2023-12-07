package com.mapfre.tron.api.cmn.tdn.dl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.ins.c.CInsCmp;
import com.mapfre.nwt.ins.c.CInsConstant;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class OCmnTdnDaoImpl implements OCmnTdnDao {

    
    @Value("${default.app.language}")	
    private String defaultLangApp;
    
    /** cmpVal default value */
    @Value("${default.app.cmpVal}")
    public String cmpValDefault;
    
    private static final String QUERY = "SELECT LBL_NAM, LBL_TXT " + 
    	"FROM " + 
    	"(SELECT t.*, ROW_NUMBER() OVER (PARTITION BY LBL_NAM ORDER BY case when (t.ins_val = 'TRN' and lng_tag_val = :DEFAULT_LANG_APP) THEN 2" +
    									   "when (t.ins_val = 'TRN' and lng_tag_val = :idioma) THEN 1" +	
    									   "ELSE 0 END ASC) AS ORDEN " + 
    	"FROM DF_TRN_NWT_XX_TXT_LBL t " + 
    	"WHERE (t.ins_val, t.lng_tag_val) in (('TRN',:idioma),(:insVal, :idioma),('TRN',:DEFAULT_LANG_APP))) " + 
    	"WHERE ORDEN = 1";
    
    @Autowired @Qualifier("jdbcFetch1000") protected NamedParameterJdbcTemplate jdbcTemplateAll;
    @Autowired @Qualifier("jdbcFetch1") protected NamedParameterJdbcTemplate jdbcTemplateOne;
    
    public Map<String,String> get(String idioma) {
	
	Map<String,String> m = new HashMap<>();
	try {
	    m = jdbcTemplateAll.query(QUERY, this.getParametersAsMap(defaultLangApp, idioma, CInsCmp.get(CInsConstant.INS_VAL, new BigDecimal(cmpValDefault))), new ResultSetExtractor<Map<String,String>>(){
		@Override
		public Map<String,String> extractData(ResultSet rs)
		throws SQLException, DataAccessException {
		    Map<String,String> tagsMap = new HashMap<>();
		    while (rs.next()) {
			tagsMap.put(rs.getString("LBL_NAM"), rs.getString("LBL_TXT"));
		    }
		    return tagsMap;
		} 
	    });
	} catch (Exception e) {
	    log.info("Can't recover jade tags \n" + e.getMessage());
	}
	
	return m;
    }

    private Map<String,String> getParametersAsMap(String defaultLangApp, String idioma, String insVal) {
	Map<String,String> m = new HashMap<>();
	m.put("DEFAULT_LANG_APP", defaultLangApp);
	m.put("idioma", idioma);
	m.put("insVal", insVal);
	return m;
    }
    
}
