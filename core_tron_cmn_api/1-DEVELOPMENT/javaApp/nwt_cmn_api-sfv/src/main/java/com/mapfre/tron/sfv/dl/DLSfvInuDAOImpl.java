package com.mapfre.tron.sfv.dl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.cmu.c.CCmu;
import com.mapfre.nwt.dsr.c.CDsr;
import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.thp.c.CThp;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.sfv.bo.OCmnSfvS;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.rule.model.NavRule;

import lombok.extern.slf4j.Slf4j;

/**
 * The SfvInu implementation repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 8 May 2023 - 13:37:57
 *
 */
@Slf4j
@Repository
public class DLSfvInuDAOImpl implements IDLSfvInuDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Consultar el flujo que aplica a los datos de entrada IDN_KEY.
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User value
     * @param lngVal -> Language code
     * @param svfIn  -> Input structure data (agent, channel ...)
     * @return       -> Output structure data
     */
    @Cacheable("Sfv-IdnKeyDataQry")
    @Override
    public List<OCmnSfvS> inStreamIdnKeyDataQry(final BigDecimal cmpVal, final String usrVal, final String lngVal,
            final SfvIn sfvIn) {

        if (log.isInfoEnabled()) {
            log.info("inStreamIdnKeyDataQry querying...");
        }

        final String sql = new StringBuilder()
                .append("SELECT  ")
                .append("    a.flw_idn,")
                .append("    a.frs_lvl_val,")
                .append("    a.scn_lvl_val,")
                .append("    a.thr_lvl_val,")
                .append("    a.frs_dst_hnl_val,")
                .append("    a.scn_dst_hnl_val,")
                .append("    a.thr_dst_hnl_val,")
                .append("    a.agn_val,")
                .append("    a.sec_val,")
                .append("    a.sbs_val,")
                .append("    a.del_val,")
                .append("    a.sbl_val,")
                .append("    a.idn_key, ")
                .append("    a.vld_dat, ")
                .append("    a.cmp_val, ")
                .append("    a.dsb_row, ")
                .append("    a.usr_val, ")
                .append("    a.mdf_dat  ")
                .append("FROM")
                .append("    df_trn_nwt_xx_sfv a ")
                .append("WHERE ")
                .append("        a.flw_idn = ?")                 // sfvIn.flwIdn
                .append("    AND a.cmp_val = ? ")                // sfvIn.cmpVal
                .append("    AND a.frs_lvl_val IN ( ?, ? )")     // sfvIn.filter.frsLvlVal, CCmu.GNC_FRS_LVL
                .append("    AND a.scn_lvl_val IN ( ?, ? )")     // sfvIn.filter.sncLvlval, CCmu.GNC_SCN_LVL
                .append("    AND a.thr_lvl_val IN ( ?, ? )")     // sfvIn.filter.thrLvlVal, CCmu.GNC_THR_LVL
                .append("    AND a.frs_dst_hnl_val IN ( ?, ? )") // sfvIn.filter.frsDstHnlVal, CDsr.GNC_FRS_DST_HNL_VAL
                .append("    AND a.scn_dst_hnl_val IN ( ?, ? )") // sfvIn.filter.scnDstHnlVal, CDsr.GNC_SCN_DST_HNL_VAL
                .append("    AND a.thr_dst_hnl_val IN ( ?, ? )") // sfvIn.filter.thrDstHnlVal, CDsr.GNC_THR_DST_HNL_VAL
                .append("    AND a.agn_val IN ( ?, ? )")         // sfvIn.filter.agnVal, CThp.GNC_AGN_VAL
                .append("    AND a.sec_val IN ( ?, ? )")         // sfvIn.filter.secVal, CPrt.GNC_SEC_VAL
                .append("    AND a.sbs_val IN ( ?, ? )")         // sfvIn.filter.sbsVal, CPrt.GNC_SBS_VAL
                .append("    AND a.del_val IN ( ?, ? )")         // sfvIn.filter.delVal, CPrt.GNC_DEL_VAL
                .append("    AND a.sbl_val IN ( ?, ? )")         // sfvIn.filter.sblVal, CPrt.GNC_SBL_VAL
                .append("    AND a.dsb_row = 'N' ")
                .append("    AND a.vld_dat = ( ")
                .append("        SELECT ")
                .append("            MAX(b.vld_dat) ")
                .append("        FROM ")
                .append("            df_trn_nwt_xx_sfv b")
                .append("        WHERE ")
                .append("                b.cmp_val = a.cmp_val ")
                .append("            AND b.flw_idn = a.flw_idn")
                .append("            AND b.frs_lvl_val = a.frs_lvl_val")
                .append("            AND b.scn_lvl_val = a.scn_lvl_val")
                .append("            AND b.thr_lvl_val = a.thr_lvl_val")
                .append("            AND b.frs_dst_hnl_val = a.frs_dst_hnl_val")
                .append("            AND b.scn_dst_hnl_val = a.scn_dst_hnl_val")
                .append("            AND b.thr_dst_hnl_val = a.thr_dst_hnl_val")
                .append("            AND b.agn_val = a.agn_val")
                .append("            AND b.sec_val = a.sec_val")
                .append("            AND b.sbs_val = a.sbs_val")
                .append("            AND b.del_val = a.del_val")
                .append("            AND b.sbl_val = a.sbl_val")
                .append("            AND b.dsb_row = 'N' ")
                .append("    )  ")
                .append("ORDER BY")
                .append("    ( decode(a.agn_val, ?, 0, 10000000) +")                        // CThp.GNC_AGN_VAL
                .append("      decode(a.thr_dst_hnl_val, ?, 0, 1000000) +")                 // CDsr.GNC_THR_DST_HNL_VAL
                .append("      decode(a.scn_dst_hnl_val, ?, 0, 100000) +")                  // CDsr.GNC_SCN_DST_HNL_VAL
                .append("      decode(a.frs_dst_hnl_val, ?, 0, 10000) +")                   // CDsr.GNC_FRS_DST_HNL_VAL
                .append("      decode(a.thr_lvl_val, ?, 0, 1000) +")                        // CCmu.GNC_THR_LVL
                .append("      decode(a.scn_lvl_val, ?, 0, 100) +")                         // CCmu.GNC_SCN_LVL
                .append("      decode(a.frs_lvl_val, ?, 0, 10) +")                          // CCmu.GNC_FRS_LVL
                .append("      decode(a.sec_val, ?, 0, 1) +")                               // CPrt.GNC_SEC_VAL
                .append("      decode(a.sbs_val, ?, 0, 1) +")                               // CPrt.GNC_SBS_VAL
                .append("      decode(a.del_val, ?, 0, 1) +")                               // CPrt.GNC_DEL_VAL
                .append("      decode(a.sbl_val, ?, 0, 1) ) DESC,")                         // CPrt.GNC_SBL_VAL
                .append("    a.vld_dat DESC,")
                .append("    a.agn_val,")
                .append("    a.thr_dst_hnl_val,")
                .append("    a.scn_dst_hnl_val,")
                .append("    a.frs_dst_hnl_val,")
                .append("    a.thr_lvl_val,")
                .append("    a.scn_lvl_val,")
                .append("    a.frs_lvl_val,")
                .append("    a.sec_val,")
                .append("    a.sbs_val,")
                .append("    a.del_val,")
                .append("    a.sbl_val")
                .toString();

        return jdbcTemplate.query(sql, new Object[] {
                sfvIn.getFlwIdn(),
                cmpVal,
                sfvIn.getFilter().getFrsLvlVal(), CCmu.GNC_FRS_LVL,
                sfvIn.getFilter().getSncLvlval(), CCmu.GNC_SCN_LVL,
                sfvIn.getFilter().getThrLvlVal(), CCmu.GNC_THR_LVL,
                sfvIn.getFilter().getFrsDstHnlVal(), CDsr.GNC_FRS_DST_HNL_VAL,
                sfvIn.getFilter().getScnDstHnlVal(), CDsr.GNC_SCN_DST_HNL_VAL,
                sfvIn.getFilter().getThrDstHnlVal(), CDsr.GNC_THR_DST_HNL_VAL,
                sfvIn.getFilter().getAgnVal(), CThp.GNC_AGN_VAL,
                sfvIn.getFilter().getSecVal(), CPrt.GNC_SEC_VAL,
                sfvIn.getFilter().getSbsVal(), CPrt.GNC_SBS_VAL,
                sfvIn.getFilter().getDelVal(), CPrt.GNC_DEL_VAL,
                sfvIn.getFilter().getSblVal(), CPrt.GNC_SBL_VAL,
                CThp.GNC_AGN_VAL,
                CDsr.GNC_THR_DST_HNL_VAL,
                CDsr.GNC_SCN_DST_HNL_VAL,
                CDsr.GNC_FRS_DST_HNL_VAL,
                CCmu.GNC_THR_LVL,
                CCmu.GNC_SCN_LVL,
                CCmu.GNC_FRS_LVL,
                CPrt.GNC_SEC_VAL,
                CPrt.GNC_SBS_VAL,
                CPrt.GNC_DEL_VAL,
                CPrt.GNC_SBL_VAL
        }, new OCmnSfvSInStreamIdnKeyDataQryRowMapper());
    }

    /**
     * Leer la tabla de parametrización de programas java.
     *
     * @param lvOCmnSfvS -> In structure data
     * @return           -> Out structure data list
     */
    @Cacheable("Sfv-ReadParamProgram")
    @Override
    public List<OCmnSfvS> readParamProgram(final OCmnSfvS oCmnSfvS) {

        if (log.isInfoEnabled()) {
            log.info("readParamProgram querying...");
        }

        final String sql = new StringBuilder()
                .append("SELECT")
                .append("    a.idn_key,  ")
                .append("    a.ste_idn,  ")
                .append("    a.fld_nam,")
                .append("    a.scr_sci,")
                .append("    a.pgm_exn_ord,")
                .append("    a.pgm_typ_val,")
                .append("    a.vld_dat,  ")
                .append("    a.cmp_val,  ")
                .append("    a.pgm_nam,")
                .append("    a.dsb_row,  ")
                .append("    a.usr_val,  ")
                .append("    a.mdf_dat,  ")
                .append("    a.mnr_cpo_val  ")
                .append("FROM")
                .append("    df_trn_nwt_xx_sfv_pgm a ")
                .append("WHERE  ")
                .append("        a.idn_key = ?  ")                                                // oCmnSfvS.idnKey
                .append("    AND a.cmp_val = ?  ")                                                // oCmnSfvS.cmpVal
                .append("    AND a.ste_idn = ?  ")                                                // oCmnSfvS.steIdn
                .append("    AND a.fld_nam = nvl(?, ?)")                                          // oCmnSfvS.fldNam
                .append("    AND a.scr_sci = nvl(?, ?)")                                          // oCmnSfvS.scrSci
                .append("    AND a.pgm_typ_val = ?")                                              // oCmnSfvS.pgmTypVal
                .append("    AND a.dsb_row = 'N'  ")
                .append("    AND a.vld_dat = (  ")
                .append("        SELECT  ")
                .append("            MAX(b.vld_dat)  ")
                .append("        FROM  ")
                .append("            df_trn_nwt_xx_sfv_pgm b")
                .append("        WHERE  ")
                .append("                b.cmp_val = a.cmp_val  ")
                .append("            AND b.idn_key = a.idn_key  ")
                .append("            AND b.ste_idn = a.ste_idn  ")
                .append("            AND b.fld_nam = a.fld_nam")
                .append("            AND b.scr_sci = a.scr_sci")
                .append("            AND b.pgm_exn_ord = a.pgm_exn_ord")
                .append("            AND b.pgm_typ_val = a.pgm_typ_val")
                .append("            AND b.dsb_row = 'N'  ")
                .append("    ) ")
                .append("ORDER BY  ")
                .append("    a.pgm_exn_ord")
                .toString();

        return jdbcTemplate.query(sql, new Object[] {
                oCmnSfvS.getIdnKey(),
                oCmnSfvS.getCmpVal(),
                oCmnSfvS.getSteIdn(),
                oCmnSfvS.getFldNam(),
                CTrn.GNC_FLD_NAM,
                oCmnSfvS.getScrSci(),
                CTrn.GNC_SCR_SCI,
                oCmnSfvS.getPgmTypVal()
        }, new OCmnSfvSReadParamProgramRowMapper());
    }

    /**
     * Recupera el mensaje de error personalizado.
     *
     * @param oCmnSfvS -> In structure data
     * @param cmpVal   -> Company code
     * @param usrVal   -> User value
     * @param lngVal   -> Language code
     * @return         -> Out structure data
     */
    @Cacheable("Sfv-ReadErrMsgTxt")
    @Override
    public OCmnSfvS readErrMsgTxt(final OCmnSfvS oCmnSfvS, final BigDecimal cmpVal, final String usrVal,
            final String lngVal) {

        if (log.isInfoEnabled()) {
            log.info("readErrMsgTxt querying...");
        }

        final String sql = new StringBuilder()
                .append("SELECT ")
                .append("    a.idn_key,")
                .append("    a.ste_idn,")
                .append("    a.fld_nam,")
                .append("    a.scr_sci,")
                .append("    a.msg_val,")
                .append("    a.lng_val,")
                .append("    a.vld_dat,")
                .append("    a.cmp_val,")
                .append("    a.msg_txt_val,")
                .append("    a.dsb_row,")
                .append("    a.usr_val,")
                .append("    a.mdf_dat ")
                .append("FROM")
                .append("    df_trn_nwt_xx_sfv_msg a ")
                .append("WHERE")
                .append("        a.idn_key = ?")                                                     // oCmnSfvS.idnKey
                .append("    AND a.ste_idn = ?")                                                     // oCmnSfvS.steIdn
                .append("    AND a.fld_nam = nvl(?, ?)")                                             // oCmnSfvS.fldNam
                .append("    AND a.scr_sci = nvl(?, ?)")                                             // oCmnSfvS.scrSci
                .append("    AND a.msg_val = ?")                                                     // oCmnSfvS.msgVal
                .append("    AND a.lng_val = ?")                                                     // oCmnSfvS.lngVal
                .append("    AND a.cmp_val = ?")                                                     // oCmnSfvS.cmpVal
                .append("    AND a.dsb_row = 'N'")
                .append("    AND a.vld_dat = (")
                .append("        SELECT")
                .append("            MAX(b.vld_dat)")
                .append("        FROM")
                .append("            df_trn_nwt_xx_sfv_msg b")
                .append("        WHERE")
                .append("                b.cmp_val = a.cmp_val")
                .append("            AND b.idn_key = a.idn_key")
                .append("            AND b.ste_idn = a.ste_idn")
                .append("            AND b.fld_nam = a.fld_nam")
                .append("            AND b.scr_sci = a.scr_sci")
                .append("            AND b.msg_val = a.msg_val")
                .append("            AND b.lng_val = a.lng_val")
                .append("            AND b.dsb_row = 'N'")
                .append("    )")
                .toString();

        return jdbcTemplate.queryForObject(sql,
                new Object[] {
                    oCmnSfvS.getIdnKey(),
                    oCmnSfvS.getSteIdn(),
                    oCmnSfvS.getFldNam(),
                    CTrn.GNC_FLD_NAM,
                    oCmnSfvS.getScrSci(),
                    CTrn.GNC_SCR_SCI,
                    oCmnSfvS.getMsgVal(),
                    lngVal,
                    cmpVal
                },
                new OCmnSfvSReadErrMsgTxtRowMapper());

    }

    /**
     * Recuperar las reglas de navegacion.
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User value
     * @param lngVal -> Language code
     * @param idnKey -> Identification key
     * @param steIdn -> Step identification
     * @return       -> Navigation rules list
     */
    @Cacheable("Sfv-ReadNavRules")
    @Override
    public List<NavRule> readNavRules(final BigDecimal cmpVal, final String usrVal, final String lngVal,
            final String idnKey, final String steIdn) {

        if (log.isInfoEnabled()) {
            log.info("readParamProgram querying...");
        }

        final String sql = new StringBuilder()
                .append("SELECT")
                .append("    a.idn_key,")
                .append("    a.ste_idn,")
                .append("    a.ord_rul_val,")
                .append("    a.vld_dat,")
                .append("    a.cmp_val,")
                .append("    a.rul_val_txt,")
                .append("    a.nxt_ste_idn,")
                .append("    a.pmn_nvg_prr_ste,")
                .append("    a.pmn_nvg_wht_prr_ste,")
                .append("    a.dsb_row,")
                .append("    a.usr_val,")
                .append("    a.mdf_dat ")
                .append("FROM")
                .append("    df_trn_nwt_xx_sfv_tsn a ")
                .append("WHERE")
                .append("        a.idn_key = ?")                                                              // idnKey
                .append("    AND a.cmp_val = ?")                                                              // cmpVal
                .append("    AND a.ste_idn = ?")                                                              // steIdn
                .append("    AND a.dsb_row = 'N'")
                .append("    AND a.vld_dat = (")
                .append("        SELECT")
                .append("            MAX(b.vld_dat)")
                .append("        FROM")
                .append("            df_trn_nwt_xx_sfv_tsn b")
                .append("        WHERE")
                .append("                b.cmp_val = a.cmp_val")
                .append("            AND b.idn_key = a.idn_key")
                .append("            AND b.ste_idn = a.ste_idn")
                .append("            AND b.ord_rul_val = a.ord_rul_val")
                .append("            AND b.dsb_row = 'N'")
                .append("    ) ")
                .append("ORDER BY")
                .append("    a.ord_rul_val")
                .toString();

        return jdbcTemplate.query(sql,
                new Object[] {
                        idnKey,
                        cmpVal,
                        steIdn
                },
                new NavRuleRowMapper());
    }

}
