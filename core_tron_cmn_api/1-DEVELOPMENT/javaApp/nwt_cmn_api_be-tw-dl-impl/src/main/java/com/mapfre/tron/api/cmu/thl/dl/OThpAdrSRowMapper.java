package com.mapfre.tron.api.cmu.thl.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;

/**
 * The OThpAdrS row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 22 Nov 2022 - 16:15:12
 *
 */
public class OThpAdrSRowMapper implements RowMapper<OThpAdrS> {

    @Override
    public OThpAdrS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OThpAdrS lvOThpAdrS = new OThpAdrS();

        lvOThpAdrS.setCmpVal(rs.getBigDecimal("cmp_val"));
        lvOThpAdrS.setThpDcmTypVal(rs.getString("thp_dcm_typ_val"));
        lvOThpAdrS.setThpDcmVal(rs.getString("thp_dcm_val"));
        lvOThpAdrS.setThpAcvVal(rs.getBigDecimal("thp_acv_val"));
        lvOThpAdrS.setIdnThpVal(rs.getString("idn_thp_val"));
        lvOThpAdrS.setAdrSqnVal(rs.getBigDecimal("adr_sqn_val"));
        lvOThpAdrS.setDmlTypVal(rs.getString("dml_typ_val"));
        lvOThpAdrS.setAdrTxtVal(rs.getString("adr_txt_val"));
        lvOThpAdrS.setAdrNbrVal(rs.getBigDecimal("adr_nbr_val"));
        lvOThpAdrS.setExtAdrTxtVal(rs.getString("ext_adr_txt_val"));
        lvOThpAdrS.setExtCnyTxtVal(rs.getString("ext_cny_txt_val"));
        lvOThpAdrS.setCnyVal(rs.getString("cny_val"));
        lvOThpAdrS.setSttVal(rs.getBigDecimal("stt_val"));
        lvOThpAdrS.setPvcVal(rs.getBigDecimal("pvc_val"));
        lvOThpAdrS.setTwnVal(rs.getBigDecimal("twn_val"));
        lvOThpAdrS.setPslCodVal(rs.getString("psl_cod_val"));
        lvOThpAdrS.setObsVal(rs.getString("obs_val"));
        lvOThpAdrS.setDsbRow(rs.getString("dsb_row"));
        lvOThpAdrS.setUsrVal(rs.getString("usr_val"));
        lvOThpAdrS.setVldDat(rs.getDate("vld_dat"));
        lvOThpAdrS.setLntVal(rs.getString("lnt_val"));
        lvOThpAdrS.setLttVal(rs.getString("ltt_val"));
        lvOThpAdrS.setMdfDat(rs.getDate("mdf_dat"));
        lvOThpAdrS.setDflAdr(rs.getString("dfl_adr"));
        lvOThpAdrS.setAdrCck(rs.getString("adr_cck"));
        lvOThpAdrS.setAdrUseTypVal(rs.getString("adr_use_typ_val"));
        lvOThpAdrS.setDitVal(rs.getString("dit_val"));

        return lvOThpAdrS;
    }

}
