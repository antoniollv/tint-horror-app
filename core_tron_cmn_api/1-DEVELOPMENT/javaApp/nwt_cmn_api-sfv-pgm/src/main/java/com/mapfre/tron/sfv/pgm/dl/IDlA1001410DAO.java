package com.mapfre.tron.sfv.pgm.dl;

import java.math.BigDecimal;

/**
 * Access to table df_cmn_nwt_wbs_dfn_trn.
 */
public interface IDlA1001410DAO {

	/**
	 * PK Query NumCueotas.
	 * @param cmpVal compañía
	 * @param codPlanPago plan dde pagos
	 * @return numero de cuotas
	 */
	Integer getNumCuotas(BigDecimal cmpVal, String codPlanPago);

}
