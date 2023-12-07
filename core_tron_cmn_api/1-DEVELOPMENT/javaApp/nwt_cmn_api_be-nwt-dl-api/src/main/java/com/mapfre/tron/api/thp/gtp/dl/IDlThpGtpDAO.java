package com.mapfre.tron.api.thp.gtp.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.thp.gtp.bo.OThpGtpS;

public interface IDlThpGtpDAO {

    OThpGtpS get(BigDecimal cmpVal, String dcmTypVal, String usrLngVal);

}
