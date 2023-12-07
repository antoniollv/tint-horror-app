package com.mapfre.tron.api.cmn.men.dl;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OCmnMenRowMapper extends NewtronRowMapper<OCmnMenS> {

    @Override
    public OCmnMenS mapRow(ResultSet rs, int rowNum) throws SQLException {
	OCmnMenS s = new OCmnMenS();
	s.setCmpVal(rs.getBigDecimal("cmp_val"));
	s.setAgrValPrn(rs.getString("agr_val_prn"));
	s.setOprIdnVal(rs.getString("opr_idn_val"));
	s.setOprIdnNam(rs.getString("txt_nam"));
	s.setFunOprUrl(rs.getString("app_url"));
	s.setPgmDsb(rs.getString("pgm_dsb"));
	s.setMenSqnVal(rs.getBigDecimal("men_sqn_val"));
	s.setPgmVal(rs.getString("pgm_val"));
	s.setTskVal(rs.getString("tsk_val"));
	return s;
    }

}
