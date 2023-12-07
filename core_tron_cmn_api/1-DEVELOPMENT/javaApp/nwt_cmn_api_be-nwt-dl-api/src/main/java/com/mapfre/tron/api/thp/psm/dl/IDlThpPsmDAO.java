package com.mapfre.tron.api.thp.psm.dl;

import java.math.BigDecimal;
import java.util.Date;
import com.mapfre.nwt.trn.thp.psm.bo.OThpPsmS;

public interface IDlThpPsmDAO {

    OThpPsmS get(BigDecimal cmpVal, String psmVal, String lngVal, Date vldDat, String usrLngVal);

}
