package com.mapfre.tron.api.cmn.contributor.bl;

import java.util.List;

import lombok.Data;

@Data
public class ControllerBeanInfo {

    private String controllerName;
    private String methodName;
    List<String> usedBeans;

}
