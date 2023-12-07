package com.mapfre.tron.api.dfq.lod.bl;

import com.mapfre.tron.api.bo.TronWebAccess;

import lombok.Data;

/**
 * TwBlPidDfqLodImpl
 *
 * @author magarafr
 * @since 1.8
 * @version 21 ene. 2021 12:54:02
 *
 */
@Data
@TronWebAccess
public class TwBlPidDfqLodImpl implements IBlPidDfqLod {

    @Override
    public void savBtc(String lngVal, String usrVal, Integer cmpVal) throws Exception {
	throw new UnsupportedOperationException("Operation not supported in tronweb profile");
    }

}
