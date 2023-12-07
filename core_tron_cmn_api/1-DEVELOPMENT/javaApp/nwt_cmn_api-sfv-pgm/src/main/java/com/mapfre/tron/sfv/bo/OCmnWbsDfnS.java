package com.mapfre.tron.sfv.bo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * df_cmn_nwt_wbs_dfn_trn.
 */
@Getter @Setter
public class OCmnWbsDfnS {
	private BigDecimal cmpVal; // COMPAÃ‘IA
	private String wsCodVal; // CODIGO WEBSERVICE
	private String wsSbdVal; // SUBCODIGO WEBSERVICE EJECUTADO
	private String mthTypVal; // METODO WEBSERVICE(POST,GET)
	private Long tmtVal; // MAXIMO TIEMPO PERMITIDO PARA RESPUESTA
	private String wbsUsrVal; // USUARIO WEBSERVICE
	private String wbsPswTxtVal; // PASSWORD WEBSERVICE
	private String urlWbsTxtVal; // DIRECCION URL WEBSERVICE
}
