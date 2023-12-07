package com.mapfre.tron.api.thp.usr.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.mapfre.nwt.trn.thp.usr.bo.OThpUsrS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OThpUsrG1002700G1010120RowMapper extends NewtronRowMapper<OThpUsrS> {

    @Override
    public OThpUsrS mapRow(ResultSet rs, int rowNum) throws SQLException {
	
	
	OThpUsrS s = new OThpUsrS();
	s.setCmpVal(rs.getBigDecimal("cod_cia"));
	s.setUsrVal(rs.getString("cod_usr_cia"));
	s.setUsrNmbVal(rs.getBigDecimal("num_usr_cia"));
	s.setUsrNam(rs.getString("nom_usr_cia"));
	s.setThrLvlVal(rs.getBigDecimal("cod_nivel3"));
	s.setDflMen(rs.getString("menu_defecto"));
	s.setUsrGrpVal(rs.getString("cod_grp_usr"));
	s.setPtlInfQry(rs.getString("mca_inf_parcial"));
	s.setLngVal(rs.getString("cod_idioma"));
	
	String tipDocum = rs.getString("tip_docum");
	if (tipDocum != null)
	    s.setThpDcmTypVal(rs.getString("tip_docum"));
	
	String codDocum = rs.getString("cod_docum");
	if (codDocum != null) 
	    s.setThpDcmVal(rs.getString("cod_docum"));
	
	return s;
    }

}
