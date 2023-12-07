package com.mapfre.tron.sfv.pgm.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.tron.sfv.bo.OFldNamValDocQueryS;

/**
 * Access to table P2300205.
 */
public interface IDlP2300205DAO {

	/**
	 * PK Query questionnaries.
	 * @param cmpVal compañía
	 * @param plyVal poliza
	 * @param enrSqn suplemento
	 * @param rskVal riesgo
	 * @param frlVal formulario
	 * @param fldNam campo
	 * @return situacion fromulario y fecha proceso
	 */
    List<OFldNamValDocQueryS> getDocNam(BigDecimal cmpVal, 
		   		   String     plyVal,
		   		   BigDecimal enrSqn, 
		   		   BigDecimal rskVal,
		   		   BigDecimal frlVal);
    
    /**
	 * COUNT Query questionnaries.
	 * @param cmpVal compañía
	 * @param plyVal poliza
	 * @param enrSqn suplemento
	 * @param rskVal riesgo
	 * @param frlVal formulario
	 * @param fldNam campo
	 * @return situacion fromulario y fecha proceso
	 */
   Integer getCountDocNam(BigDecimal cmpVal, 
		   		   String     plyVal,
		   		   BigDecimal enrSqn, 
		   		   BigDecimal rskVal,
		   		   BigDecimal frlVal);

}
