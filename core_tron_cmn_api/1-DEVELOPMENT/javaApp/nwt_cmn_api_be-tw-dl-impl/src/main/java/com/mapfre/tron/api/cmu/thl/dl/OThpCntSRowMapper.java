package com.mapfre.tron.api.cmu.thl.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

/**
 * The ThpCnt row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 22 Nov 2022 - 07:47:22
 *
 */
public class OThpCntSRowMapper implements RowMapper<OThpCntS> {

    @Override
    public OThpCntS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OThpCntS lvOThpCntS = new OThpCntS();

        lvOThpCntS.setCmpVal(rs.getBigDecimal("cmp_val"));
        lvOThpCntS.setThpDcmTypVal(rs.getString("thp_dcm_typ_val"));
        lvOThpCntS.setThpDcmVal(rs.getString("thp_dcm_val"));
        lvOThpCntS.setThpAcvVal(rs.getBigDecimal("thp_acv_val"));
        lvOThpCntS.setIdnThpVal(rs.getString("idn_thp_val"));
        lvOThpCntS.setCnhTypVal(rs.getString("cnh_typ_val"));
        lvOThpCntS.setPstTypVal(rs.getString("pst_typ_val"));
        lvOThpCntS.setDsbRow(rs.getString("dsb_row")); 
        lvOThpCntS.setUsrVal(rs.getString("usr_val"));
        lvOThpCntS.setObsVal(rs.getString("obs_val"));
        lvOThpCntS.setMdfDat(rs.getDate("mdf_dat"));
        lvOThpCntS.setVldDat(rs.getDate("vld_dat"));
        lvOThpCntS.setCnhUseTypVal(rs.getString("cnh_use_typ_val"));
        lvOThpCntS.setCnhTxtVal(rs.getString("cnh_txt_val"));
        lvOThpCntS.setCnhSqnVal(rs.getBigDecimal("cnh_sqn_val"));
        lvOThpCntS.setCnhCck(rs.getString("cnh_cck"));
        lvOThpCntS.setExtCntDprVal(rs.getString("ext_cnt_dpr_val"));
        lvOThpCntS.setCnhNam(rs.getString("cnh_nam"));
        lvOThpCntS.setDflCnh(rs.getString("dfl_cnh"));
        lvOThpCntS.setPtyCnh(rs.getString("pty_cnh"));
        lvOThpCntS.setCntPstVal(rs.getString("cnt_pst_val"));
        lvOThpCntS.setCntDprVal(rs.getString("cnt_dpr_val"));
        lvOThpCntS.setCntFrsScnSrn(rs.getString("cnt_frs_scn_srn"));
        lvOThpCntS.setCntDcmTypVal(rs.getString("cnt_dcm_typ_val"));
        lvOThpCntS.setCntDcmVal(rs.getString("cnt_dcm_val"));
        lvOThpCntS.setCntCnyVal(rs.getString("cnt_cny_val"));

        return lvOThpCntS;
    }

}
