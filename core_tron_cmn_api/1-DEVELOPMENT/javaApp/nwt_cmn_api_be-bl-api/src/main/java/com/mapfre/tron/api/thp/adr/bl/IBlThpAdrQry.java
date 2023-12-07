package com.mapfre.tron.api.thp.adr.bl;

import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;

public interface IBlThpAdrQry {

	OThpAdrS get(Integer cmpVal, String lngVal, String usrVal, Integer frsLvlVal);

}
