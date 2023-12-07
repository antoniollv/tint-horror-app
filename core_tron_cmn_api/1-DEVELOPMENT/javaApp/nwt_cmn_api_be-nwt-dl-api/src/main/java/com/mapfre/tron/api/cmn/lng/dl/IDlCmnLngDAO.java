package com.mapfre.tron.api.cmn.lng.dl;
import java.math.BigDecimal;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;

public interface IDlCmnLngDAO {

    OCmnLngS get(BigDecimal cmpVal, String lngVal, String usrLngVal);

}
