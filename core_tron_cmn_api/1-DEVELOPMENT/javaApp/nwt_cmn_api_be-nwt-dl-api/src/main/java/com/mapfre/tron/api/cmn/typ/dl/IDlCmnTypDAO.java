package com.mapfre.tron.api.cmn.typ.dl;

import java.math.BigDecimal;
import java.util.List;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;

public interface IDlCmnTypDAO {
    
    List<OCmnTypS> get(BigDecimal cmpVal, String fldVal, String itcVal, String usrLngVal);

}
