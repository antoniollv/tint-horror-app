package com.mapfre.tron.api.cmn.ply.gni.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniS;

/**
 * General comment of the class OPlyGniSRowMapper.
 *
 * @author BRCHARL
 * @since 1.0.0
 * @version 22 oct. 2019 9:54:18
 *
 */
public class OPlyGniSRowMapper implements RowMapper<OPlyGniS> {

    @Override
    public OPlyGniS mapRow(ResultSet rs, int rowNum) throws SQLException {

        OPlyGniS oPlyGniS = new OPlyGniS();

        oPlyGniS.setCmpVal(rs.getBigDecimal("COD_CIA"));
        oPlyGniS.setPlyVal(rs.getString("NUM_POLIZA"));
        oPlyGniS.setOrgQtnVal(rs.getString("NUM_PRESUPUESTO"));
        oPlyGniS.setEnrSqn(rs.getBigDecimal("NUM_SPTO"));
        oPlyGniS.setAplVal(rs.getBigDecimal("NUM_APLI"));
        oPlyGniS.setAplEnrSqn(rs.getBigDecimal("NUM_SPTO_APLI"));
        oPlyGniS.setSecVal(rs.getBigDecimal("COD_SECTOR"));
        oPlyGniS.setLobVal(rs.getBigDecimal("COD_RAMO"));
        oPlyGniS.setVldDat(rs.getDate("FEC_VALIDEZ"));
        oPlyGniS.setIsuDat(rs.getDate("FEC_EMISION"));
        oPlyGniS.setEnrIsuDat(rs.getDate("FEC_EMISION_SPTO"));
        oPlyGniS.setPlyEfcDat(rs.getDate("FEC_EFEC_POLIZA"));
        oPlyGniS.setPlyExpDat(rs.getDate("FEC_VCTO_POLIZA"));
        oPlyGniS.setEnrEfcDat(rs.getDate("FEC_EFEC_SPTO"));
        oPlyGniS.setEnrExpDat(rs.getDate("FEC_VCTO_SPTO"));
        oPlyGniS.setPlyDrtVal(rs.getString("TIP_DURACION"));
        oPlyGniS.setTotRskVal(rs.getBigDecimal("NUM_RIESGOS"));
        oPlyGniS.setCrnVal(rs.getBigDecimal("COD_MON"));
        oPlyGniS.setPmsVal(rs.getBigDecimal("COD_FRACC_PAGO"));
        oPlyGniS.setMxmRnwQntVal(rs.getBigDecimal("CANT_RENOVACIONES"));
        oPlyGniS.setRnwVal(rs.getBigDecimal("NUM_RENOVACIONES"));
        oPlyGniS.setCinTypVal(rs.getString("TIP_COASEGURO"));
        oPlyGniS.setPrrPlyVal(rs.getString("NUM_POLIZA_ANTERIOR"));
        oPlyGniS.setClpVal(rs.getString("NUM_POLIZA_CLIENTE"));
        oPlyGniS.setDelVal(rs.getBigDecimal("NUM_CONTRATO"));
        oPlyGniS.setGppVal(rs.getString("NUM_POLIZA_GRUPO"));
        oPlyGniS.setGppSqnVal(rs.getBigDecimal("NUM_SECU_GRUPO"));
        oPlyGniS.setEnrVal(rs.getBigDecimal("COD_SPTO"));
        oPlyGniS.setEnrSbdVal(rs.getBigDecimal("SUB_COD_SPTO"));
        oPlyGniS.setEnrCasVal(rs.getString("COD_TIP_SPTO"));
        oPlyGniS.setEnrTypVal(rs.getString("TIP_SPTO"));
        oPlyGniS.setEnrRsnTxtVal(rs.getString("TXT_MOTIVO_SPTO"));
        oPlyGniS.setPlyRgl(rs.getString("MCA_REGULARIZA"));
        oPlyGniS.setRglTypVal(rs.getString("TIP_REGULARIZA"));
        oPlyGniS.setRglPer(rs.getBigDecimal("PCT_REGULARIZA"));
        oPlyGniS.setRglIdxVal(rs.getBigDecimal("COD_INDICE"));
        oPlyGniS.setMxmDrtYerVal(rs.getBigDecimal("ANIOS_MAX_DURACION"));
        oPlyGniS.setMxmDrtMnhVal(rs.getBigDecimal("MESES_MAX_DURACION"));
        oPlyGniS.setMxmDrtDayVal(rs.getBigDecimal("DIAS_MAX_DURACION"));
        oPlyGniS.setPrePymDrtVal(rs.getBigDecimal("DURACION_PAGO_PRIMA"));
        oPlyGniS.setSndVal(rs.getString("COD_ENVIO"));
        oPlyGniS.setMnlRnsDst(rs.getString("MCA_REASEGURO_MANUAL"));
        oPlyGniS.setPtaPly(rs.getString("MCA_PRORRATA"));
        oPlyGniS.setMnlPre(rs.getString("MCA_PRIMA_MANUAL"));
        oPlyGniS.setPrvMvm(rs.getString("MCA_PROVISIONAL"));
        oPlyGniS.setAtzDat(rs.getDate("FEC_AUTORIZACION"));
        oPlyGniS.setCanPly(rs.getString("MCA_POLIZA_ANULADA"));
        oPlyGniS.setCanEnr(rs.getString("MCA_SPTO_ANULADO"));
        oPlyGniS.setCacEnrSqn(rs.getBigDecimal("NUM_SPTO_ANULADO"));
        oPlyGniS.setEnrCanDat(rs.getDate("FEC_SPTO_ANULADO"));
        oPlyGniS.setTmpEnr(rs.getString("MCA_SPTO_TMP"));
        oPlyGniS.setMnmDaaPly(rs.getString("MCA_DATOS_MINIMOS"));
        oPlyGniS.setPlyPnt(rs.getString("MCA_IMPRESION"));
        oPlyGniS.setExvVal(rs.getString("MCA_EXCLUSIVO"));
        oPlyGniS.setUsrVal(rs.getString("COD_USR"));
        oPlyGniS.setMdfDat(rs.getDate("FEC_ACTU"));
        oPlyGniS.setCptThrLvlVal(rs.getBigDecimal("COD_NIVEL3_CAPTURA"));
        oPlyGniS.setFrpRns(rs.getString("MCA_REASEGURO_MARCO"));
        oPlyGniS.setTnrPlyTypVal(rs.getString("TIP_POLIZA_TR"));
        oPlyGniS.setNxtPlyVal(rs.getString("NUM_POLIZA_SIGUIENTE"));
        oPlyGniS.setAgnDstVal(rs.getBigDecimal("COD_DST_AGT"));
        oPlyGniS.setCinChaVal(rs.getString("COD_CUADRO_COA"));
        oPlyGniS.setRnsTypVal(rs.getString("TIP_REA"));
        oPlyGniS.setPblEnrSqn(rs.getBigDecimal("NUM_SPTO_PUBLICO"));
        oPlyGniS.setIntVldVal(rs.getString("VAL_MCA_INT"));
        oPlyGniS.setStm(rs.getString("HORA_DESDE"));
        oPlyGniS.setSblVal(rs.getBigDecimal("NUM_SUBCONTRATO"));
        oPlyGniS.setBsnVal(rs.getString("COD_NEGOCIO"));
        oPlyGniS.setExrVal(rs.getBigDecimal("VAL_CAMBIO"));
        

        return oPlyGniS;
    }

}
