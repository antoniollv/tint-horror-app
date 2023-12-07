package com.mapfre.tron.api.cmu.thl.dl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntP;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

import lombok.Data;


/**
 * The CmuThlCntQry repository.
 *
 * @author Cristian Saball
 * @since 1.8
 * @version 08 feb. 2021 13:08:36
 *
 */
@Data
@Repository
public class SrCmuThlCntQryDAOImpl implements ISrCmuThlCntQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public OThpCntP get(final Integer cmpVal, final String lngVal, final String usrVal, final Integer thrLvlVal) {

        final String query = new StringBuilder()
                .append("SELECT ")
                .append(" a.cod_cia, ")
                .append(" a.tip_docum, ") 
                .append(" a.cod_docum, ")
                .append(" a.tlf_pais, ")
                .append(" a.tlf_zona, ")
                .append(" a.tlf_numero, ")
                .append(" a.fax_numero, ")  
                .append(" a.email, ")
                .append(" a.nom_resp, ")
                .append(" a.ape_resp ")
                .append(" FROM a1000702 a ")
                .append(" WHERE a.cod_cia = ? ") //cmpVal
                .append(" AND a.cod_nivel3 =  ? ") //thrLvlVal
                .toString();

        return jdbcTemplate.queryForObject(query,
                new Object[] { cmpVal, thrLvlVal },
                new OThpCntRowMapper());

    }

    /**
     * Query the third level contact information.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return          -> It will return the contact of the third level of the commercial structure
     */
    @Override
    public List<OThpCntS> getContactFromThirdLevelv1(final Integer cmpVal, final String lngVal, final String usrVal,
            final Integer thrLvlVal) {

        final String sql = new StringBuilder()
            .append("  SELECT DISTINCT  a.cmp_val         ,")
            .append("                   a.thp_dcm_typ_val ,")
            .append("                   a.thp_dcm_val     ,")
            .append("                   a.thp_acv_val     ,")
            .append("                   a.idn_thp_val     ,")
            .append("                   a.cnh_typ_val     ,")
            .append("                   a.pst_typ_val     ,")
            .append("                   a.dsb_row         ,")
            .append("                   a.usr_val         ,")
            .append("                   a.obs_val         ,")
            .append("                   a.mdf_dat         ,")
            .append("                   a.vld_dat         ,")
            .append("                   a.cnh_use_typ_val ,")
            .append("                   a.cnh_txt_val     ,")
            .append("                   a.cnh_sqn_val     ,")
            .append("                   a.cnh_cck         ,")
            .append("                   a.ext_cnt_dpr_val ,")
            .append("                   a.cnh_nam         ,")
            .append("                   a.dfl_cnh         ,")
            .append("                   a.pty_cnh         ,")
            .append("                   a.cnt_pst_val     ,")
            .append("                   a.cnt_dpr_val     ,")
            .append("                   a.cnt_frs_scn_srn ,")
            .append("                   a.cnt_dcm_typ_val ,")
            .append("                   a.cnt_dcm_val     ,")
            .append("                   a.cnt_cny_val     ")
            .append("           FROM df_thp_nwt_xx_cnt a")
            .append(" INNER JOIN a1000702 b ")
            .append(" ON b.cod_cia = a.cmp_val AND b.TIP_DOCUM = a.thp_dcm_typ_val AND b.COD_DOCUM = a.thp_dcm_val AND b.COD_ACT_TERCERO = a.thp_acv_val") 
            .append(" WHERE b.cod_cia = ? ")                                                           // cmpVal
            .append(" AND b.cod_nivel3 =  ? ")                                                         // thrLvlVal
            .append("                               ")
            .append("            AND a.dsb_row         = ? ")                                          //nwt_o.c_ins.NO
            .append("            AND a.vld_dat         = (SELECT MAX (vld_dat)")
            .append("                                     FROM df_thp_nwt_xx_cnt c")
            .append("                                     WHERE c.cmp_val         = a.cmp_val")
            .append("                                       AND c.thp_dcm_typ_val = a.thp_dcm_typ_val")
            .append("                                       AND c.thp_dcm_val     = a.thp_dcm_val")
            .append("                                       AND c.thp_acv_val     = a.thp_acv_val")
            .append("                                       AND c.cnh_sqn_val     = a.cnh_sqn_val  ) ")
            .toString();

            return jdbcTemplate.query(sql,
                    new Object[] { cmpVal, thrLvlVal, CIns.NO },
                    new OThpCntSRowMapper());
    }

    /**
     * Query Address From Third Level 1.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return          -> It will return the address of the third level of the commercial structure.
     */
    @Override
    public List<OThpAdrS> getAddresstFromThirdLevelv1(final Integer cmpVal, final String lngVal, final String usrVal,
            final Integer thrLvlVal) {

        final String sql = new StringBuilder()
            .append(" SELECT DISTINCT ")
            .append(" a.cmp_val,")
            .append(" a.thp_dcm_typ_val  ,")
            .append(" a.thp_dcm_val ,")
            .append(" a.thp_acv_val ,")
            .append(" a.idn_thp_val ,")
            .append(" a.adr_sqn_val ,")
            .append(" a.dml_typ_val ,")
            .append(" a.adr_txt_val ,")
            .append(" a.adr_nbr_val ,")
            .append(" a.ext_adr_txt_val  ,")
            .append(" a.ext_cny_txt_val  ,")
            .append(" a.cny_val ,")
            .append(" a.stt_val ,")
            .append(" a.pvc_val ,")
            .append(" a.twn_val ,")
            .append(" a.psl_cod_val ,")
            .append(" a.obs_val ,")
            .append(" a.dsb_row ,")
            .append(" a.usr_val ,")
            .append(" a.vld_dat ,")
            .append(" a.lnt_val ,")
            .append(" a.ltt_val ,")
            .append(" a.mdf_dat ,")
            .append(" a.dfl_adr ,")
            .append(" a.adr_cck ,")
            .append(" a.adr_use_typ_val ,")
            .append(" a.dit_val")
            .append("           FROM df_thp_nwt_xx_adr a")
            .append(" INNER JOIN a1000702 b ")
            .append(" ON b.cod_cia = a.cmp_val AND b.TIP_DOCUM=a.thp_dcm_typ_val AND b.COD_DOCUM=a.thp_dcm_val AND b.COD_ACT_TERCERO=a.thp_acv_val") 
            .append(" WHERE b.cod_cia = ? ")                                                           // cmpVal
            .append(" AND b.cod_nivel3 =  ? ")                                                         // thrLvlVal
            .append("                               ")
            .append("            AND a.dsb_row         = ? ")                                          //nwt_o.c_ins.NO
            .append("            AND a.vld_dat         = (SELECT MAX (vld_dat)")
            .append("                                     FROM df_thp_nwt_xx_adr c")
            .append("                                     WHERE c.cmp_val         = a.cmp_val")
            .append("                                       AND c.thp_dcm_typ_val = a.thp_dcm_typ_val")
            .append("                                       AND c.thp_dcm_val     = a.thp_dcm_val")
            .append("                                       AND c.thp_acv_val     = a.thp_acv_val")
            .append("                                       AND c.adr_sqn_val     = a.adr_sqn_val  ) ")
            .toString();

        return jdbcTemplate.query(sql,
                new Object[] { cmpVal, thrLvlVal, CIns.NO },
                new OThpAdrSRowMapper());
    }

}
