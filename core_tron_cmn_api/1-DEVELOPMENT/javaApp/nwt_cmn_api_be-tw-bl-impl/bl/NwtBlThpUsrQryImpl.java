package com.mapfre.tron.api.thp.usr.bl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapfre.tron.api.bo.NewTronAccess;
import com.mapfre.tron.api.bo.UserConnected;
import com.mapfre.tron.api.thp.usr.dl.DlThpUsr;

import lombok.Data;

@Data
@NewTronAccess
public class NwtBlThpUsrQryImpl implements IBlThpUsrQry {

    DlThpUsr dlThpUsr;
    
    @Autowired
    public NwtBlThpUsrQryImpl(DlThpUsr dlThpUsr) {
	this.dlThpUsr = dlThpUsr;
    }

    @Override
    public UserConnected getUserConnected(String usrVal) {
	return this.dlThpUsr.getUserInfo(usrVal);
    }
    
}
