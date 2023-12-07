package com.mapfre.tron.api.dsr.hdc.dl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;

import lombok.Data;

/**
 * @author Cristian Saball
 * @version 19/08/2021
 *
 */
@Data
@Repository
public class SrDsrHdcQryDAOImpl implements ISrDsrHdcQryDAO {

	
	/** The spring jdbc template. */
	@Qualifier("jdbcTemplate")
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
    /**
     * Second Level Distribution Channel by company and first level
     *
     * @author Cristian S.
     * 
     * 
     * @param cmpVal       -> company code
     * @param frsDstHnlVal -> first Channel Distribution
     * @param usrVal       -> user value
     * @param lngVal       -> language value
     * 
     * @return List<ODsrSdcS>
     * 
     */
    @Override
    public List<ODsrHdcS> tblByCmpFrsScnLvl(Integer cmpVal, String frsDstHnlVal, String scnDstHnlVal, String usrVal, String lngVal) {
		final String query = new StringBuilder()
				 .append(" SELECT  a.cod_cia, a.cod_canal1 , c.des_canal1, a.cod_canal2, b.des_canal2, a.cod_canal3, a.des_canal3, a.abr_canal3, a.mca_inh ")
				 .append(" from A1000723 a, A1000722 b, A1000721 c ")
				 .append(" where a.cod_cia = ? ")
				 .append(" and a.cod_cia = c.cod_cia  ")
				 .append(" and a.cod_canal1 = c.cod_canal1  ")
				 .append(" and a.cod_cia = b.cod_cia  ")
				 .append(" and a.cod_canal2 = b.cod_canal2  ")
				 .append(" and a.cod_canal1 = ?  ")
				 .append(" and a.cod_canal2 = ?  ")
				 .append(" and a.mca_inh = 'N'  ")
				.toString();

		List<ODsrHdcS> oDsrHdcSList = jdbcTemplate.query(query,
            new Object[] {cmpVal, frsDstHnlVal, scnDstHnlVal },
            new ODsrHdcSRowMapper());
		
	return oDsrHdcSList;
	}

}
