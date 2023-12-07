package com.mapfre.tron.api.prt.lob.bl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;

import lombok.Data;

@Data
@Repository
public class SrPrtLobQryDAOImpl implements ISrPrtLobQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    /**
     * Query line of business by deals.
     *
     * @author Cristian Saball
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param delVal -> Deal value
     * @param vldDat -> Validation Date
     *
     * @return -> The list of line of business by deals
     */
    @Override
    public List<OPrtLobS> getLineOfBusinessByDeal(String lngVal, Integer cmpVal, String usrVal, String delVal,
	    Long vldDat) {       
	
	Date pmVldDat = (vldDat != null) ? new Date(vldDat) : null;
	
	String query = new StringBuilder()
		.append(" SELECT b.ROWID, b.* ")
		.append(" FROM g2990027 a")
		.append(" INNER JOIN a1001800 b ON b.cod_cia=a.cod_cia AND b.cod_ramo=a.cod_ramo")
		.append(" WHERE a.cod_cia = ?")
		.append(" AND a.num_contrato = ?")
		.append(" AND a.fec_validez = (SELECT MAX(b.fec_validez)FROM g2990027 b")
		.append(" WHERE a.cod_cia = b.cod_cia")
		.append(" AND a.num_contrato = b.num_Contrato")
		.append(" AND a.cod_ramo = b.cod_ramo")
		.append(" AND b.fec_validez <= NVL(?,SYSDATE)")
		.append(" AND mca_inh='N')")
		.toString();

	return jdbcTemplate.query(query, new Object[] { cmpVal, delVal, pmVldDat }, new OPrtLobSRowMapper());
	
    }

}
