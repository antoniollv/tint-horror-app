package com.mapfre.tron.api.trn.syp.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mapfre.nwt.trn.trn.syp.bo.OTrnSypS;

public class OTrnSypRowMapper implements RowMapper<OTrnSypS>{
    @Override
    public OTrnSypS mapRow(ResultSet rs, int rowNum) throws SQLException {

	OTrnSypS lvOTrnSypS = new OTrnSypS();

	lvOTrnSypS.setStuVal(rs.getString("COD_EST"));
	lvOTrnSypS.setStuNam(rs.getString("NOM_EST"));
	lvOTrnSypS.setPgmVal(rs.getString("COD_PGM"));
	lvOTrnSypS.setPgmNam(rs.getString("NOM_PRG"));
	lvOTrnSypS.setLgcCncTypVal(rs.getString("TIP_CTO_LOGICO"));
	lvOTrnSypS.setLodDynNwt(rs.getString("NOM_PRG_CARGA_EST_NWT"));
	lvOTrnSypS.setUrlPss(rs.getString("URL_TRATAR"));
	lvOTrnSypS.setRmeDynNwt(rs.getString("NOM_PRG_ELIMINAR"));
	lvOTrnSypS.setTnfDynNwt(rs.getString("NOM_PRG_TRASPASAR"));
	lvOTrnSypS.setShwDynNwt(rs.getString("NOM_PRG_MOSTRAR"));
	lvOTrnSypS.setPgmDynNwt(rs.getString("NOM_PRG_NWT"));
	lvOTrnSypS.setFunOprTypVal(rs.getString("TIP_OPR_FUNCIONAL"));


	return lvOTrnSypS;
    }
}
