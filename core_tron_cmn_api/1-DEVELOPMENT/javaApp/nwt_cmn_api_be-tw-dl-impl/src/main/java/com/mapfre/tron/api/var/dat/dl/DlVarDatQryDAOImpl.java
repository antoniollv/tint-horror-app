package com.mapfre.tron.api.var.dat.dl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.Data;


/**
 * 
 * @author Javier Sangil
 * @version 27/11/2023
 *
 */
@Data
@Repository
public class DlVarDatQryDAOImpl implements IDlVarDatQryDAO{

	
    	/** The spring jdbc template. */
    	@Qualifier("jdbcTemplate")
    	@Autowired
    	private JdbcTemplate jdbcTemplate;
	
	
        /**
         * Post variable data . It will be mandatory send the parameters defined in the
         * service and the service will response with the output object defined.
         * 
         * @param lngVal    -> Language code
         * @param usrVal    -> User code
         * @param fldNam    -> Field code
         * @param fldValVal -> Field Value
         * @param qtnVal    -> Quotation value
         * @param cmpVal    -> company
         * 
         */
	@Override
	public int postVariableData(Integer cmpVal, String usrVal, String lngVal, String fldNam, String fldValVal,
		    Long qtnVal) {
		final String query = new StringBuilder()
			.append(" UPDATE P2000020 SET ")
			.append(" val_campo = ? ")//fldValVal
			.append(" where cod_cia = ? ")//cmp_val
			.append(" and num_poliza = ? ")//qtnVal
			.append(" and cod_campo = ? ")//fldNam
			.toString();

	return jdbcTemplate.update(query, fldValVal, new BigDecimal(cmpVal), qtnVal.toString(), fldNam);
    }

}
