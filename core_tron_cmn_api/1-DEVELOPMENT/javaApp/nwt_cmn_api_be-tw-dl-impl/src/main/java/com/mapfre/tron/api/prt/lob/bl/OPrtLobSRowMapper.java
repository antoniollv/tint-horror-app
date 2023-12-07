package com.mapfre.tron.api.prt.lob.bl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;

public class OPrtLobSRowMapper implements RowMapper<OPrtLobS>{

	@Override
	public OPrtLobS mapRow(ResultSet rs, int rowNum) throws SQLException {
	        OPrtLobS oPrtLobS = new OPrtLobS();

	        oPrtLobS.setCmpVal(rs.getBigDecimal("COD_CIA"));
	        oPrtLobS.setSecVal(rs.getBigDecimal("COD_SECTOR"));
	        oPrtLobS.setSbsVal(rs.getBigDecimal("COD_SUBSECTOR"));
	        oPrtLobS.setLobVal(rs.getBigDecimal("COD_RAMO"));
	        oPrtLobS.setLobNam(rs.getString("NOM_RAMO"));
	        oPrtLobS.setLobAbr(rs.getString("ABR_RAMO"));
	        oPrtLobS.setClaLob(rs.getString("MCA_CLAUSULA"));

	        oPrtLobS.setAnxTxt(rs.getString("MCA_ANEXO"));
	        oPrtLobS.setPtaPly(rs.getString("MCA_PRORRATA"));
	        oPrtLobS.setPtaMdf(rs.getString("MCA_CAMBIA_PRORRATA"));
	        oPrtLobS.setMulRsk(rs.getString("MCA_RIESGOS"));
	        oPrtLobS.setIsuPed(rs.getString("MCA_PERIODOS"));
	        oPrtLobS.setRcpPed(rs.getString("MCA_RECIBO_POR_PERIODO"));
	        oPrtLobS.setIsuCmu(rs.getString("MCA_EMISION"));
	        oPrtLobS.setThrLvlMdf(rs.getString("MCA_CAMBIO_NIVEL_3"));

	        oPrtLobS.setEvtLep(rs.getString("MCA_CUENTA_2902_TMP"));
	        oPrtLobS.setPmsMdf(rs.getString("MCA_CAMBIO_PLAN_PAGO"));

	        oPrtLobS.setPmsMdfGnrEnr(rs.getString("MCA_SPTO_EN_PLAN_PAGO"));
	        oPrtLobS.setCerLob(rs.getString("MCA_CERTIFICADOS"));
	        oPrtLobS.setDsbRow(rs.getString("MCA_INH"));
	        oPrtLobS.setCinCedCmsWitRpt(rs.getString("MCA_COMIS_COA_EXT"));
	        oPrtLobS.setCllDayLob(rs.getString("MCA_365_DIAS"));
	        oPrtLobS.setQtnRqr(rs.getString("MCA_OBLIGA_PRESUPUESTO"));
	        oPrtLobS.setAtzQtn(rs.getString("MCA_AUTORIZA_PRESUPUESTO"));
	        oPrtLobS.setPlyValMdf(rs.getString("MCA_CAMBIO_NUM_POLIZA"));
	        oPrtLobS.setAplPlyValMdf(rs.getString("MCA_CAMBIO_NUM_POLIZA_APLI"));
	        oPrtLobS.setLssEnrVld(rs.getString("MCA_VAL_STRO_EN_SPTO"));
	        oPrtLobS.setRcpRmt(rs.getString("MCA_REMESA_RECIBO"));
	        oPrtLobS.setMnlRcp(rs.getString("MCA_RECIBO_MANUAL"));
	        oPrtLobS.setMnlCms(rs.getString("MCA_COMIS_MANUAL"));
	        oPrtLobS.setLsaRqrIsu(rs.getString("MCA_BUSCA_INSP_EMISION"));
	        oPrtLobS.setPlyWitRcp(rs.getString("MCA_EMISION_SIN_RECIBO"));
	        oPrtLobS.setResQtn(rs.getString("MCA_REUTILIZA_PRESUPUESTO"));
	        oPrtLobS.setResDec(rs.getString("MCA_REUTILIZA_DECLARACION"));
	        oPrtLobS.setRsrTlc(rs.getString("MCA_APLICA_AT_EN_RIESGO"));
	        oPrtLobS.setPemCinTypVal(rs.getString("TIP_COASEGURO_PERMITIDO"));
	        oPrtLobS.setLobCdc(rs.getString("MCA_CUADRO_COASEGURO_OBL"));

	        oPrtLobS.setMnlPreTypVal(rs.getString("TIP_PRIMAS_MANUALES"));
	        oPrtLobS.setPemAgnVal(rs.getBigDecimal("NUM_AGT"));
	        oPrtLobS.setMxmDrcRskTrsVal(rs.getBigDecimal("NUM_RIESGOS_TRASPASO_DIRECTO"));
	        oPrtLobS.setMxmOnlRskPntVal(rs.getBigDecimal("NUM_RIESGOS_IMPRESION"));
	        oPrtLobS.setRskDspPrdNam(rs.getString("NOM_PRG_RIESGO"));
	        oPrtLobS.setMdtGnrTypVal(rs.getString("TIP_FORMACION_MODALIDAD"));
	        oPrtLobS.setIsuLsaCckPgmNam(rs.getString("NOM_PRG_BUSCA_INSP_EMISION"));
	        oPrtLobS.setPocVal(rs.getString("COD_TRATAMIENTO"));
	        oPrtLobS.setLssPocVal(rs.getString("COD_TRATAMIENTO_SINI"));
	        oPrtLobS.setPocAlbVal(rs.getString("COD_TRATAMIENTO_CTABLE"));
	        oPrtLobS.setIsuWitRcpPrdNam(rs.getString("NOM_PRG_EMISION_SIN_RECIBO"));

	        oPrtLobS.setPrcVal(rs.getString("COD_PROCESO"));
	        oPrtLobS.setQtnPntPrcVal(rs.getString("COD_PROCESO_P"));
	        oPrtLobS.setPlrTccRjcPrcVal(rs.getString("COD_PROCESO_R"));
	        oPrtLobS.setPlyVrdStuVal(rs.getString("COD_EST_DV_POLIZA"));
	        oPrtLobS.setRskStuVal(rs.getString("COD_EST_RIESGO"));
	        oPrtLobS.setMdtStuVal(rs.getString("COD_EST_MODALIDAD"));
	        oPrtLobS.setAcrStuVal(rs.getString("COD_EST_ACCESORIOS"));
	        oPrtLobS.setPmsStuVal(rs.getString("COD_EST_PLAN_PAGO"));
	        oPrtLobS.setLsaStuVal(rs.getString("COD_EST_INSPEC"));
	        oPrtLobS.setCvrCfcPrdNam(rs.getString("NOM_PRG_COEF_COB"));
	        oPrtLobS.setLsaCckPgmNam(rs.getString("NOM_PRG_BUSCA_INSP"));
	        oPrtLobS.setLsaExlPgmNam(rs.getString("NOM_PRG_EXCLUYE_INSP"));
	        oPrtLobS.setCmsDstVal(rs.getBigDecimal("TIP_DST_COMIS"));
	        oPrtLobS.setCmsDstPrdNam(rs.getString("NOM_PRG_DST_COMIS"));
	        oPrtLobS.setRmtRcpPrdNam(rs.getString("NOM_PRG_REMESA_RECIBO"));
	        oPrtLobS.setOrnAvsMdf(rs.getString("MCA_MOD_ORG_ASE"));
	        oPrtLobS.setInmCmsMdf(rs.getString("MCA_MOD_COM_CUOTA_INTERV"));
	        oPrtLobS.setRjcSpnPlyIsuUsr(rs.getString("MCA_RECHAZO_SUSP_A_TODOS"));
	        oPrtLobS.setCmsRcl(rs.getString("MCA_RECALCULA_COMIS"));
	        oPrtLobS.setStmRgt(rs.getString("MCA_REGISTRA_HORA"));
	        oPrtLobS.setPemRnsTypVal(rs.getString("TIP_REA_PERMITIDO"));
	        oPrtLobS.setMnlRat(rs.getString("MCA_TASA_MANUAL"));
	        oPrtLobS.setBtcRskBrw(rs.getString("MCA_DES_POR_RIESGO_BATCH"));
	        oPrtLobS.setEnrRsn(rs.getString("MCA_MOTIVOS_SPTO"));
	        oPrtLobS.setCmcAcsDatVal(rs.getString("TIP_ACCESO_COM"));
	        oPrtLobS.setAplRjcSpn(rs.getString("MCA_RECHAZA_SUSPENDE_APLI"));
	        oPrtLobS.setPmsEnrPrdNam(rs.getString("NOM_PRG_SPTO_PLAN_PAGO"));
	        oPrtLobS.setCanEnrTchCnl(rs.getString("MCA_CT_EN_ANULACION_SPTOS"));
	        oPrtLobS.setMntExpDat(rs.getString("MCA_RESPETA_DIA_VCTO"));
	        oPrtLobS.setImgGnrTypVal(rs.getString("TIP_FORMACION_IMAGEN"));
	        oPrtLobS.setWrnErrLssEnr(rs.getString("MCA_ERROR_AVISO_STRO"));
	        oPrtLobS.setExsRskAnx(rs.getString("MCA_ANEXO_RIES"));
	        oPrtLobS.setExsTrmLssEnr(rs.getString("MCA_VAL_STRO_TERM_EN_SPTO"));
	        oPrtLobS.setWrnErrTrmLssEnr(rs.getString("MCA_ERROR_AVISO_STRO_TERM"));
	        oPrtLobS.setRgnEnrAtzRta(rs.getString("MCA_REGENERA_SPTOS_CT"));
	        oPrtLobS.setPorCms(rs.getString("MCA_COMIS_CARTERA"));
	        oPrtLobS.setRcpCloPrdNam(rs.getString("NOM_PRG_COBRO_RECIBO"));
	        oPrtLobS.setExrValMdf(rs.getString("MCA_MODIFICA_CAMBIO"));
	        oPrtLobS.setExrValVldPrdNam(rs.getString("NOM_PRG_VALIDA_CAMBIO"));
	        oPrtLobS.setSdrIsuDat(rs.getString("MCA_VAL_FEC_EMISION_ESTANDAR"));

	        oPrtLobS.setSdrIsuDatPrdNam(rs.getString("NOM_PRG_VAL_FEC_EMI_ESTANDAR"));
	        oPrtLobS.setTypAnx(rs.getString("MCA_TIP_ANEXO"));
	        oPrtLobS.setIvfMng(rs.getString("MCA_GESTION_FONDO"));
	        oPrtLobS.setCllCvrTotAmn(rs.getString("MCA_CAL_IMP_COB"));

	        return oPrtLobS;
	    }

}
