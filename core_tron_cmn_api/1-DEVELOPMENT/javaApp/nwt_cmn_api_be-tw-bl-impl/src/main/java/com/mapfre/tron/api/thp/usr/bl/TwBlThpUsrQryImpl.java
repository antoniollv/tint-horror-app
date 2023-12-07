package com.mapfre.tron.api.thp.usr.bl;

import com.mapfre.tron.api.bo.TronWebAccess;
import com.mapfre.tron.api.bo.UserConnected;
import lombok.Data;

@Data
@TronWebAccess
public class TwBlThpUsrQryImpl implements IBlThpUsrQry {

    @Override
    public UserConnected getUserConnected(final String usrVal) {
        throw new UnsupportedOperationException();
    }

}
