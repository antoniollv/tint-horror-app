package com.mapfre.tron.sfv.pgm.dl;

import java.math.BigDecimal;

import com.mapfre.tron.sfv.bo.OFlrDfn;

/**
 * Access to table P2300205.
 */
public interface IDlA2300200DAO {

	/**
	 * PK Query questionnaries definition.
	 * @param cmpVal compañía
	 * @param frlVal formulario
	 * @return situacion fromulario y fecha proceso
	 */
	OFlrDfn getFrlDfn(BigDecimal cmpVal, 
		   		      BigDecimal frlVal);
    
   

}
