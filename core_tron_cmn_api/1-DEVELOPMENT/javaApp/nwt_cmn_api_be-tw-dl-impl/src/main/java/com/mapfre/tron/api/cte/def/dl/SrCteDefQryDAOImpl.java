package com.mapfre.tron.api.cte.def.dl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.cmu.c.CCmu;
import com.mapfre.nwt.crn.c.CCrn;
import com.mapfre.nwt.dsr.c.CDsr;
import com.mapfre.nwt.pid.c.CPid;
import com.mapfre.nwt.ply.c.CPly;
import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.thp.c.CThp;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrCPT;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.cmn.model.InConstantInformation;

import lombok.Data;

/**
 * The CteDefQry repository.
 *
 * @author magarafr
 * @since 1.8
 * @version 20 ene. 2021 13:08:36
 *
 */
@Data
@Repository
public class SrCteDefQryDAOImpl implements ISrCteDefQryDAO {
    
    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @Override
    public OPlyAtrCPT xxCnn(InConstantInformation inConstantInformation, String strFldNam) {
	
	if(strFldNam == null) {
	    strFldNam = "";
	}
	
	final String query = new StringBuilder()
                .append("SELECT t.vrb_nam, ")
                .append("t.vrb_nam_val ")
                .append("FROM df_cmn_nwt_xx_cnn t ")
                .append("where t.cmp_val = ? ")
                .append("and t.lob_val = ? ")
		.append(" and t.mdt_val = NVL( ? , "+CPly.GNC_MDT+") ")
		.append(" and t.crn_val = NVL( ? , "+CCrn.GNC_CRN+") ")
		.append(" and t.cvr_val = NVL( ? , "+CPly.GNC_CVR+") ")
		.append(" and t.frs_lvl_val = NVL( ? , "+CCmu.GNC_FRS_LVL+") ")
		.append(" and t.scn_lvl_val = NVL( ? , "+CCmu.GNC_SCN_LVL+") ")
		.append(" and t.thr_lvl_val = NVL( ? , "+CCmu.GNC_THR_LVL+") ")
		.append(" and t.frs_dst_hnl_val= NVL( ? , '"+CDsr.GNC_FRS_DST_HNL_VAL+"') ")
		.append(" and t.scn_dst_hnl_val= NVL( ? , '"+CDsr.GNC_SCN_DST_HNL_VAL+"') ")
		.append(" and t.thr_dst_hnl_val= NVL( ? , '"+CDsr.GNC_THR_DST_HNL_VAL+"') ")
		.append(" and t.agn_val= NVL( ? , "+CThp.GNC_AGN_VAL+") ")
		.append(" and t.gpp_val= NVL( ? , '"+CPid.GNC_GPP_VAL+"') ")
		.append(" and t.del_val= NVL( ? , "+CPrt.GNC_DEL_VAL+") ")
		.append(" and t.sbl_val= NVL( ? , "+CPrt.GNC_SBL_VAL+") ")
		.append(" and t.ply_val= NVL( ? , '"+CPly.GNC_QTN+"') ")
                .append("and t.vrb_nam in (" + strFldNam + ") ")
                .append("and dsb_row='N' ")
                .append("and (t.vrb_nam,t.VLD_DAT) ")
                .append("IN (SELECT a.vrb_nam,MAX(a.VLD_DAT) ")
                .append("FROM df_cmn_nwt_xx_cnn a ")
                .append("where a.cmp_val=t.cmp_val ")
                .append("and a.lob_val=t.lob_val ")
                .append("and a.mdt_val=t.mdt_val ")
                .append("and a.cvr_val=t.cvr_val ")
                .append("and a.frs_lvl_val=t.frs_lvl_val ")
                .append("and a.scn_lvl_val=t.scn_lvl_val ")
                .append("and a.crn_val=t.crn_val ")
                .append("and a.agn_val=t.agn_val ")
                .append("and a.del_val=t.del_val ")
                .append("and a.sbl_val=t.sbl_val ")
                .append("and a.gpp_val=t.gpp_val ")
                .append("and a.ply_val=t.ply_val ")
                .append("and a.vrb_nam=t.vrb_nam ")
                .append("and a.FRS_DST_HNL_VAL=t.FRS_DST_HNL_VAL ")
                .append("AND A.SCN_DST_HNL_VAL=T.SCN_DST_HNL_VAL ")
                .append("AND A.THR_DST_HNL_VAL=T.THR_DST_HNL_VAL ")
                .append("group by a.vrb_nam,a.vld_dat) ")
                .toString();
	
	 List<OPlyAtrP> lvOPlyAtrP = jdbcTemplate.query(
	                query,
	                new Object[] {inConstantInformation.getCmpVal(),
	                	inConstantInformation.getLobVal(),
	                	inConstantInformation.getMdtVal(),
	                	inConstantInformation.getCrnVal(),
	                	inConstantInformation.getCvrVal(),
	                	
	                	inConstantInformation.getFrsLvlVal(),
	                	inConstantInformation.getScnLvlVal(),
	                	inConstantInformation.getThrLvlVal(),
	                	inConstantInformation.getFrsDstHnlVal(),
	                	inConstantInformation.getScnDstHnlVal(),
	                	inConstantInformation.getThrDstHnlVal(),
	                	
	                	inConstantInformation.getAgnVal(),
	                	inConstantInformation.getGppVal(),
	                	inConstantInformation.getDelVal(),
	                	inConstantInformation.getSblVal(),
	                	inConstantInformation.getPlyVal()},
	                new OCteDefRowMapper());

	 OPlyAtrCPT lvOPlyAtrCPT = new OPlyAtrCPT();
	 lvOPlyAtrCPT.setOPlyAtrPT(lvOPlyAtrP);
	 
        return lvOPlyAtrCPT;
    }

}
