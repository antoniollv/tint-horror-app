package com.mapfre.tron.api.cmn.mvd.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.mvd.bo.OCmnMvdS;

public class OCmnMvdSRowMapper implements RowMapper<OCmnMvdS> {

    @Override
    public OCmnMvdS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OCmnMvdS lvOCmnMvdS = new OCmnMvdS();

        lvOCmnMvdS.setCmpVal(rs.getBigDecimal("cmp_val"));
        lvOCmnMvdS.setMvmIdn(rs.getString("mvm_idn"));
        lvOCmnMvdS.setMvmTrn(rs.getString("mvm_trn"));
        lvOCmnMvdS.setGnrCmq(rs.getString("gnr_cmq"));
        lvOCmnMvdS.setCmiTypVal(rs.getString("cmi_typ_val"));
        lvOCmnMvdS.setAlrCmiHnl(rs.getString("alr_cmi_hnl"));
        lvOCmnMvdS.setMvmPbl(rs.getString("mvm_pbl"));
        lvOCmnMvdS.setMvmTypVal(rs.getString("mvm_typ_val"));
        lvOCmnMvdS.setDsbRow(rs.getString("dsb_row"));
        lvOCmnMvdS.setUsrVal(rs.getString("usr_val"));
        lvOCmnMvdS.setMdfDat(rs.getDate("mdf_dat"));
        lvOCmnMvdS.setVldDat(rs.getDate("vld_dat"));
        lvOCmnMvdS.setNteVal(rs.getString("nte_val"));
        lvOCmnMvdS.setNteClfVal(rs.getString("nte_clf_val"));

        return lvOCmnMvdS;
    }

}
