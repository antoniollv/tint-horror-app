package com.mapfre.tron.api.cmn.mvr.dl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;

import lombok.Data;

@Data
@Repository
public class IBlCmnMvrCrtDAOImpl implements IBlCmnMvrCrtDAO {
    
    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Create movement record for a third party
     * 
     * @author Cristian Saball
     * 
     * @param lngVal           -> Language code
     * @param usrVal           -> User code
     * @param cmpVal           -> Company code
     * @param thpDcmTypVal     -> Document type
     * @param thpDcmVal        -> Document
     * @param thpAcvVal        -> Activity
     * @param thpAcvVal        -> Activity
     * @param inMovementRecord -> Input data to new movement record
     * @return -> void
     */
    @Override
    public int mvrOchUpdate(String lngVal, String usrVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal,
            Integer thpAcvVal, OCmnMvrS inMovementRecord) {

        final String sql = new StringBuilder()
                .append(" UPDATE ( ")
                .append(" SELECT a.* FROM rl_cmn_nwt_xx_mvr a ")
                .append(" INNER JOIN rl_thp_nwt_xx_stv b ON a.rsk_idn = b.rsk_idn AND a.cmp_val = b.cmp_val AND a.sqn_val = b.sqn_val ")
                .append(" WHERE a.cmp_val = ? ")
                .append(" AND b.thp_acv_val = ? ")
                .append(" AND b.thp_dcm_typ_val = ? ")
                .append(" AND b.thp_dcm_val = ? ")
                .append(" AND a.rsk_idn = ? ")
                .append(" AND a.sqn_val = ? ) t ")
                .append(" SET t.mvm_stt_typ_val = NVL(?,t.mvm_stt_typ_val), ")
                .append(" t.mvm_pbl= NVL(?,t.mvm_pbl), ")
                .append(" t.usr_val = ?, ")
                .append(" t.mdf_dat = SYSDATE , ")
                .append(" t.mvm_tme = to_char (SYSDATE,'HH24:MI'), ")
                .append(" t.sts_typ_val = NVL(?,t.sts_typ_val), ")
                .append(" t.err_txt_val = NVL(?, t.err_txt_val) ")
                .toString();

        int lvVod = jdbcTemplate.update(sql,
                cmpVal,
                thpAcvVal,
                thpDcmTypVal,
                thpDcmVal,
                inMovementRecord.getRskIdn(),
                inMovementRecord.getSqnVal(),
                inMovementRecord.getMvmSttTypVal(),
                inMovementRecord.getMvmPbl(),
                usrVal,
                inMovementRecord.getStsTypVal(),
                inMovementRecord.getErrTxtVal());

        return lvVod;

    }

    @Override
    public int mvrOchInsert(String lngVal, String usrVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal,
            Integer thpAcvVal, OCmnMvrS inMovementRecord) {

        String rskIdnCalculado = null;
        rskIdnCalculado = cmpVal.toString() + '-' + thpDcmTypVal + '-' + thpDcmVal;
        final String sql1 = new StringBuilder()
                .append(" select nvl(max(a.sqn_val),0) AS sqnValMax ")
                .append(" FROM rl_thp_nwt_xx_stv a ")
                .append(" WHERE a.cmp_val = ? ")
             //   .append(" AND a.thp_acv_val     = ? ")
                .append(" AND a.thp_dcm_typ_val = ? ")
                .append(" AND a.thp_dcm_val     = ? ").toString();

        Integer sqnValMax = jdbcTemplate.queryForObject(sql1,
                new Object[] { cmpVal, thpDcmTypVal, thpDcmVal },
                new sqnValMaxRowMapper());

        final String sql2 = new StringBuilder()
                .append(" insert into rl_thp_nwt_xx_stv ")
                .append(" (CMP_VAL, ")
                .append(" RSK_IDN, ")
                .append(" SQN_VAL, ")
                .append(" THP_ACV_VAL, ")
                .append(" BNF_TYP_VAL, ")
                .append(" THP_DCM_TYP_VAL, ")
                .append(" THP_DCM_VAL, ")
                .append(" USR_VAL, ")
                .append(" MDF_DAT) ")
                .append(" values ")
                .append(" ( ?, ") //cmpVal
                .append(" ?, ") //rskIdnCalculado
                .append(" ?, ") //sqnValMax + 1
                .append(" ?, ") //thpAcvVal
                .append(" 0, ")
                .append(" ?, ") //thpDcmTypVal
                .append(" ?, ") //thpDcmVal
                .append(" ?, ") //usrVal
                .append(" SYSDATE ) ")
                .toString();

        jdbcTemplate.update(sql2, cmpVal, rskIdnCalculado, sqnValMax + 1, thpAcvVal, thpDcmTypVal, thpDcmVal, usrVal);

        Date pmMvmDat = (inMovementRecord.getMvmDat() != null) ? inMovementRecord.getMvmDat() : null;

        StringBuilder sql3 = new StringBuilder()
                .append(" insert into rl_cmn_nwt_xx_mvr ")
                .append(" (CMP_VAL, ")
                .append(" RSK_IDN, ")
                .append(" SQN_VAL, ")
                .append(" MVM_IDN, ")
                .append(" MVM_DAT, ")
                .append(" MVM_TME, ")
                .append(" THR_DST_HNL_VAL, ")
                .append(" MVM_STT_TYP_VAL, ")
                .append(" MVM_STT_DAT, ")
                .append(" MVM_PBL, ")
                .append(" QTN_VAL, ") 
                .append(" PLY_VAL, ") 
                .append(" RCP_VAL, ") 
                .append(" LSS_VAL, ") 
                .append(" PYM_ORD_VAL, ")
                .append(" MVM_PRC_IDN, ") 
                .append(" MVM_PRC_NAM, ") 
                .append(" MVM_SUC_IDN, ") 
                .append(" MVM_SUC_NAM, ")
                .append(" MVM_RSN_IDN, ")
                .append(" MVM_RSN_NAM, ")
                .append(" USR_VAL, ")
                .append(" MDF_DAT, ")
                .append(" RSK_VAL, ")
                .append(" TIT_VAL, ")
                .append(" DSP_VAL, ")
                .append(" STS_TYP_VAL, ")
                .append(" ERR_TXT_VAL, ")
                .append(" PRC_TYP_VAL, ")
                .append(" AGR_VAL_PRN, ")
                .append(" ETC_VAL, ")
                .append(" NTE_VAL) ")
                .append(" values ")
                .append(" (?, ") //cmpVal
                .append(" ?, ") //rskIdnCalculado
                .append(" ?, ") //sqnValMax + 1
                .append(" ?, ") //oCmnMvrS.mvmIdn
                .append(" NVL( ? ,SYSDATE), "); //oCmnMvrS.mvmDat

        if (pmMvmDat != null) {
            sql3.append(" to_char(NVL(?,SYSDATE), 'HH24:MI'), ");// oCmnMvrS.mvmDat
        } else {
            sql3.append(" to_char(SYSDATE, 'HH24:MI'), ");
        }

        sql3.append(" ?, ") //oCmnMvrS.thrDstHnl
                .append(" NVL(?, '02'), ") //oCmnMvrS.mvmSttTypVal
                .append(" ?, ") //oCmnMvrS.mvmSttDat
                .append(" ?, ") //oCmnMvrS.mvmPbl
                .append(" ?, ") //oCmnMvrS.qtnVal
                .append(" ?, ") //oCmnMvrS.plyVal
                .append(" ?, ") //oCmnMvrS.rcpVal
                .append(" ?, ") //oCmnMvrS.lssVal
                .append(" ?, ") //oCmnMvrS.pymOrdVal
                .append(" ?, ") //oCmnMvrS.mvmPrcIdn
                .append(" ?, ") //oCmnMvrS.mvmPrcNam
                .append(" ?, ") //oCmnMvrS.mvmSucIdn
                .append(" ?, ") //oCmnMvrS.vmSucNam
                .append(" ?, ") //oCmnMvrS.mvmRsnIdn
                .append(" ?, ") //oCmnMvrS.mvmRsnNam
                .append(" ?, ") //usrVal
                .append(" SYSDATE, ")
                .append(" ?, ") //oCmnMvrS.rskVal
                .append(" ?, ") //oCmnMvrS.titVal
                .append(" ?, ") //oCmnMvrS.dspVal
                .append(" ?, ") //oCmnMvrS.stsTypVal
                .append(" ?, ") //oCmnMvrS.errTxtval
                .append(" ?, ") //oCmnMvrS.prcTypVal
                .append(" ?, ") //oCmnMvrS.agrValPrn
                .append(" ?, ") //oCmnMvrS.etcval
                .append(" ? ) "); ////oCmnMvrS.nteVal

        int lvVod2 = 0;

        if (pmMvmDat != null) {
            lvVod2 = jdbcTemplate.update(sql3.toString(),
    		cmpVal, 
    		rskIdnCalculado, 
    		sqnValMax + 1, 
    		inMovementRecord.getMvmIdn(),
    		pmMvmDat,
    		pmMvmDat,
    		inMovementRecord.getThrDstHnlVal(),
    		inMovementRecord.getMvmSttTypVal(),
    		inMovementRecord.getMvmSttDat(),
    		inMovementRecord.getMvmPbl(),
    		inMovementRecord.getQtnVal(),
    		inMovementRecord.getPlyVal(),
    		inMovementRecord.getRcpVal(),
    		inMovementRecord.getLssVal(),
    		inMovementRecord.getPymOrdVal(),
    		inMovementRecord.getMvmPrcIdn(),
    		inMovementRecord.getMvmPrcNam(),
    		inMovementRecord.getMvmSucIdn(),
    		inMovementRecord.getMvmSucNam(),
    		inMovementRecord.getMvmRsnIdn(),
    		inMovementRecord.getMvmRsnNam(),
    		usrVal,
    		inMovementRecord.getRskVal(),
    		inMovementRecord.getTitVal(),
    		inMovementRecord.getDspVal(),
    		inMovementRecord.getStsTypVal(),
    		inMovementRecord.getErrTxtVal(),
    		inMovementRecord.getPrcTypVal(),
    		inMovementRecord.getAgrValPrn(),
    		inMovementRecord.getEtcVal(),
    		inMovementRecord.getNteVal()
    		);
       } else {
	    lvVod2 = jdbcTemplate.update(sql3.toString(),
			cmpVal, 
			rskIdnCalculado, 
			sqnValMax + 1, 
			inMovementRecord.getMvmIdn(),
			pmMvmDat,
			inMovementRecord.getThrDstHnlVal(),
			inMovementRecord.getMvmSttTypVal(),
			inMovementRecord.getMvmSttDat(),
			inMovementRecord.getMvmPbl(),
			inMovementRecord.getQtnVal(),
			inMovementRecord.getPlyVal(),
			inMovementRecord.getRcpVal(),
			inMovementRecord.getLssVal(),
			inMovementRecord.getPymOrdVal(),
			inMovementRecord.getMvmPrcIdn(),
			inMovementRecord.getMvmPrcNam(),
			inMovementRecord.getMvmSucIdn(),
			inMovementRecord.getMvmSucNam(),
			inMovementRecord.getMvmRsnIdn(),
			inMovementRecord.getMvmRsnNam(),
			usrVal,
			inMovementRecord.getRskVal(),
			inMovementRecord.getTitVal(),
			inMovementRecord.getDspVal(),
			inMovementRecord.getStsTypVal(),
			inMovementRecord.getErrTxtVal(),
			inMovementRecord.getPrcTypVal(),
			inMovementRecord.getAgrValPrn(),
			inMovementRecord.getEtcVal(),
			inMovementRecord.getNteVal()

			);
       }

        return lvVod2;
    }

    /**
     * Update rl_cmn_nwt_xx_mvr.
     *
     * @param cmpVal    -> Company code
     * @param rskIdn    -> The rskIdn property
     * @param sqnVal    -> The sqnVal property
     * @param errTxtVal -> The error txt value
     * @param stsTypVal -> The sts typ value
     * @param mvmPbl    -> The mvmPbl property
     * @return          -> The result of the update
     */
    @Override
    public int rlCmnNwtXxMvrUpdate(final BigDecimal cmpVal, final String rskIdn, final BigDecimal sqnVal,
            String errTxtVal, final String stsTypVal, final String mvmPbl) {

        int lvVod = 0;
        if (errTxtVal == null) {
            errTxtVal = "";
        }

        final String sql = new StringBuilder()
                .append("UPDATE rl_cmn_nwt_xx_mvr ")
                .append("SET mvm_pbl   =  NVL(?, mvm_pbl),")
                .append("  sts_typ_val = ?,")
                .append("  err_txt_val = ?,") // errTxtVal                
                .append("  mvm_stt_dat = SYSDATE,") // errTxtVal
                .append("  mdf_dat = SYSDATE ")
                .append("WHERE cmp_Val = ? ") // oCmnMvrS.cmpVal
                .append("AND rsk_idn   = ? ") // oCmnMvrS.rskIdn
                .append("AND sqn_val   = ? ") // oCmnMvrS.sqnVal
                .toString();

        jdbcTemplate.update(sql, mvmPbl, stsTypVal, errTxtVal, cmpVal, rskIdn, sqnVal);

        return lvVod;
    }

}
