package com.mapfre.tron.api.cmn.pid.enr.dl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapfre.nwt.trn.pid.enr.bo.OPidEnrS;

/**
 * The PidEnr row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 10:41:47
 *
 */
public class OPidEnrSRowMapper implements RowMapper<OPidEnrS> {

    @Override
    public OPidEnrS mapRow(ResultSet rs, int rowNum) throws SQLException {
        OPidEnrS lvOPidEnrS = new OPidEnrS();
        lvOPidEnrS.setCmpVal(rs.getBigDecimal("cod_cia"));
        lvOPidEnrS.setEnrVal(rs.getBigDecimal("cod_spto"));
        lvOPidEnrS.setEnrSbdVal(rs.getBigDecimal("sub_cod_spto"));
        lvOPidEnrS.setEnrNam(rs.getString("nom_spto"));
        lvOPidEnrS.setEnrTypVal(rs.getString("tip_spto"));
        lvOPidEnrS.setEnrScoTypVal(rs.getString("tip_ambito_spto"));
        lvOPidEnrS.setClaLob(rs.getString("mca_clausula"));
        lvOPidEnrS.setAnxTxt(rs.getString("mca_anexo"));
        lvOPidEnrS.setTmpEnr(rs.getString("mca_temporal"));
        lvOPidEnrS.setEfpMdfEnr(rs.getString("mca_mod_vigencia"));
        lvOPidEnrS.setPtaPly(rs.getString("mca_prorrata"));
        lvOPidEnrS.setCanCfcCllPrdNam(rs.getString("nom_prg_coef_anulacion"));
        lvOPidEnrS.setCanSclTypVal(rs.getString("tip_anulacion_escala"));
        lvOPidEnrS.setPlyValMdf(rs.getString("mca_cambio_num_poliza"));
        lvOPidEnrS.setPlyValMdfPrdNam(rs.getString("nom_prg_mca_cambio_num_poliza"));
        lvOPidEnrS.setRncRqr(rs.getString("mca_reinspeccion"));
        lvOPidEnrS.setRncPrdNam(rs.getString("nom_prg_mca_reinspeccion"));
        lvOPidEnrS.setTotRfnQst(rs.getString("mca_pregunta_si_devuelve"));
        lvOPidEnrS.setAgnMdfCmuVld(rs.getString("mca_valida_cambio_nivel3"));
        lvOPidEnrS.setRhbEnrTypVal(rs.getString("tip_spto_re"));
        lvOPidEnrS.setDsbRow(rs.getString("mca_inh"));
        lvOPidEnrS.setPlyVrd(rs.getString("mca_dv_poliza"));
        lvOPidEnrS.setEnrNewRcp(rs.getString("mca_recibos_nuevos"));
        lvOPidEnrS.setAgnMdfEnrWitMvm(rs.getString("mca_ca_en_sm"));
        lvOPidEnrS.setAgnMdfInmStsPnd(rs.getString("mca_val_fec_efec_ep_ca"));
        lvOPidEnrS.setPblEnr(rs.getString("mca_spto_publico"));
        lvOPidEnrS.setCmsRcl(rs.getString("mca_recalcula_comis"));
        lvOPidEnrS.setCanPlyLss(rs.getString("mca_sini_pol_anul"));
        lvOPidEnrS.setCanEnrSlc(rs.getString("mca_selecciona_spto_anul"));
        lvOPidEnrS.setObsRqr(rs.getString("mca_obs_obligatorias"));
        lvOPidEnrS.setGndValCll(rs.getString("mca_calcula_val_garantizados"));
        lvOPidEnrS.setPmsMdfVld(rs.getString("mca_plan_pago_valida"));
        lvOPidEnrS.setRtaEnr(rs.getString("mca_spto_retroactivo"));
        lvOPidEnrS.setRtaCanEnr(rs.getString("mca_anul_spto_en_retroactivo"));
        lvOPidEnrS.setRtaCanTmpEnr(rs.getString("mca_anul_tmp_en_retroactivo"));
        lvOPidEnrS.setAllRskRtePrdNam(rs.getString("nom_prg_todos_riesgos_tarifica"));
        lvOPidEnrS.setIvfEnrTypVal(rs.getString("tip_spto_fondo"));

        return lvOPidEnrS;
    }

}
