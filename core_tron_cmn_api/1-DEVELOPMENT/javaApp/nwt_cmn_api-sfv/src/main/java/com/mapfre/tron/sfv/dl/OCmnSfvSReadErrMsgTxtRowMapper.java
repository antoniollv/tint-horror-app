package com.mapfre.tron.sfv.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.sfv.bo.OCmnSfvS;

/**
 * The ReadErrMsgTxt row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 may 2023 - 15:35:59
 *
 */
public class OCmnSfvSReadErrMsgTxtRowMapper implements RowMapper<OCmnSfvS> {

    @Override
    public OCmnSfvS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OCmnSfvS lvOCmnSfvS = new OCmnSfvS();
        lvOCmnSfvS.setIdnKey(rs.getString("idn_key"));
        lvOCmnSfvS.setSteIdn(rs.getString("ste_idn"));
        lvOCmnSfvS.setFldNam(rs.getString("fld_nam"));
        lvOCmnSfvS.setScrSci(rs.getString("scr_sci"));
        lvOCmnSfvS.setMsgVal(rs.getBigDecimal("msg_val"));
        lvOCmnSfvS.setLngVal(rs.getString("lng_val"));
        lvOCmnSfvS.setVldDat(rs.getDate("vld_dat"));
        lvOCmnSfvS.setCmpVal(rs.getBigDecimal("cmp_val"));
        lvOCmnSfvS.setMsgTxtVal(rs.getString("msg_txt_val"));
        lvOCmnSfvS.setDsbRow(rs.getString("dsb_row"));
        lvOCmnSfvS.setUsrVal(rs.getString("usr_val"));
        lvOCmnSfvS.setMdfDat(rs.getDate("mdf_dat"));

        return lvOCmnSfvS;
    }

}
