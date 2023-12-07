package com.mapfre.tron.api.cmn.contributor.bl;

import lombok.Data;

@Data
public class ServiceInfo {
    private String operationId;
    private String url;

    // Constructor (Lombok genera automáticamente un constructor con todos los campos)
    public ServiceInfo(String operationId, String url) {
        this.operationId = operationId;
        this.url = url;
    }
}
