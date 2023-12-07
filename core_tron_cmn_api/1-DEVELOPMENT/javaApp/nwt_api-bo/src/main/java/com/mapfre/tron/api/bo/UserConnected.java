package com.mapfre.tron.api.bo;

import java.util.List;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.nwt.trn.thp.usr.bo.OThpUsrS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConnected {

    private OThpUsrS usrData;
    
    private List<OCmuCmpS> cmpData;
}
