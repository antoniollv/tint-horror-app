package com.mapfre.tron.sfv.pgm.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.tron.sfv.bo.Clausula;

/**
 * Access to table A9990011.
 */
public interface IDlA9990011DAO {

	/**
	 * PK Query Clausulas.
	 * @param cmpVal compañía
	 * @param codClausula codigo Clausula
	 * @return clausulas
	 */
	List<Clausula> getClausula(BigDecimal cmpVal, String codClausula);

}
