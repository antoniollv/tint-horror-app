package com.mapfre.tron.api.cmn.mvr.dl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;

import lombok.Data;

/**
 * @author Javier Sangil
 * @version 04/06/2021
 *
 */
@Data
@Repository
public class SrCmnMvsQryDAOImpl implements ISrCmnMvsQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Query movements record by third party
     * 
     * @author Javier Sangil
     * 
     * @param lngVal           -> Language code
     * @param usrVal           -> User code
     * @param cmpVal           -> Company code
     * @param thpDcmTypVal     -> Document type
     * @param thpDcmVal        -> document
     * @param thpAcvVal        -> Activity
     * @param mvmIdn           -> Third Channel Distribution
     * @param qryDat           -> Initial Date
     * @param mvmSttTypVal     -> Movement state type
     * @param mvmPbl           -> Public Movement
     * @param stsTypVal        -> Status Type Value
     * @param prcTypVal        -> Process Type value
     * @param orderByAgrValPrn -> Order the query by field agrValPrn
     * @return                 -> List<OCmnMvrS>
     */
    @Override
    public List<OCmnMvrS> dcmTbl(String lngVal, String usrVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal,
            Integer thpAcvVal, List<String> mvmIdn, Long qryDat, String mvmSttTypVal, String mvmPbl,
            List<String> stsTypVal, String prcTypVal, boolean orderByAgrValPrn) {

        String mvmIdnString = "";
        if (mvmIdn != null && !mvmIdn.isEmpty()) {
            mvmIdnString = "AND a.mvm_idn IN ";// ? ";
            mvmIdnString = mvmIdnString.concat(" (");
            for (String i : mvmIdn) {
                mvmIdnString = mvmIdnString.concat(i + ",");
            }
            mvmIdnString = mvmIdnString.substring(0, mvmIdnString.length() - 1);
            mvmIdnString = mvmIdnString.concat(") ");
        }

        String stsTypValString = "";
        if (stsTypVal != null && !stsTypVal.isEmpty()) {
            stsTypValString = "AND a.sts_Typ_Val IN ";// ? ";
            stsTypValString = stsTypValString.concat(" (");
            for (String i : stsTypVal) {
                stsTypValString = stsTypValString.concat(i + ",");
            }
            stsTypValString = stsTypValString.substring(0, stsTypValString.length() - 1);
            stsTypValString = stsTypValString.concat(") ");
        }

        String mvmSttTypValString = "";
        if (mvmSttTypVal != null)
            mvmSttTypValString = "AND a.mvm_Stt_Typ_Val = NVL('" + mvmSttTypVal + "' , a.mvm_Stt_Typ_Val) ";

        String mvmPblString = "";
        if (mvmPbl != null)
            mvmPblString = "AND a.mvm_pbl = NVL('" + mvmPbl + "', a.mvm_pbl) ";
        String prcTypValString = "";
        if (prcTypVal != null)
            prcTypValString = "AND a.prc_typ_val = NVL ('" + prcTypVal + "',a.prc_typ_val) ";

        Date inlDatD = (qryDat != null) ? new Date(qryDat) : null;

        StringBuilder query = new StringBuilder()
		.append("SELECT a.cmp_val, ")
		.append("a.rsk_idn, ")
		.append("a.sqn_val, ")
		.append("a.mvm_idn, ")
		.append("a.mvm_dat, ")
		.append("a.mvm_tme, ")
		.append("a.thr_dst_hnl_val, ")
		.append("a.mvm_stt_typ_val, ")
		.append("a.mvm_stt_dat, ")
		.append("a.mvm_pbl, ")
		.append("a.qtn_val, ")
		.append("a.ply_val, ")
		.append("a.rcp_val, ")
		.append("a.lss_val, ")
		.append("a.pym_ord_val, ")
		.append("a.mvm_prc_idn, ")
		.append("a.mvm_prc_nam, ")
		.append("a.mvm_suc_idn, ")
		.append("a.mvm_suc_nam, ")
		.append("a.mvm_rsn_idn, ")
		.append("a.mvm_rsn_nam, ")
		.append("a.rsk_val, ")
		.append("a.tit_val, ")
		.append("a.dsp_val, ")
		.append("a.sts_typ_val, ")
		.append("a.err_txt_val, ")
		.append("a.prc_typ_val, ")
		.append("a.agr_val_prn, ")
		.append("a.etc_val ")
		.append("FROM rl_cmn_nwt_xx_mvr a ")
		.append("INNER JOIN rl_thp_nwt_xx_stv b ON a.rsk_idn = b.rsk_idn AND a.cmp_val = b.cmp_val AND a.sqn_val = b.sqn_val ")
		//.append("INNER JOIN df_cmn_nwt_xx_mvs c ON c.mvm_idn = a.mvm_idn AND c.lng_val = ? ")
		.append("WHERE a.cmp_val = ? ")// – parámetro de entrada cmpVal 
		.append("AND b.thp_acv_val = NVL(?, b.thp_acv_val) ") //– parámetro de entrada thpAcvVal 
		.append("AND b.thp_dcm_typ_val = NVL(?,b.thp_dcm_typ_val ) ") //– parámetro de entrada thpDcmTypVal
		.append("AND b.thp_dcm_val = NVL(?, b.thp_dcm_val) ") //– parámetro de entrada thpDcmVal
		.append(mvmIdnString) //– parámetro de entrada mvmIdn de tipo array 
		.append(mvmPblString)//– parámetro de entrada mvmPbl 
		.append(mvmSttTypValString) //– parámetro de entrada mvmSttTypVal
		.append(stsTypValString) //– parámetro de entrada stsTypVal de tipo array
		.append("AND a.mvm_dat >= NVL(? , a.mvm_dat) ") //– parámetro de entrada mvmDat
		.append(prcTypValString); //– parámetro de entrada prcTypVal

        if (orderByAgrValPrn) {
            query.append(" ORDER BY a.agr_val_prn,");
            query.append("  a.rsk_idn");
        } else {
            query.append(" ORDER BY a.mvm_dat DESC,");
            query.append("  a.mvm_tme DESC");
        }

        List<OCmnMvrS> oCmnMvrCPT = jdbcTemplate.query(query.toString(), 
                new Object[] {
                        // lngVal,
                        cmpVal, thpAcvVal, thpDcmTypVal, thpDcmVal, inlDatD },
                new OCmnMvsSRowMapper());

        return oCmnMvrCPT;
    }

}
