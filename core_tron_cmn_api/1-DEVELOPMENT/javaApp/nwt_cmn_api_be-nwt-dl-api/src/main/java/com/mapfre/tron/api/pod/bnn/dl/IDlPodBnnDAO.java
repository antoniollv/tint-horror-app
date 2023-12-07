package com.mapfre.tron.api.pod.bnn.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.pod.bnn.bo.OPodBnnS;

public interface IDlPodBnnDAO {
	
	//Consultar Entidad Bancaria
	List<OPodBnnS> getEntidadBancaria(BigDecimal cmpVal, String bneVal, String lngVal, String usrLngVal);
    
    //Consultar Marca Pago Autorizado
	List<OPodBnnS> getMarcaPagoAutorizado(BigDecimal cmpVal, String bneVal, String lngVal, String usrLngVal);
}
