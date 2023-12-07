package com.mapfre.tron.api.cmn.ply.ina.dl.impl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.tpd.c.CTpd;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaCPT;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.cmn.ply.ina.dl.ISrPlyInaQryDAO;

import lombok.Data;

/**
 * The PlyInaQry implementation repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 dic. 2020 - 16:49:10
 *
 */
@Data
@Repository
public class SrCmnPlyInaQryDAOImpl implements ISrPlyInaQryDAO {

    /** The jdbc spring template.*/
    private JdbcTemplate jdbcTemplate;

    /**
     * Set the datasource.
     *
     * @param dataSource -> the jdbc template datasource to set.
     */
    @Autowired
    public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }

    /**
     * plyTbl: ISU-25153 - OFRECER CONSULTAR intervencion agente por poliza, vigente, conjunto.
     *
     * arquitectura - pvraul1
     * 
     * @param  pmCmpVal        -> Company code
     * @param  pmPlyVal        -> Policy
     * @param  pmAplVal        -> Application
     * @param  pmOrgTypVal     -> Origen
     * @param  pmLngVal        -> Language code
     * @param  pmGetNamTypVal  -> Mostrar nombre
     * @return OPlyInaCPT      -> Agent Intervention List
     */
    @Override
    public OPlyInaCPT plyTbl(final BigDecimal pmCmpVal, final String pmPlyVal, final BigDecimal pmAplVal,
            final String pmOrgTypVal, final String pmLngVal, final String pmGetNamTypVal) {

        StringBuilder query = new StringBuilder()
                .append("SELECT ")

                .append("p.cod_cia              ,")
                .append("p.num_poliza           ,")
                .append("p.num_spto             ,")
                .append("p.num_apli             ,")
                .append("p.num_spto_apli        ,")
                .append("p.cod_agt              ,")
                .append("p.pct_agt              ,")
                .append("p.cod_cuadro_com       ,")
                .append("p.cod_nivel1           ,")
                .append("p.cod_nivel2           ,")
                .append("p.cod_nivel3           ,")
                .append("p.cod_canal1           ,")
                .append("p.cod_canal2           ,")
                .append("p.cod_canal3           ,")

                .append("p.cod_agt2             ,")
                .append("p.pct_agt2             ,")
                .append("p.cod_agt3             ,")
                .append("p.pct_agt3             ,")
                .append("p.cod_agt4             ,")
                .append("p.pct_agt4             ,")
                .append("p.cod_org              ,")
                .append("p.cod_asesor           ,")
                .append("p.cod_ejecutivo        ,")

                .append("? as constante1        ,")
                .append("? as constante2        ,")
                .append("? as constante3        ,")
                .append("? as constante4        ,")
                .append("? as constante5        ,")
                .append("? as constante6        ,")
                .append("? as constante7        ");

        OPlyInaCPT oPlyInaCPT;
        if(CTrn.REA_ORG.equals(pmOrgTypVal)) {
            query
                .append("FROM A2000030 p ")
                .append("WHERE p.cod_cia     = ? ")
                .append("AND   p.num_poliza  = ? ")
                .append("AND   p.num_apli    = ? ")
                .append("AND   (p.num_spto, p.num_spto_apli) = (")
                .append("           SELECT MAX(b.num_spto), MAX(b.num_spto_apli) ")
                .append("               FROM A2000030 b ")
                .append("               WHERE b.cod_cia      = p.cod_cia ")
                .append("               AND   b.num_poliza   = p.num_poliza ")
                .append("               AND   b.num_apli     = p.num_apli ")
                .append("               AND   b.mca_spto_tmp = ? ")
                .append("               AND   (b.mca_spto_anulado = ? OR (mca_spto_anulado = ? AND mca_provisional = ?))")
                .append("      )");

            oPlyInaCPT = jdbcTemplate.queryForObject(
                    query.toString(),
                    new Object[] { CTpd.MPR_PDR,
                                   CTpd.MPR_INA_SCN,
                                   CTpd.MPR_INA_THR,
                                   CTpd.MPR_INA_FRT,
                                   CTpd.MPR_ORN,
                                   CTpd.MPR_AVS,
                                   CTpd.MPR_ECT,
                                   pmCmpVal,
                                   pmPlyVal,
                                   pmAplVal,
                                   CIns.NO,
                                   CIns.NO,
                                   CIns.YES,
                                   CIns.YES }, 
                    new OPlyInaCPTRowMapper());
        } else if(CTrn.PRR_ORG.equals(pmOrgTypVal)) {
            query
                .append("FROM p2000030 p ")
                .append(" WHERE p.cod_cia = ? AND p.num_poliza = ? ");

            oPlyInaCPT = jdbcTemplate.queryForObject(
                query.toString(),
                new Object[] { CTpd.MPR_PDR,
                               CTpd.MPR_INA_SCN,
                               CTpd.MPR_INA_THR,
                               CTpd.MPR_INA_FRT,
                               CTpd.MPR_ORN,
                               CTpd.MPR_AVS,
                               CTpd.MPR_ECT,
                               pmCmpVal,
                               pmPlyVal }, 
                new OPlyInaCPTRowMapper());
        } else {
            oPlyInaCPT = null;
        }

        return oPlyInaCPT;
    }

}
