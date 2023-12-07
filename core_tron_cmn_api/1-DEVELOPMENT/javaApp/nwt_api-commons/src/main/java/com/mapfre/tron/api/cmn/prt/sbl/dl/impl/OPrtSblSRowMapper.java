package com.mapfre.tron.api.cmn.prt.sbl.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.prt.sbl.bo.OPrtSblS;

/**
 * The PrtSblS row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 07:47:01
 *
 */
public class OPrtSblSRowMapper implements RowMapper<OPrtSblS> {

    @Override
    public OPrtSblS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OPrtSblS lvOPrtSblS = new OPrtSblS();
        lvOPrtSblS.setCmpVal(rs.getBigDecimal("cod_cia"));
        lvOPrtSblS.setSblVal(rs.getBigDecimal("num_subcontrato"));
        lvOPrtSblS.setSblNam(rs.getString("nom_subcontrato"));
        lvOPrtSblS.setSblShrNam(rs.getString("nom_cort_subcontrato"));
        lvOPrtSblS.setSblVldDat(rs.getDate("fec_actu"));

        return lvOPrtSblS;
    }

}
