package com.mapfre.tron.api.cmn.contributor.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Endpoint(id="troninfo")
public class TronApiInfoContributor {
    
    @Autowired
    TronBeansInfo tronApiBeanInfo;

    @ReadOperation
    public TronInfoBeansStats invoke() {
	log.debug("Running troninfo endpoint");
	return tronApiBeanInfo.invoke();
    }
    
}