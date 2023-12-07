package com.mapfre.tron.sfv.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.sfv.bo.OCmnSfvS;

/**
 * The OCmnSfvS row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 8 May 2023 - 16:31:46
 *
 */
public class OCmnSfvSInStreamIdnKeyDataQryRowMapper implements RowMapper<OCmnSfvS> {

    @Override
    public OCmnSfvS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OCmnSfvS lvOCmnSfvS = new OCmnSfvS();
        lvOCmnSfvS.setIdnKey(rs.getString("flw_idn"));
        lvOCmnSfvS.setFrsLvlVal(rs.getBigDecimal("frs_lvl_val"));
        lvOCmnSfvS.setScnLvlVal(rs.getBigDecimal("scn_lvl_val"));
        lvOCmnSfvS.setThrLvlVal(rs.getBigDecimal("thr_lvl_val"));
        lvOCmnSfvS.setFrsDstHnlVal(rs.getString("frs_dst_hnl_val"));
        lvOCmnSfvS.setScnDstHnlVal(rs.getString("scn_dst_hnl_val"));
        lvOCmnSfvS.setThrDstHnlVal(rs.getString("thr_dst_hnl_val"));
        lvOCmnSfvS.setAgnVal(rs.getBigDecimal("agn_val"));
        lvOCmnSfvS.setSecVal(rs.getBigDecimal("sec_val"));
        lvOCmnSfvS.setSbsVal(rs.getBigDecimal("sbs_val"));
        lvOCmnSfvS.setDelVal(rs.getBigDecimal("del_val"));
        lvOCmnSfvS.setSblVal(rs.getBigDecimal("sbl_val"));
        lvOCmnSfvS.setIdnKey(rs.getString("idn_key"));
        lvOCmnSfvS.setVldDat(rs.getDate("vld_dat"));
        lvOCmnSfvS.setCmpVal(rs.getBigDecimal("cmp_val"));
        lvOCmnSfvS.setDsbRow(rs.getString("dsb_row"));
        lvOCmnSfvS.setUsrVal(rs.getString("usr_val"));
        lvOCmnSfvS.setMdfDat(rs.getDate("mdf_dat"));

        return lvOCmnSfvS;
    }


}
