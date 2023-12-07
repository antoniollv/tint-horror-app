package com.mapfre.tron.api.thp.usr.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.nwt.trn.thp.usr.bo.OThpUsrS;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Repository
@Data
@Slf4j
public class OThpUsrDaoImpl implements OThpUsrDao {

    
    private static final String QUERY1 = "SELECT a.cod_cia, " +
        				        "a.cod_usr_cia, " + 
    					        "a.num_usr_cia, " + 
    					        "a.nom_usr_cia, " + 
    					        "a.cod_nivel3, " + 
    					        "a.menu_defecto, " + 
    					        "a.cod_grp_usr, " + 
    					        "a.mca_inf_parcial, " + 
    					        "b.cod_idioma, " + 
    					        "a.tip_docum, " + 
    					        "a.cod_docum " + 
    					"FROM g1002700 a, g1010120 b ";
    private static final String RESTRICTION1 =
    					"WHERE a.cod_cia     = (SELECT txt_valor_variable " + 
    							       "FROM (SELECT cod_usr, " + 
    							       		    "cod_grupo, " + 
    							       		    "txt_nombre_variable, " + 
    							       		    "txt_valor_variable, " + 
    							       		    "ROW_NUMBER() OVER(PARTITION BY 1 ORDER BY DECODE(COD_USR, 'DEFECTO', 1, 0) ASC) AS ORDEN " + 
    							       	     "FROM G1010107 " + 
    							       	     "WHERE cod_usr in (:usrVal, 'DEFECTO') " + 
    							       	     "AND txt_nombre_variable = 'COD_CIA') " + 
    							       "WHERE ORDEN = 1) " + 
    					"AND a.cod_usr_cia = :usrVal " + 
    					"AND a.cod_usr_cia = b.cod_usr"; 
    
    private static final String QUERY2 = "SELECT g.cod_cia, " + 
    					 "g.cod_nivel3, " + 
    					 "g.nom_nivel3, " + 
    					 "g.abr_nivel3, " + 
    					 "g.cod_nivel1, " + 
    					 "g.cod_nivel2, " + 
    					 "g.obs_nivel3, " + 
    					 "g.mca_inh, " + 
    					 "g.mca_emision, " + 
    					 "g.tip_distribucion, " + 
    					 "g.fec_apertura, " + 
    					 "g.mca_propia " + 
    			          "FROM a1000702 g ";
    private static final String RESTRICTION2 = 
    				  "WHERE g.cod_cia    = :cmpVal " + 
    				  "AND g.cod_nivel3 = :thrLvlVal";

	private static final String QUERY3 = "SELECT g.txt_usuario_bbdd " + 
										 "FROM g1010108 g ";

	private static final String RESTRICTION3 = "WHERE g.cod_saptron = :usrVal " +
										"AND g.cod_cia = (SELECT txt_valor_variable " + 
    							       "FROM (SELECT cod_usr, " + 
    							       		    "cod_grupo, " + 
    							       		    "txt_nombre_variable, " + 
    							       		    "txt_valor_variable, " + 
    							       		    "ROW_NUMBER() OVER(PARTITION BY 1 ORDER BY DECODE(COD_USR, 'DEFECTO', 1, 0) ASC) AS ORDEN " + 
    							       	     "FROM G1010107 " + 
    							       	     "WHERE cod_usr in (NVL(g.txt_usuario_bbdd,:usrVal), 'DEFECTO') " +
    							       	     "AND txt_nombre_variable = 'COD_CIA') " + 
    							       "WHERE ORDEN = 1)";
										    
    private static final OThpUsrG1002700G1010120RowMapper MAPPER1 = new OThpUsrG1002700G1010120RowMapper();
    private static final OThpUsrA1000702RowMapper MAPPER2 = new OThpUsrA1000702RowMapper();
    @Autowired @Qualifier("jdbcFetch1") protected NamedParameterJdbcTemplate jdbcTemplateOne;
    
    
    @Override
    public OThpUsrS get(OThpUsrPK o) {
	// Consulta el usuario de bbdd para este usuario
	String dbUserName = this.queryUserDb(o.asMap());

	// Si existe, reemplazamos la consulta por este.
	if (dbUserName != null)
		o.setUsrVal(dbUserName);

	// Realizamos consulta para este user.
	return this.queryUserData(o.asMap());

    }

    private OCmuThlS queryThirdLevelCommercialStructureDesc(Map<String, Object> parametersDesc) {
	MapUtils.debugPrint(System.out, "Parameters Map", parametersDesc);
	return jdbcTemplateOne.queryForObject(QUERY2 + RESTRICTION2, parametersDesc, MAPPER2);
	
    }

    private Map<String, Object> createParametersDesc(OThpUsrS userData) {
	Map<String,Object> cmuThlParameters = new HashMap<>();
	cmuThlParameters.put("cmpVal", userData.getCmpVal());
	cmuThlParameters.put("thrLvlVal", userData.getThrLvlVal());
	return cmuThlParameters;
    }

    private Map<String, Object> createParametersCodTercero(OThpUsrS userData) {
	Map<String,Object> codTerceroParameters = new HashMap<>();
	codTerceroParameters.put("cmpVal", userData.getCmpVal());
	codTerceroParameters.put("thpDcmTypVal", userData.getThpDcmTypVal());
	codTerceroParameters.put("thpDcmVal", userData.getThpDcmVal());
	
	return codTerceroParameters;
    }

    @Override
    public BigDecimal getCodTercero(OThpUsrS userData) {
	return this.queryCodTercero(this.createParametersCodTercero(userData));
    }
    
    private BigDecimal queryCodTercero(Map<String, Object> codTerceroParameters) {
	return jdbcTemplateOne.queryForObject("SELECT COD_TERCERO " + 
		"FROM v1001390 " + 
		"WHERE cod_cia = :cmpVal " + 
		"AND cod_act_tercero = 2 " + 
		"AND tip_docum = :thpDcmTypVal " + 
		"AND cod_docum = :thpDcmVal", codTerceroParameters, BigDecimal.class);
    }

    private OThpUsrS queryUserData(Map<String, Object> propertiesMap) {
	return jdbcTemplateOne.queryForObject(QUERY1 + RESTRICTION1, propertiesMap, MAPPER1);
    }

	private String queryUserDb(Map<String,Object> userNameParameter) { 
		return jdbcTemplateOne.queryForObject(QUERY3 + RESTRICTION3, userNameParameter, String.class);
	}

    @Override
    public String getDescription(OThpUsrS o) {
	log.info(Objects.toString(o));
	OCmuThlS oCmuThlS = this.queryThirdLevelCommercialStructureDesc(this.createParametersDesc(o));
	return oCmuThlS.getThrLvlNam();
    }

    @Override
    public String getAbr(OThpUsrS o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public List<OThpUsrS> getAll() {
	throw new UnsupportedOperationException();
    }

    @Override
    public OThpUsrS save(OThpUsrS o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public int delete(OThpUsrPK o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public OThpUsrS update(OThpUsrS o) {
	throw new UnsupportedOperationException(); 
    }
}
