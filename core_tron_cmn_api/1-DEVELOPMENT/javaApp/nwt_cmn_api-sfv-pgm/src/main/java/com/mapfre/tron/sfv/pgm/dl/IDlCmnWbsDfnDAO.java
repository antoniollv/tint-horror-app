package com.mapfre.tron.sfv.pgm.dl;

import com.mapfre.tron.sfv.bo.OCmnWbsDfnS;

/**
 * Access to table df_cmn_nwt_wbs_dfn_trn.
 */
public interface IDlCmnWbsDfnDAO {

	/**
	 * PK Query.
	 * @param in pk
	 * @return data
	 */
	OCmnWbsDfnS get(OCmnWbsDfnS in);

}
