package com.mapfre.tron.api.cmn.ssg.dl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.cmn.ssg.bo.OCmnSsgS;

import lombok.Data;

@Data
@Repository
public class SrCmnSsgQryDAOImpl implements ISrCmnSsgQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Query Storage From Self-Service.
     *
     * @author Cristian Saball
     *
     * @param cmpVal 	-> Company code
     * @param usrVal 	-> User code
     * @param lngVal 	-> Language code
     * @param sfrSciVal -> Self-Service Section
     * @param sfrSbsVal -> Self-Service Subsection
     * @param sfrIdnVal -> Authentication Identifier
     * @param vrbDspVal -> Variable Description
     * @return       	-> The variables saved for the self service front end
     */
    @Override
    public List<OCmnSsgS> getStorageFromSelfService(Integer cmpVal, String usrVal, String lngVal, String sfrSciVal,
	    String sfrSbsVal, List<String> sfrIdnVal, String vrbDspVal) {
	
	String cadena = (sfrIdnVal!=null) ? extractSfrIdnVal(sfrIdnVal) : null;
	
        final String query = new StringBuilder()
                .append("SELECT ")
                .append("a.CMP_VAL, ")
                .append("a.SFR_IDN_VAL, ")
                .append("a.SFR_SCI_VAL, ")
                .append("a.SFR_SBS_VAL, ")
                .append("a.SFR_SQN_VAL, ")
                .append("a.VRB_DSP_VAL, ")
                .append("a.VRB_VAL, ")
                .append("a.VLD_DAT, ")
                .append("a.DSB_ROW, ")
                .append("a.USR_VAL, ")
                .append("a.MDF_DAT ")
                .append("FROM RL_CMN_NWT_XX_SSG a ")
                .append("WHERE a.CMP_VAL = ? ") // cmpVal
                .append("AND a.SFR_IDN_VAL IN (" +cadena+ ")") //sfrIdnVal
                .append("AND a.SFR_SCI_VAL = ? ") //sfrSciVal
                .append("AND a.SFR_SBS_VAL = ? ") //sfrSbSVal
                .append("AND a.VRB_DSP_VAL = NVL( ? ,a.VRB_DSP_VAL) ") //vrbDspVal
                .append("AND a.DSB_ROW = 'N' ")
                .append("AND a.VLD_DAT IN (SELECT MAX(b.VLD_DAT) FROM  RL_CMN_NWT_XX_SSG b ")
                .append("WHERE a.CMP_VAL = b.CMP_VAL ")
                .append("AND a.SFR_IDN_VAL= b.SFR_IDN_VAL ")
                .append("AND a.SFR_SCI_VAL = b.SFR_SCI_VAL ")
                .append("AND a.SFR_SBS_VAL = b.SFR_SBS_VAL ")
                .append("AND a.SFR_SQN_VAL = b.SFR_SQN_VAL) ")
                .append("ORDER BY a.SFR_IDN_VAL,a.SFR_SCI_VAL,a.SFR_SBS_VAL,a.VRB_DSP_VAL,a.SFR_SQN_VAL ")
                .toString();

        List<OCmnSsgS> lOCmnSsgSList = jdbcTemplate.query(query, new Object[] { cmpVal, sfrSciVal, sfrSbsVal, vrbDspVal },
                new OCmnSsgSRowMapper());
        
        return lOCmnSsgSList;

    }

    private String extractSfrIdnVal(List<String> sfrIdnVal) {
	StringBuilder cadena = new StringBuilder();
	
	if (sfrIdnVal != null && !sfrIdnVal.isEmpty()) {
	    for (int i = 0; i < sfrIdnVal.size(); i++) {
		cadena.append(sfrIdnVal.get(i));
		if (i < sfrIdnVal.size() - 1) {
		    cadena.append(",");
		}
	    }
	}
	return cadena.toString();
    }

}
