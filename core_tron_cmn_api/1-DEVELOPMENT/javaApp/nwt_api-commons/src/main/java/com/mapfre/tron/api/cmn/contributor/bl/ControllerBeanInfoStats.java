package com.mapfre.tron.api.cmn.contributor.bl;

import java.util.ArrayList;
import java.util.List;

public class ControllerBeanInfoStats {

    private List<ControllerBeanInfo> controllerBeanInfos= new ArrayList<>();


    public List<ControllerBeanInfo> getControllerBeanInfos() {
	return controllerBeanInfos;
    }

    public void addControllerBeanInfo(ControllerBeanInfo controllerBeanInfo) {
	controllerBeanInfos.add(controllerBeanInfo);
    }

}
