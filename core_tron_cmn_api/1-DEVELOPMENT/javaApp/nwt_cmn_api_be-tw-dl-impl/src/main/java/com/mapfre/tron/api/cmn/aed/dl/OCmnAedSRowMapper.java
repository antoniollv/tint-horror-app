package com.mapfre.tron.api.cmn.aed.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;

/**
 * The CmnAed row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 Oct 2023 - 17:59:59
 *
 */
public class OCmnAedSRowMapper implements RowMapper<OCmnAedS> {

    @Override
    public OCmnAedS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OCmnAedS lvOCmnAedS = new OCmnAedS();

        lvOCmnAedS.setCmpVal(rs.getBigDecimal("cod_cia"));
        lvOCmnAedS.setCmpUsrVal(rs.getString("cod_usr_cia"));
        lvOCmnAedS.setUsrNam(rs.getString("nom_usr_cia"));
        lvOCmnAedS.setFrsLvlVal(rs.getBigDecimal("cod_nivel1"));
        lvOCmnAedS.setFrsLvlNam(rs.getString("nom_nivel1"));
        lvOCmnAedS.setScnLvlVal(rs.getBigDecimal("cod_nivel2"));
        lvOCmnAedS.setScnLvlNam(rs.getString("nom_nivel2"));
        lvOCmnAedS.setThrLvlVal(rs.getBigDecimal("cod_nivel3"));
        lvOCmnAedS.setThrLvlNam(rs.getString("nom_nivel3"));
        lvOCmnAedS.setAgnVal(rs.getBigDecimal("cod_agt"));
        lvOCmnAedS.setAgnCpeNam(rs.getString("nom_completo"));
        lvOCmnAedS.setSecVal(rs.getBigDecimal("cod_sector"));
        lvOCmnAedS.setSecNam(rs.getString("nom_sector"));
        lvOCmnAedS.setSbsVal(rs.getBigDecimal("cod_subsector"));
        lvOCmnAedS.setSbsNam(rs.getString("nom_subsector"));
        lvOCmnAedS.setLobVal(rs.getBigDecimal("cod_ramo"));
        lvOCmnAedS.setLobNam(rs.getString("nom_ramo"));
        lvOCmnAedS.setAgnEmpVal(rs.getBigDecimal("cod_emp_agt"));
        lvOCmnAedS.setAgnEmpCpeNam(rs.getString("nom_completo"));

        return lvOCmnAedS;
    }

}
