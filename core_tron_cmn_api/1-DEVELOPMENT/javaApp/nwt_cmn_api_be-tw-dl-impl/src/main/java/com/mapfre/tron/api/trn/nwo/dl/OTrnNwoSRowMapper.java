package com.mapfre.tron.api.trn.nwo.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;

/**
 * OTrnNwoSRowMapper
 *
 * @author Cristian Saball
 * @since 1.8
 * @version 07 feb. 2022 14:31:33
 *
 */
public class OTrnNwoSRowMapper implements RowMapper<OTrnNwoS> {

    @Override
    public OTrnNwoS mapRow(ResultSet rs, int rowNum) throws SQLException {

	
	OTrnNwoS oTrnNwoS = new OTrnNwoS();
	oTrnNwoS.setOprIdnVal(rs.getString("opr_idn_val"));
	oTrnNwoS.setOprIdnNam(rs.getString("opr_idn_nam"));
	oTrnNwoS.setMolVal(rs.getString("mol_val"));
	
	return oTrnNwoS;
    }

}
