package com.mapfre.tron.api.crn.crn.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;

public interface IDlCrnCrnDAO {

    List<OCrnCrnS> get(BigDecimal cmpVal, String crnVal, String usrLngVal);

}
