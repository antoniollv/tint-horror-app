package com.mapfre.tron.api.dsr.sdc.dl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.dsr.sdc.bo.ODsrSdcS;

import lombok.Data;

/**
 * @author Cristian Saball
 * @version 19/08/2021
 *
 */
@Data
@Repository
public class SrDsrSdcQryDAO implements ISrDsrSdcQryDAO {

	
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
    public List<ODsrSdcS> tblByCmpFrsLvl(Integer cmpVal, String frsDstHnlVal, String usrVal, String lngVal) {
		final String query = new StringBuilder()
				 .append(" SELECT  a.cod_cia, a.cod_canal1, b.des_canal1, a.cod_canal2, a.des_canal2, a.abr_canal2, a.mca_inh ")
				 .append(" from A1000722 a, A1000721 b ")
				 .append(" where a.cod_cia = ? ")
				 .append(" and a.cod_cia = b.cod_cia  ")
				 .append(" and a.cod_canal1 = b.cod_canal1  ")
				 .append(" and a.cod_canal1 = ?  ")
				 .append(" and a.mca_inh = 'N'  ")
				.toString();

		List<ODsrSdcS> oDsrSdcSList = jdbcTemplate.query(query,
            new Object[] {cmpVal, frsDstHnlVal },
            new ODsrSdcSRowMapper());
		
	return oDsrSdcSList;
	}

}
