package com.mapfre.tron.api.acd.acc.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.acd.acc.bo.OAcdAccS;

/**
 * 
 * @author lruizg1
 *
 */
public class OAcdAccRowMapper implements RowMapper<OAcdAccS> {

    @Override
    public OAcdAccS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OAcdAccS lvOAcdAccS = new OAcdAccS();
		lvOAcdAccS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        lvOAcdAccS.setAcgGrgVal(rs.getString("COD_AGRUP_CTABLE"));
        lvOAcdAccS.setCloPymCncTypVal(rs.getString("COD_CTO_CTABLE"));
        lvOAcdAccS.setCloPymCncNam(rs.getString("NOM_CTO_CTABLE"));
        lvOAcdAccS.setCloPymCncShrNam(rs.getString("NOM_COR_CTO"));
        lvOAcdAccS.setDsbRow(rs.getString("MCA_INH"));
        lvOAcdAccS.setDsbDat(rs.getDate("FEC_INH"));
        lvOAcdAccS.setUsrVal(rs.getString("COD_USR"));
        lvOAcdAccS.setMdfDat(rs.getDate("FEC_ACTU"));

	return lvOAcdAccS;
    }

}
