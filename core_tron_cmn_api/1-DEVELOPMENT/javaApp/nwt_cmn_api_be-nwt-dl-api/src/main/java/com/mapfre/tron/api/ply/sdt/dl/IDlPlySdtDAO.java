package com.mapfre.tron.api.ply.sdt.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.ply.sdt.bo.OPlySdtS;

public interface IDlPlySdtDAO {

        List<OPlySdtS> getPoliza(BigDecimal cmpVal, String plyVal, String usrLngVal);
        
        List<OPlySdtS> getAplicacion(BigDecimal cmpVal, String plyVal, BigDecimal aplVal, String usrLngVal);
}
