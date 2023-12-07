package com.mapfre.tron.api.cmn.contributor.bl;

import java.util.List;

import lombok.Data;

@Data
public class TronLocalServicesStats {
    // Number of local services
    Integer localServicesCount;
    // Number of core services
    Integer coreServicesCount;
    // Percentage (local/total)
    Float percentage;

    // Detail (list of services)
    private List<ServiceInfo> core;
    private List<ServiceInfo> local;

    
}