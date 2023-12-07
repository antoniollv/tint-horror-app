package com.mapfre.tron.api.cmn.atr.dl;

import java.util.List;

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
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;
import com.mapfre.tron.api.cmn.model.InVariableConceptsInformation;

import lombok.Data;


/**
 * @author AMINJOU
 * @version 24/02/2021
 *
 */
@Data
@Repository
public class SrCmnAtrQryDAOImpl implements ISrCmnAtrQryDAO{

	
    	/** The spring jdbc template. */
    	@Qualifier("jdbcTemplate")
    	@Autowired
    	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 
	 * @param usrVal -> user value
	 * @param lngVal -> language value
	 * @param inConstantInformation 
	 * @return List<OPlyAtrS>
	 * 
	 */
	@Override
	public List<OPlyAtrS> atr(final String usrVal, final String lngVal, final InVariableConceptsInformation inConstantInformation) {
		final String query = new StringBuilder()
				.append(" SELECT")
				.append(" t.row_nam,")
				.append(" t.row_val_val,")
				.append(" t.cnc_vrb_val")
				.append(" FROM df_cmn_nwt_xx_vrb_cnc t")
				.append(" where t.cmp_val = ? ")
				.append(" and t.lob_val = ? ")
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
				.append(" and t.vrb_nam = ?")
				.append(" and dsb_row='N'")
				.append(" and t.VLD_DAT")
				.append(" IN (SELECT MAX(a.VLD_DAT) ")
				.append(" FROM df_cmn_nwt_xx_vrb_cnc a ")
				.append(" where a.cmp_val=t.cmp_val ")
				.append(" and a.lob_val=t.lob_val")
				.append(" and a.mdt_val=t.mdt_val ")
				.append(" and a.cvr_val=t.cvr_val ")
				.append(" and a.frs_lvl_val=t.frs_lvl_val ")
				.append(" and a.scn_lvl_val=t.scn_lvl_val ")
				.append(" and a.crn_val=t.crn_val ")
				.append(" and a.agn_val=t.agn_val ")
				.append(" and a.del_val=t.del_val ")
				.append(" and a.sbl_val=t.sbl_val ")
				.append(" and a.gpp_val=t.gpp_val ")
				.append(" and a.ply_val=t.ply_val ")
				.append(" and a.vrb_nam=t.vrb_nam")
				.append(" and a.FRS_DST_HNL_VAL=t.FRS_DST_HNL_VAL")
				.append(" AND A.SCN_DST_HNL_VAL=T.SCN_DST_HNL_VAL")
				.append(" AND A.THR_DST_HNL_VAL=T.THR_DST_HNL_VAL")
				.append(" AND a.vrb_nam = t.vrb_nam )")
                .toString();

		List<OPlyAtrS> oPlyAtrSList = jdbcTemplate.query(query,
                new Object[] { inConstantInformation.getCmpVal(), 
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
                		inConstantInformation.getPlyVal(),
                		inConstantInformation.getVrbNam()},
                new OCmnAtrSRowMapper());
        return oPlyAtrSList;
	}

}
