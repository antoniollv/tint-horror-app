package com.mapfre.tron.api.trv.vrb.dl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author AMINJOU
 *
 */
@Data
@Slf4j
@Repository
public class SrTrvVrbQryDAOImpl implements ISrTrvVrbQryDAO {

    /** The spring jdbc template. */
    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

	
    /**
     * Query variable  definition by company. 
     * It will return the variable definition filtering by company
     * 
     * @param usrVal 		-> User code
     * @param lngVal 		-> Language code
     * @param cmpVal 		-> Company code
     * @param vrbNamVal 	-> Variable Name
     * @param vldDat 		-> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
	@Override
	public List<OTrnVrbS> get(final String usrVal, final String lngVal, final Integer cmpVal, final String vrbNamVal, final Long vldDat) {
        	
		Date date = null;
		if(vldDat!=null){
			date = new Date(vldDat);
		}


		final String query = new StringBuilder()
				.append(" select  ")
				.append("  a.vrb_val,")
				.append("  a.vrb_dsp_val,")
				.append("  a.adt_val,")
				.append("  a.dfl_val_pem")
				.append(" from df_trn_nwt_xx_vrb a ")
				.append(" inner join df_trn_nwt_xx_vrb_cmp b on a.idn_key=b.idn_key")
				.append(" where b.cmp_val= ? ")
				.append("  and a.lng_val= ? ")
				.append(" and a.dsb_row='N' ")
				.append(" and b.vrb_nam_val = ? ")
				.append("  and a.vld_dat in (")
				.append(" select MAX(vld_dat)  ")
				.append("  FROM df_trn_nwt_xx_vrb c ")
				.append("  WHERE ")
				.append(" c.idn_key=a.idn_key  ")
				.append(" and c.lng_val=a.lng_val  ")
				.append(" and c.vrb_val=a.vrb_val  ")
				.append(" and vld_dat<=NVL(? ,SYSDATE)) ")
				.append(" order by a.sqn_val ")
                .toString();

		return jdbcTemplate.query(query, new Object[] { cmpVal, lngVal, vrbNamVal, date }, new OTrnVrbSRowMapper());

	}


	
	
	/**
	 * Query variable  definition by line of business. 
	 * It will return the variable definition filtering by company and line of business
	 * 
	 *  
     * @param usrVal 		-> User code
     * @param lngVal 		-> Language code
     * @param cmpVal 		-> Company code
     * @param vrbNamVal 	-> Variable Name
     * @param vldDat 		-> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
	@Override
	public List<OTrnVrbS> getVrb(final String usrVal, final String lngVal, final Integer cmpVal, final String vrbNamVal, final Long vldDat, final Integer lobVal) {

		Date date = null;
		if(vldDat!=null){
			date = new Date(vldDat);
		}

		final String query = new StringBuilder()
				.append(" select")
				.append("  a.vrb_val,")
				.append("  a.vrb_dsp_val,")
				.append("  a.adt_val,")
				.append("  a.dfl_val_pem")
				.append("  from df_trn_nwt_xx_vrb a")
				.append(" inner join df_trn_nwt_xx_vrb_lob b on a.idn_key=b.idn_key")
				.append(" where b.cmp_val= ? ")
				.append(" and a.lng_val= ?")
				.append("  and a.dsb_row='N'and b.vrb_nam_val = ?")
				.append(" and b.lob_val= ?")
				.append("  and a.vld_dat in (")
				.append(" select MAX(vld_dat)")
				.append(" FROM df_trn_nwt_xx_vrb c")
				.append(" WHERE  ")
				.append(" c.idn_key=a.idn_key")
				.append(" and c.lng_val=a.lng_val")
				.append(" and c.vrb_val=a.vrb_val")
				.append(" and c.vld_dat<=NVL(?,SYSDATE)")
				.append(" )")                
				.toString();

		return jdbcTemplate.query(query, new Object[] { cmpVal, lngVal, vrbNamVal, lobVal, date }, new OTrnVrbSRowMapper());

	}

	
	/**
	 * Variable Definition
	 * 
	 *  
     * @param usrVal 		-> User code
     * @param lngVal 		-> Language code
     * @param cmpVal 		-> Company code
     * @param thpDcmTypVal 	-> Document type
     * @param thpDcmVal		-> Document
     * @param thpAcvVal		-> Activity
     * @param vrbNamVal		-> Variable Name
     * @param vldDat		-> Valid Date
     *  
     * @return List<OTrnVrbS>
     * 
     */
	@Override
	public List<OTrnVrbS> getVrb(final String usrVal, final String lngVal, final Integer cmpVal, final String thpDcmTypVal, final String thpDcmVal,
		final Integer thpAcvVal, final String vrbNamVal, final Long vldDat) {
		Date date = null;	

		if(vldDat == null) {
			final String query = new StringBuilder()
					.append(" select ")
					.append(" a.vrb_val,")
					.append(" a.vrb_dsp_val,")
					.append(" a.adt_val,")
					.append(" a.dfl_val_pem")
					.append(" from df_trn_nwt_xx_vrb a")
					.append(" inner join df_trn_nwt_xx_vrb_thp b on a.idn_key=b.idn_key")
					.append(" where b.cmp_val= ?")
					.append(" and a.lng_val= ? ")
					.append(" and a.dsb_row='N'and b.vrb_nam_val = ?")
					.append(" and b.thp_dcm_typ_val= ?")
					.append(" and b.thp_dcm_val= ?")
					.append(" and b.thp_acv_val= ?")
					.append(" and a.vld_dat in (")
					.append(" select MAX(vld_dat) ")
					.append(" FROM df_trn_nwt_xx_vrb c ")
					.append(" WHERE ")
					.append(" c.idn_key=a.idn_key ")
					.append(" and c.lng_val=a.lng_val ")
					.append(" and c.vrb_val=a.vrb_val ")
					.append(" and vld_dat<=NVL(null,SYSDATE))")
					.append(" order by a.sqn_val")
					.toString();
			return jdbcTemplate.query(query, new Object[] { cmpVal, lngVal, vrbNamVal, thpDcmTypVal, thpDcmVal, thpAcvVal }, new OTrnVrbSRowMapper());

		}else {
			date = new Date(vldDat);
			final String query = new StringBuilder()
					.append(" select ")
					.append(" a.vrb_val,")
					.append(" a.vrb_dsp_val,")
					.append(" a.adt_val,")
					.append(" a.dfl_val_pem")
					.append(" from df_trn_nwt_xx_vrb a")
					.append(" inner join df_trn_nwt_xx_vrb_thp b on a.idn_key=b.idn_key")
					.append(" where b.cmp_val= ?")
					.append(" and a.lng_val= ? ")
					.append(" and a.dsb_row='N'and b.vrb_nam_val = ?")
					.append(" and b.thp_dcm_typ_val= ?")
					.append(" and b.thp_dcm_val= ?")
					.append(" and b.thp_acv_val= ?")
					.append(" and a.vld_dat in (")
					.append(" select MAX(vld_dat) ")
					.append(" FROM df_trn_nwt_xx_vrb c ")
					.append(" WHERE ")
					.append(" c.idn_key=a.idn_key ")
					.append(" and c.lng_val=a.lng_val ")
					.append(" and c.vrb_val=a.vrb_val ")
					.append(" and vld_dat<=NVL(?,SYSDATE))")
					.append(" order by a.sqn_val")
					.toString();
			return jdbcTemplate.query(query, new Object[] { cmpVal, lngVal, vrbNamVal, thpDcmTypVal, thpDcmVal, thpAcvVal, date }, new OTrnVrbSRowMapper());
		}	}

	/**
	     * Update variable  definition by thrid party. It will update the variable definition  by third party
	     * 
	     * @param usrVal 		-> User code
	     * @param lngVal 		-> Language code
	     * @param cmpVal 		-> Company code
	     * @param thpDcmTypVal 	-> Document type
	     * @param thpDcmVal 	-> document
	     * @param thpAcvVal 	-> Activity
	     * @param vrbNamVal 	-> Variable Name
	     * @param inVariableDefinition 	-> Input data to update the variable definition
	     * 
	     * @return void
	     * 
	     */
	@Override
	public void upd(String usrVal, String lngVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal, 
		Integer thpAcvVal, String vrbNamVal, OTrnVrbS inVariableDefinition) {
	    	
	    final String update = new StringBuilder()
        	.append("UPDATE ")
        	.append("(SELECT a.* ")
        	.append("FROM df_trn_nwt_xx_vrb a ")
        	.append("INNER JOIN df_trn_nwt_xx_vrb_thp b ")
        	.append("ON  ")
        	.append(" a.idn_key         =b.idn_key ")
        	.append("WHERE b.cmp_val       = ? ")
        	.append("AND b.thp_acv_val     = ? ")
        	.append("AND b.thp_dcm_typ_val = ? ")
        	.append("AND b.thp_dcm_val     = ? ")
        	.append("AND b.vrb_nam_val     = ? ")
        	.append("AND a.vrb_val         = ? ")
        	.append(") t ")
        	.append("SET ")
        	.append("t.vrb_dsp_val= NVL(?, t.vrb_dsp_val), ")
        	.append("t.adt_val    = NVL(?, t.adt_val), ")
        	.append("t.dfl_val_pem= NVL(?, t.dfl_val_pem) ")
        	.toString();

	    	int rowUpdated = jdbcTemplate.update(update, new Object[] { cmpVal, 
	    		thpAcvVal, thpDcmTypVal,  thpDcmVal, vrbNamVal, 
	    		inVariableDefinition.getVrbVal(), 
	    		inVariableDefinition.getVrbDspVal(), inVariableDefinition.getAdtVal(), 
	    		inVariableDefinition.getDflValPem()});
	    	
	    	if(rowUpdated == 0) {
	    	    String idnKey = String.valueOf(cmpVal).concat("-")
	    		    	.concat(String.valueOf(thpAcvVal)).concat("-")
	    		    	.concat(thpDcmTypVal).concat("-")
	    		    	.concat(String.valueOf(thpDcmVal)).concat("-")
	    		    	.concat(vrbNamVal);
	    		    
	    	    final String insert1 = new StringBuilder()
	        	.append("INSERT INTO df_trn_nwt_xx_vrb_thp( ")
	        	.append("cmp_Val, ")
	        	.append("thp_acv_val, ")
	        	.append("thp_dcm_Typ_val, ")
	        	.append("thp_Dcm_val, ")
	        	.append("vrb_nam_val, ")
	        	.append("idn_key, ")
	        	.append("usr_val, ")
	        	.append("mdf_Dat ")
	        	.append(") ")
	        	.append("VALUES( ")
	        	.append("?, ?, ?, ?, ?, ?, ?, ")
	        	.append("SYSDATE ")
	        	.append(") ")
	        	.toString();
	    	
	    	    try {
	    		jdbcTemplate.update(insert1, new Object[] { cmpVal, 
	    			thpAcvVal, thpDcmTypVal, thpDcmVal, 
	    			vrbNamVal, idnKey, usrVal});
	    	    } catch(DataAccessException dae) {
	    		log.error("PK error inserting in df_trn_nwt_xx_vrb_thp...");
	    	    }
	    	    
	    	    final String insert2 = new StringBuilder()
	        	.append("INSERT INTO df_trn_nwt_xx_vrb( ")
	        	
	        	.append("idn_key, ")
	        	.append("lng_val, ")
	        	.append("sqn_Val, ")
	        	.append("vrb_Val, ")
	        	.append("vrb_dsp_Val, ")
	        	.append("adt_val, ")
	        	.append("dfl_val_pem, ")
	        	.append("dsb_row, ")
	        	.append("vld_dat, ")
	        	.append("usr_val, ")
	        	.append("mdf_Dat ")
	        	.append(") ")
	        	.append("VALUES( ")
	        	.append("?, ?, 1, ?, ?, ?, ?, ")
	        	.append("'N', SYSDATE, ")
	        	.append("?, SYSDATE ")
	        	.append(") ")
	        	.toString();
	    	
	    		jdbcTemplate.update(insert2, new Object[] { idnKey, lngVal, 
	    			inVariableDefinition.getVrbVal(),  
	    			inVariableDefinition.getVrbDspVal(), inVariableDefinition.getAdtVal(),
	    			inVariableDefinition.getDflValPem(), usrVal});
	    	}
	}




	@Override
	public OTrnVrbS getById(String usrVal, String lngVal, Integer cmpVal, String vrbNamVal, Long vldDat,
		String vrbVal) {

    	
	Date date = null;
	if (vldDat != null) {
	    date = new Date(vldDat);
	}


	final String query = new StringBuilder()			
		.append("select ")
		.append("a.vrb_val, ")
		.append("a.vrb_dsp_val, ")
		.append("a.adt_val, ")
		.append("a.dfl_val_pem ")
		.append("from df_trn_nwt_xx_vrb a ")
		.append("inner join df_trn_nwt_xx_vrb_cmp b on a.idn_key=b.idn_key ")
		.append("where b.cmp_val= ? ")// cmpValpara probar 1 
		.append("and a.lng_val= ? ")//lngVal para probar 'ES' 
		.append("and a.vrb_val = ? ") //vrbVal- para probar, 1
		.append("and a.dsb_row='N' ")
		.append("and b.vrb_nam_val = ? ")//vrbNamVal para probar, poner 'AS_HELPMETHODS' 
		.append("and a.vld_dat in (select MAX(vld_dat) FROM df_trn_nwt_xx_vrb c WHERE ")
		.append(" c.idn_key=a.idn_key and c.lng_val=a.lng_val and c.vrb_val=a.vrb_val and c.vld_dat<=NVL(?,SYSDATE)) ") //-vldDatpara pruebas, poner nulo
		.toString();

		List<OTrnVrbS> oTrnVrbS = jdbcTemplate.query(query, new Object[] { cmpVal, lngVal, vrbVal, vrbNamVal, date }, new OTrnVrbSRowMapper());
		if(oTrnVrbS.isEmpty())
		    oTrnVrbS.add(null);
		return oTrnVrbS.get(0);
	
	}

}
