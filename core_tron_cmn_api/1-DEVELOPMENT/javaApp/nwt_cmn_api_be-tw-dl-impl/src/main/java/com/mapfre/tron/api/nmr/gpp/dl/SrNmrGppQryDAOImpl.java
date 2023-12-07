
package com.mapfre.tron.api.nmr.gpp.dl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.nmr.gpp.bo.ONmrGppS;

import lombok.Data;

/**
 * The SrNmrGppQryDAOImpl dao repository.
 *
 * @author AMINJOU
 *
 */
@Data
@Repository
public class SrNmrGppQryDAOImpl implements ISrNmrGppQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 
     * 
     * @param lngVal   -> Language code
     * @param cmpVal   -> Company code
     * @param usrVal   -> User code
     * @param lobVal   -> Line of Business
     * @param vldDat   -> Valid Date
     * 
     * @return List<ONmrGppS>
     * 
     * 
     */
	@Override
	public List<ONmrGppS> get(final String usrVal, final String lngVal, final Integer cmpVal, final Long vldDat, final Integer lobVal) {
        
		Date date = null;
		
		if(vldDat!=null) {
			date = new Date(vldDat);
		}
		
		
		String query = new StringBuilder()
        		.append(" SELECT")
        		.append(" distinct g.cod_cia, g.num_poliza, g.nom_poliza, g.nom_cort_poliza, g.num_secu_grupo")
        		.append(" FROM g2990017 g")
        		.append(" INNER JOIN A2000010 b ON g.cod_cia = b.cod_cia ")
        		.append(" AND g.num_poliza = b.num_poliza")
        		.append(" INNER JOIN G2990027 c ON C.cod_cia = b.cod_cia ")
        		.append(" AND c.num_contrato=b.num_contrato")
        		.append(" WHERE g.cod_cia = ?")
        		.append(" AND (c.cod_ramo = '999' OR c.cod_ramo = ? ) ")
        		.append(" AND g.mca_inh='N' ")
        		.append(" AND c.fec_validez IN (SELECT MAX(d.fec_validez)")
        		.append(" FROM G2990027 d")
        		.append(" WHERE d.cod_cia=c.cod_cia")
        		.append(" AND c.num_contrato=d.num_contrato")
        		.append(" and c.cod_ramo=d.cod_ramo")
        		.append(" AND d.fec_validez <= NVL(?,SYSDATE))")
        		.toString();
        
        List<ONmrGppS> oGrsZnoS = jdbcTemplate.query(query, new Object[] {cmpVal, lobVal, date}, new ONmrGppSRowMapper());
		return oGrsZnoS;
	}


    

}
