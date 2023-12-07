package com.mapfre.tron.sfv.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.sfv.bo.OCmnSfvS;

/**
 * The OCmnSfvS row mapper for ReadParamProgram query.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 8 May 2023 - 16:31:46
 *
 */
public class OCmnSfvSReadParamProgramRowMapper implements RowMapper<OCmnSfvS> {

    @Override
    public OCmnSfvS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OCmnSfvS lvOCmnSfvS = new OCmnSfvS();
        lvOCmnSfvS.setIdnKey(rs.getString("idn_key"));
        lvOCmnSfvS.setSteIdn(rs.getString("ste_idn"));
        lvOCmnSfvS.setFldNam(rs.getString("fld_nam"));
        lvOCmnSfvS.setScrSci(rs.getString("scr_sci"));
        lvOCmnSfvS.setPgmExnOrd(rs.getBigDecimal("pgm_exn_ord"));
        lvOCmnSfvS.setPgmTypVal(rs.getString("pgm_typ_val"));
        lvOCmnSfvS.setVldDat(rs.getDate("vld_dat"));
        lvOCmnSfvS.setCmpVal(rs.getBigDecimal("cmp_val"));
        lvOCmnSfvS.setPgmNam(rs.getString("pgm_nam"));
        lvOCmnSfvS.setDsbRow(rs.getString("dsb_row"));
        lvOCmnSfvS.setUsrVal(rs.getString("usr_val"));
        lvOCmnSfvS.setMdfDat(rs.getDate("mdf_dat"));
        lvOCmnSfvS.setMnrCpoVal(rs.getString("mnr_cpo_val"));
        return lvOCmnSfvS;
    }


}
