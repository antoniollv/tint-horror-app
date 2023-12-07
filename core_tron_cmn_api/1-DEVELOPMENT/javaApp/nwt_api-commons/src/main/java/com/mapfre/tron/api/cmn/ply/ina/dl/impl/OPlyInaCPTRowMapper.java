package com.mapfre.tron.api.cmn.ply.ina.dl.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaCPT;
import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaP;
import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaS;

public class OPlyInaCPTRowMapper implements RowMapper<OPlyInaCPT> {

    @Override
    public OPlyInaCPT mapRow(ResultSet rs, int rowNum) throws SQLException {
        OPlyInaCPT oPlyInaCPT = new OPlyInaCPT();
        List<OPlyInaP> oPlyInaPT = new ArrayList<>();

        final BigDecimal cmpVal = rs.getBigDecimal("COD_CIA");
        final String plyVal = rs.getString("NUM_POLIZA");
        final BigDecimal enrSqn = rs.getBigDecimal("NUM_SPTO");
        final BigDecimal aplVal = rs.getBigDecimal("NUM_APLI");
        final BigDecimal aplEnrSqn = rs.getBigDecimal("NUM_SPTO_APLI");

        // Element 01
        OPlyInaP oPlyInaP01 = new OPlyInaP();
        OPlyInaS oPlyInaS01 = new OPlyInaS();
        oPlyInaS01.setCmpVal(cmpVal);
        oPlyInaS01.setPlyVal(plyVal);
        oPlyInaS01.setEnrSqn(enrSqn);
        oPlyInaS01.setAplVal(aplVal);
        oPlyInaS01.setAplEnrSqn(aplEnrSqn);
        oPlyInaS01.setItcVal(rs.getString("CONSTANTE1"));
        oPlyInaS01.setThpVal(rs.getBigDecimal("COD_AGT"));
        
        oPlyInaS01.setParPer(rs.getBigDecimal("PCT_AGT"));
        oPlyInaS01.setCmcVal(rs.getBigDecimal("COD_CUADRO_COM"));
        oPlyInaS01.setFrsLvlVal(rs.getBigDecimal("COD_NIVEL1"));
        oPlyInaS01.setScnLvlVal(rs.getBigDecimal("COD_NIVEL2"));
        oPlyInaS01.setThrLvlVal(rs.getBigDecimal("COD_NIVEL3"));


        oPlyInaS01.setFrsDstHnlVal(rs.getString("COD_CANAL1"));
        oPlyInaS01.setScnDstHnlVal(rs.getString("COD_CANAL2"));
        oPlyInaS01.setThrDstHnlVal(rs.getString("COD_CANAL3"));

        oPlyInaP01.setOPlyInaS(oPlyInaS01);
        oPlyInaPT.add(oPlyInaP01);

        // Element 02
        OPlyInaP oPlyInaP02 = new OPlyInaP();
        OPlyInaS oPlyInaS02 = new OPlyInaS();
        oPlyInaS02.setCmpVal(cmpVal);
        oPlyInaS02.setPlyVal(plyVal);
        oPlyInaS02.setEnrSqn(enrSqn);
        oPlyInaS02.setAplVal(aplVal);
        oPlyInaS02.setAplEnrSqn(aplEnrSqn);
        oPlyInaS02.setItcVal(rs.getString("CONSTANTE2"));
        oPlyInaS02.setParPer(rs.getBigDecimal("PCT_AGT2"));

        oPlyInaP02.setOPlyInaS(oPlyInaS02);
        oPlyInaPT.add(oPlyInaP02);

        // Element 03
        OPlyInaP oPlyInaP03 = new OPlyInaP();
        OPlyInaS oPlyInaS03 = new OPlyInaS();
        oPlyInaS03.setCmpVal(cmpVal);
        oPlyInaS03.setPlyVal(plyVal);
        oPlyInaS03.setEnrSqn(enrSqn);
        oPlyInaS03.setAplVal(aplVal);
        oPlyInaS03.setAplEnrSqn(aplEnrSqn);
        oPlyInaS03.setItcVal(rs.getString("CONSTANTE3"));
        oPlyInaS03.setParPer(rs.getBigDecimal("PCT_AGT3"));

        oPlyInaP03.setOPlyInaS(oPlyInaS03);
        oPlyInaPT.add(oPlyInaP03);

        // Element 04
        OPlyInaP oPlyInaP04 = new OPlyInaP();
        OPlyInaS oPlyInaS04 = new OPlyInaS();
        oPlyInaS04.setCmpVal(cmpVal);
        oPlyInaS04.setPlyVal(plyVal);
        oPlyInaS04.setEnrSqn(enrSqn);
        oPlyInaS04.setAplVal(aplVal);
        oPlyInaS04.setAplEnrSqn(aplEnrSqn);
        oPlyInaS04.setItcVal(rs.getString("CONSTANTE4"));

        oPlyInaS04.setParPer(rs.getBigDecimal("PCT_AGT4"));

        oPlyInaP04.setOPlyInaS(oPlyInaS04);
        oPlyInaPT.add(oPlyInaP04);

        // Element 05
        OPlyInaP oPlyInaP05 = new OPlyInaP();
        OPlyInaS oPlyInaS05 = new OPlyInaS();
        oPlyInaS05.setCmpVal(cmpVal);
        oPlyInaS05.setPlyVal(plyVal);
        oPlyInaS05.setEnrSqn(enrSqn);
        oPlyInaS05.setAplVal(aplVal);
        oPlyInaS05.setAplEnrSqn(aplEnrSqn);
        oPlyInaS05.setItcVal(rs.getString("CONSTANTE5"));


        oPlyInaP05.setOPlyInaS(oPlyInaS05);
        oPlyInaPT.add(oPlyInaP05);

        // Element 06
        OPlyInaP oPlyInaP06 = new OPlyInaP();
        OPlyInaS oPlyInaS06 = new OPlyInaS();
        oPlyInaS06.setCmpVal(cmpVal);
        oPlyInaS06.setPlyVal(plyVal);
        oPlyInaS06.setEnrSqn(enrSqn);
        oPlyInaS06.setAplVal(aplVal);
        oPlyInaS06.setAplEnrSqn(aplEnrSqn);
        oPlyInaS06.setItcVal(rs.getString("CONSTANTE6"));


        oPlyInaP06.setOPlyInaS(oPlyInaS06);
        oPlyInaPT.add(oPlyInaP06);

        // Element 07
        OPlyInaP oPlyInaP07 = new OPlyInaP();
        OPlyInaS oPlyInaS07 = new OPlyInaS();
        
        oPlyInaS07.setCmpVal(cmpVal);
        oPlyInaS07.setPlyVal(plyVal);
        oPlyInaS07.setEnrSqn(enrSqn);
        oPlyInaS07.setAplVal(aplVal);
        oPlyInaS07.setAplEnrSqn(aplEnrSqn);
        oPlyInaS07.setItcVal(rs.getString("CONSTANTE7"));

        
        oPlyInaP07.setOPlyInaS(oPlyInaS07);
        oPlyInaPT.add(oPlyInaP07);

        oPlyInaCPT.setOPlyInaPT(oPlyInaPT);
        return oPlyInaCPT;
    }

}
