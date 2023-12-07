package com.mapfre.tron.api.tpd.ssd.dl;

import java.math.BigDecimal;
import java.util.List;
import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;

public interface IDlTpdSsdDAO {
    OTpdSsdS get(BigDecimal cmpVal, String ssvVal, String lngVal, String usrLngVal);
    List<OTpdSsdS> get_ssdPT(BigDecimal cmpVal, String ssvVal, String usrLngVal);

}
