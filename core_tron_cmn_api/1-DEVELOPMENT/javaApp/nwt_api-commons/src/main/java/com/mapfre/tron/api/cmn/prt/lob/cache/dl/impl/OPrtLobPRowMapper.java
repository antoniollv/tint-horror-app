package com.mapfre.tron.api.cmn.prt.lob.cache.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.dl.NewtronRowMapper;

public class OPrtLobPRowMapper extends NewtronRowMapper<OPrtLobS>{
    @Override
    public OPrtLobS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OPrtLobS s = new OPrtLobS();
        s.setCmpVal(rs.getBigDecimal("cod_cia"));
        s.setSecVal(rs.getBigDecimal("cod_sector"));
        s.setSbsVal(rs.getBigDecimal("cod_subsector"));
        s.setLobVal(rs.getBigDecimal("cod_ramo"));
        s.setLobNam(rs.getString("nom_ramo"));
        s.setLobAbr(rs.getString("abr_ramo"));
        s.setClaLob(rs.getString("mca_clausula"));
        s.setAnxTxt(rs.getString("mca_anexo"));
        s.setPtaPly(rs.getString("mca_prorrata"));
        s.setPtaMdf(rs.getString("mca_cambia_prorrata"));
        s.setMulRsk(rs.getString("mca_riesgos"));
        s.setIsuPed(rs.getString("mca_periodos"));
        s.setRcpPed(rs.getString("mca_recibo_por_periodo"));
        s.setIsuCmu(rs.getString("mca_emision"));
        s.setThrLvlMdf(rs.getString("mca_cambio_nivel_3"));
        s.setPmsMdfGnrEnr(rs.getString("mca_spto_en_plan_pago"));
        s.setPmsMdf(rs.getString("mca_cambio_plan_pago"));
        s.setCerLob(rs.getString("mca_certificados"));
        s.setDsbRow(rs.getString("mca_inh"));
        s.setCinCedCmsWitRpt(rs.getString("mca_comis_coa_ext"));
        s.setCllDayLob(rs.getString("mca_365_dias"));
        s.setQtnRqr(rs.getString("mca_obliga_presupuesto"));
        s.setAtzQtn(rs.getString("mca_autoriza_presupuesto"));
        s.setPlyValMdf(rs.getString("mca_cambio_num_poliza"));
        s.setAplPlyValMdf(rs.getString("mca_cambio_num_poliza_apli"));
        s.setLssEnrVld(rs.getString("mca_val_stro_en_spto"));
        s.setRcpRmt(rs.getString("mca_remesa_recibo"));
        s.setMnlRcp(rs.getString("mca_recibo_manual"));
        s.setMnlCms(rs.getString("mca_comis_manual"));
        s.setLsaRqrIsu(rs.getString("mca_busca_insp_emision"));
        s.setPlyWitRcp(rs.getString("mca_emision_sin_recibo"));
        s.setResQtn(rs.getString("mca_reutiliza_presupuesto"));
        s.setResDec(rs.getString("mca_reutiliza_declaracion"));
        s.setRsrTlc(rs.getString("mca_aplica_at_en_riesgo"));
        s.setPemCinTypVal(rs.getString("tip_coaseguro_permitido"));
        s.setLobCdc(rs.getString("mca_cuadro_coaseguro_obl"));
        s.setMnlPreTypVal(rs.getString("tip_primas_manuales"));
        s.setPemAgnVal(rs.getBigDecimal("num_agt"));
        s.setMxmDrcRskTrsVal(rs.getBigDecimal("num_riesgos_traspaso_directo"));
        s.setMxmOnlRskPntVal(rs.getBigDecimal("num_riesgos_impresion"));
        s.setRskDspPrdNam(rs.getString("nom_prg_riesgo"));
        s.setMdtGnrTypVal(rs.getString("tip_formacion_modalidad"));
        s.setIsuLsaCckPgmNam(rs.getString("nom_prg_busca_insp_emision"));
        s.setPocVal(rs.getString("cod_tratamiento"));
        s.setIsuWitRcpPrdNam(rs.getString("nom_prg_emision_sin_recibo"));
        s.setPlyVrdStuVal(rs.getString("cod_est_dv_poliza"));
        s.setRskStuVal(rs.getString("cod_est_riesgo"));
        s.setMdtStuVal(rs.getString("cod_est_modalidad"));
        s.setAcrStuVal(rs.getString("cod_est_accesorios"));
        s.setPmsStuVal(rs.getString("cod_est_plan_pago"));
        s.setLsaStuVal(rs.getString("cod_est_inspec"));
        s.setCvrCfcPrdNam(rs.getString("nom_prg_coef_cob"));
        s.setLsaCckPgmNam(rs.getString("nom_prg_busca_insp"));
        s.setLsaExlPgmNam(rs.getString("nom_prg_excluye_insp"));
        s.setCmsDstVal(rs.getBigDecimal("tip_dst_comis"));
        s.setCmsDstPrdNam(rs.getString("nom_prg_coef_cob"));
        s.setRmtRcpPrdNam(rs.getString("nom_prg_remesa_recibo"));
        s.setOrnAvsMdf(rs.getString("mca_mod_org_ase"));
        s.setInmCmsMdf(rs.getString("mca_mod_com_cuota_interv"));
        s.setRjcSpnPlyIsuUsr(rs.getString("mca_rechazo_susp_a_todos"));
        s.setCmsRcl(rs.getString("mca_recalcula_comis"));
        s.setStmRgt(rs.getString("mca_registra_hora"));
        s.setPemRnsTypVal(rs.getString("tip_rea_permitido"));
        s.setMnlRat(rs.getString("mca_tasa_manual"));
        s.setBtcRskBrw(rs.getString("mca_des_por_riesgo_batch"));
        s.setEnrRsn(rs.getString("mca_motivos_spto"));
        s.setCmcAcsDatVal(rs.getString("tip_acceso_com"));
        s.setAplRjcSpn(rs.getString("mca_rechaza_suspende_apli"));
        s.setPmsEnrPrdNam(rs.getString("nom_prg_spto_plan_pago"));
        s.setImgGnrTypVal(rs.getString("tip_formacion_imagen"));
        s.setCanEnrTchCnl(rs.getString("mca_ct_en_anulacion_sptos"));
        s.setExrValMdf(rs.getString("mca_modifica_cambio"));
        s.setExrValVldPrdNam(rs.getString("nom_prg_valida_cambio"));
        s.setSdrIsuDat(rs.getString("mca_val_fec_emision_estandar"));
        s.setSdrIsuDatPrdNam(rs.getString("nom_prg_val_fec_emi_estandar"));
        s.setLssPocVal(rs.getString("cod_tratamiento_sini"));
        s.setPocAlbVal(rs.getString("cod_tratamiento_ctable"));
        s.setPrcVal(rs.getString("cod_proceso"));
        s.setQtnPntPrcVal(rs.getString("cod_proceso_p"));
        s.setPlrTccRjcPrcVal(rs.getString("cod_proceso_r"));
        s.setEvtLep(rs.getString("mca_cuenta_2902_tmp"));
        s.setMntExpDat(rs.getString("mca_respeta_dia_vcto"));
        s.setExsTrmLssEnr(rs.getString("mca_val_stro_term_en_spto"));
        s.setWrnErrLssEnr(rs.getString("mca_error_aviso_stro"));
        s.setWrnErrTrmLssEnr(rs.getString("mca_error_aviso_stro_term"));
        s.setExsRskAnx(rs.getString("mca_anexo_ries"));
        s.setRgnEnrAtzRta(rs.getString("mca_regenera_sptos_ct"));
        s.setPorCms(rs.getString("mca_comis_cartera"));
        s.setRcpCloPrdNam(rs.getString("nom_prg_cobro_recibo"));
        s.setTypAnx(rs.getString("mca_tip_anexo"));
        s.setIvfMng(rs.getString("mca_gestion_fondo"));
        s.setCllCvrTotAmn(rs.getString("mca_cal_imp_cob"));
        
        // CMN-100
        s.setExsAcr("A".equals(s.getPocVal()) ? CIns.YES : CIns.NO);
        s.setPorCmsEqlNewPdc("A".equals(s.getPocVal()) ? CIns.NO : CIns.YES);
        s.setExsApl("T".equals(s.getPocVal()) ? CIns.YES : CIns.NO);
        s.setBlbMdt("V".equals(s.getPocVal()) ? CIns.YES : CIns.NO);
        s.setCmcAty("V".equals(s.getPocVal()) ? CIns.YES : CIns.NO);
        
        
        return s;
    }
}
