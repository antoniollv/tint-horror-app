package com.mapfre.tron.api.cmn.pan.dl;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.cmn.pan.bo.OCmnPanS;
import com.mapfre.nwt.trn.cmn.uar.bo.OCmnUarS;


public interface IDlCmnPanDAO {

	OCmnPanS getRol(BigDecimal cmpVal, String pirVal, Date vldDat, String usrLngVal);

	OCmnPanS getConceptoLogico(BigDecimal cmpVal, String lgpVal, String usrLngVal);

	OCmnPanS getPropiedad(BigDecimal cmpVal, String prpVal, String usrLngVal);

	OCmnUarS getAplicacion(BigDecimal cmpVal, String appVal, String usrLngVal);

	OCmnUarS getUsuarioAplicacion(BigDecimal cmpVal, String appUsrVal, String usrLngVal);

	OCmnUarS getCodigoRol(BigDecimal cmpVal, String rolVal, Date vldDat, String usrLngVal);

	OCmnUarS getTipoRol(BigDecimal cmpVal, String rolTypVal, String usrLngVal);
}
