package com.mapfre.tron.api.crn.exr.dl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.crn.exr.bo.OCrnExrP;
import com.mapfre.nwt.trn.crn.exr.bo.OCrnExrS;

public class OCrnExrRowMapper implements RowMapper<OCrnExrP>{

    @Override
    public OCrnExrP mapRow(ResultSet rs, int rowNum) throws SQLException {

        OCrnExrS oCrnExrS = new OCrnExrS();

        oCrnExrS.setCrnVal(rs.getBigDecimal("COD_MON"));
        oCrnExrS.setExrDat(rs.getDate("FEC_CAMBIO"));
        oCrnExrS.setExrVal(rs.getBigDecimal("VAL_CAMBIO"));
        OCrnExrP oCrnExrP = new OCrnExrP();
        oCrnExrP.setOCrnExrS(oCrnExrS);

        return oCrnExrP;
    }

}
