package com.mapfre.tron.api.cmn.ply.gni.dl.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.ply.c.CPly;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniP;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.cmn.ply.gni.dl.ISrPlyGniQryDAO;

import lombok.Data;

/**
 * The common PlyGniQry repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 dic. 2020 - 15:59:05
 *
 */
@Data
@Repository
public class SrCmnPlyGniQryDAOImpl implements ISrPlyGniQryDAO {

    /** The spring jdbc template.*/
    private JdbcTemplate jdbcTemplate;

    /** The main query qotation.*/
    private final String mainQueryQuotations = new StringBuilder()
            .append("SELECT a.cod_cia, a.num_poliza,")
            .append(" a.num_presupuesto, ")
            .append(" a.num_spto, a.num_apli ,")
            .append(" a.num_spto_apli,a.cod_sector, ")
            .append(" a.cod_ramo, ")
            .append(" a.fec_validez ,a.fec_emision,a.fec_emision_spto ,")
            .append(" a.fec_efec_poliza ,")
            .append(" a.fec_vcto_poliza  ,a.fec_efec_spto,")
            .append(" a.fec_vcto_spto ,a.tip_duracion,")
            .append(" a.num_riesgos,a.cod_mon ,")
            .append(" a.cod_fracc_pago,")
            .append(" a.cant_renovaciones,")
            .append(" a.num_renovaciones ,a.tip_coaseguro,")
            .append(" a.num_poliza_anterior,")
            .append(" a.num_poliza_cliente,a.num_contrato,")
            .append(" a.num_poliza_grupo,a.num_secu_grupo,a.cod_spto ,")
            .append(" a.sub_cod_spto , ")
            .append(" a.cod_tip_spto , a.tip_spto,")
            .append(" a.txt_motivo_spto,")
            .append(" a.mca_regulariza,a.tip_regulariza, ")
            .append(" a.pct_regulariza,a.cod_indice,")
            .append(" a.anios_max_duracion,a.meses_max_duracion,")
            .append(" a.dias_max_duracion ,")
            .append(" a.duracion_pago_prima ,a.cod_envio,")
            .append(" a.mca_reaseguro_manual ,a.mca_prorrata,")
            .append(" a.mca_prima_manual,a.mca_provisional ,")
            .append(" a.fec_autorizacion,a.mca_poliza_anulada,")
            .append(" a.mca_spto_anulado,a.num_spto_anulado,")
            .append(" a.fec_spto_anulado,a.mca_spto_tmp ,")
            .append(" a.mca_datos_minimos,a.mca_impresion,")
            .append(" a.mca_exclusivo,a.cod_usr,a.fec_actu,")
            .append(" a.cod_nivel3_captura, ")
            .append(" a.mca_reaseguro_marco ,a.tip_poliza_tr,")
            .append(" a.num_poliza_siguiente,")
            .append(" a.cod_dst_agt,a.cod_cuadro_coa,")
            .append(" a.tip_rea , a.num_spto_publico,")
            .append(" a.val_mca_int,a.hora_desde,")
            .append(" a.num_subcontrato ,a.cod_negocio,")
            .append(" a.val_cambio ")
            .toString();

    /**
     * Set the datasource.
     *
     * @param dataSource -> the jdbc template datasource to set
     */
    @Autowired
    public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }

    /**
     * Query policy general information valid for a date.
     *
     * @author arquitectura - pvraul1
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param plyVal    -> Policy value
     * @param aplVal    -> Application value
     * @param qryDat    -> Query date
     * @return OPlyGniP -> General information response
     */
    @Override
    public OPlyGniP opvDat(final BigDecimal cmpVal, final String plyVal, final BigDecimal aplVal, final Date qryDat,
            final String getNamAll, final String reaOrg, final String lngVal, final String exlPrvVal) {

        final String query = new StringBuilder()
                .append(mainQueryQuotations)
                .append(" FROM a2000030 a ")

                .append(" LEFT JOIN  a2991800 h ")
                .append(" on h.cod_cia= a.cod_cia ")
                .append(" AND h.sub_cod_spto =a.sub_cod_spto ")
                .append(" AND h.cod_spto =  a.cod_spto ")
                .append(" AND  h.tip_ambito_spto IN (")
                .append("  CASE a.num_apli")
                .append("    WHEN ?")
                .append("    THEN 1")
                .append("    ELSE 2")
                .append("  END ,3)")
                .toString();

        final String query1 = new StringBuilder()
                .append(" WHERE a.cod_cia = ?")
                .append(" AND a.num_poliza = ?")
                .append(" AND a.num_spto   = (SELECT MAX(bb.num_spto)")
                .append("  FROM a2000030 bb")
                .append("  WHERE bb.cod_cia       = a.cod_cia ")
                .append("  AND bb.num_poliza    = a.num_poliza")
                .append("  AND bb.num_apli      = ?")
                .append("  AND bb.num_spto_apli = ?")
                .append(" AND bb.fec_efec_spto <= NVL(?, bb.fec_efec_spto)")
             //   .append(" AND bb.fec_vcto_spto")
                .append(" AND bb.mca_provisional = CASE WHEN 'N' = NVL(?,'N') THEN bb.mca_provisional WHEN 'S'=NVL(?,'N') THEN 'N' ELSE bb.mca_provisional END")
                .append(" AND bb.mca_spto_tmp     = ?")
                .append(" AND bb.mca_spto_anulado = ?)")
                .append(" AND a.num_apli      = ?")
                .append(" AND a.num_spto_apli = ?")
                .toString();

        final String query2 = new StringBuilder()
                .append("  WHERE a.cod_cia       = ?")
                .append(" AND a.num_poliza    = ?")
                .append("  AND a.num_apli      = ?")
                .append(" AND a.num_spto_apli = (SELECT MAX(bb.num_spto_apli)")
                .append(" FROM a2000030 bb")
                .append(" WHERE bb.cod_cia    = a.cod_cia")
                .append("  AND bb.num_poliza = a.num_poliza")
                .append("  AND bb.num_apli   = a.num_apli")
                .append(" AND bb.fec_efec_spto <= NVL(?, bb.fec_efec_spto)")
             //   .append(" AND bb.fec_vcto_spto")
                .append(" AND bb.mca_provisional = CASE WHEN 'N' = NVL(?,'N') THEN bb.mca_provisional WHEN 'S'=NVL(?,'N') THEN 'N' ELSE bb.mca_provisional END")
                .append(" AND bb.mca_spto_tmp     = ?")
                .append(" AND bb.mca_spto_anulado = ?)")
                .toString();

        OPlyGniP oPlyGniP = new OPlyGniP(); 

        if (CTrn.ZRO.equals(aplVal)) {
            final String queryTotal = query.concat(query1);
            OPlyGniS oPlyGniS1 =  jdbcTemplate.queryForObject(queryTotal,
                    new Object[] { 
                            CPly.APL_FXD,
                            cmpVal, 
                            plyVal,
                            CTrn.ZRO,
                            CTrn.ZRO,
                            qryDat,
                            exlPrvVal,
                            exlPrvVal,
                            CIns.NO,
                            CIns.NO,
                            CTrn.ZRO,
                            CTrn.ZRO},
                    new OPlyGniSRowMapper());

            oPlyGniP.setOPlyGniS(oPlyGniS1);

        } else {
            final String queryTotal = query.concat(query2);
            OPlyGniS oPlyGniS2 =  jdbcTemplate.queryForObject(queryTotal,
                    new Object[] {
                            CPly.APL_FXD,
                            cmpVal, 
                            plyVal,
                            aplVal,
                            qryDat,
                            exlPrvVal,
                            exlPrvVal,
                            CIns.NO,
                            CIns.NO},
                    new OPlyGniSRowMapper());

            oPlyGniP.setOPlyGniS(oPlyGniS2);
        }

        return oPlyGniP;
    }

}
