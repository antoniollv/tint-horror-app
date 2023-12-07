package com.mapfre.tron.api.trn.evn.dl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.trn.evn.bo.OTrnEvnS;

import lombok.Data;

/**
 * The SrlTrnEvnQryDAOImpl dao repository.
 *
 * @author Cristian Saball
 *
 */
@Data
@Repository
public class SrlTrnEvnQryDAOImpl implements ISrlTrnEvnQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Query message list for event not processed.
     *
     * @author Cristian Saball
     *
     * @param cmpVal 	-> Company code
     * @param usrVal 	-> User code
     * @param lngVal 	-> Language code
     * @param qryDat 	-> Query Date
     * @param evnIdnVal -> Event Identifier
     * @return       	-> The message for event
     */
    @Override
    public List<OTrnEvnS> getQueryEventNotProcessed(Integer cmpVal, String lngVal, String usrVal, Long qryDat,
	    Integer evnIdnVal) {
	
	Date pmQryDat = (qryDat !=null) ? new Date(qryDat) : null;
	
	String query = new StringBuilder()
		.append(" SELECT ")
		.append(" cmp_val,")
		.append(" evn_idn_val,")
		.append(" scn_idn_val,")
		.append(" rcr_pss_chc,")
		.append(" evn_msg_val,")
		.append(" usr_val,")
		.append(" mdf_dat")
		.append(" FROM ml_trn_nwt_xx_evn_msg ")
		.append("  WHERE cmp_val = ? ")
		.append(" AND evn_idn_val = NVL(?, evn_idn_val) ")
		.append(" AND TRUNC(mdf_dat) BETWEEN ? and TRUNC(SYSDATE)  ")
		.append(" AND rcr_pss_chc = 'N' ")
		.append(" order by evn_idn_val,scn_idn_val ")
		.toString();

	 List<OTrnEvnS> lvOTrnEvnSList = jdbcTemplate.query(query, new Object[] {cmpVal, evnIdnVal, pmQryDat}, new OTrnEvnSRowMapper());
	
	 return lvOTrnEvnSList;
}

}
