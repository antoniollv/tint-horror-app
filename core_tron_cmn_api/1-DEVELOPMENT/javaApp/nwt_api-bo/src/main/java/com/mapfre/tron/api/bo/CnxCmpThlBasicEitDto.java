package com.mapfre.tron.api.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.mapfre.nwt.trn.bo.ObjNwt;
import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlP;
import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CnxCmpThlBasicEitDto implements ObjNwt {

    private static final long serialVersionUID = 4820645901563270428L;
    
    private OTrnCnxS    oTrnCnxS ;
    private OCmuCmpS    oCmuCmpS; 
    private OCmuThlP    oCmuThlP;
}
