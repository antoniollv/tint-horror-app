
package com.mapfre.tron.api.prt.del.dl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;

import lombok.Data;

/**
 * The SrNmrGppQryDAOImpl dao repository.
 *
 * @author AMINJOU
 *
 */
@Data
@Repository
public class SrPrtDelQryDAOImpl implements ISrPrtDelQryDAO {
    
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
	public List<OPrtDelS> get(final String usrVal, final String lngVal, final Integer cmpVal, final Long vldDat, final Integer lobVal) {
		
		Date date = null;
		if(vldDat != null) {
			date = new Date(vldDat);
		}
		
        String query = new StringBuilder()
        		.append(" SELECT ")
        		.append(" distinct")
        		.append(" a.cod_cia,")
        		.append(" a.num_contrato,")
        		.append(" b.nom_contrato,")
        		.append(" a.fec_validez")
        		.append(" FROM g2990027 a")
        		.append(" INNER JOIN g2990001 b ON b.cod_cia=a.cod_cia AND b.num_contrato=a.num_contrato")
        		.append(" WHERE a.cod_cia = ?")
        		.append(" AND ( a.cod_ramo = ?")
        		.append(" OR  a.cod_ramo = 999)AND a.mca_inh='N'AND a.fec_validez   = ( ")
        		.append(" SELECT MAX(b.fec_validez)FROM g2990027 b")
        		.append(" WHERE b.cod_cia = a.cod_cia")
        		.append(" AND b.cod_ramo = a.cod_ramo")
        		.append(" AND b.num_contrato = a.num_contrato")
        		.append(" AND b.fec_validez <= NVL(?,SYSDATE))")
        		.toString();
        
        List<OPrtDelS> oPrtDelS = jdbcTemplate.query(query, new Object[] {cmpVal, lobVal, date}, new OPrtDelSRowMapper());
		return oPrtDelS;
	}


    

}
