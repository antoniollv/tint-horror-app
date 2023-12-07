package com.mapfre.tron.api.cmn.contributor.bl;

import java.util.List;

import lombok.Data;


@Data
public class TronNamingBeansStats {

    private Integer customBeansCount;
    private Integer customBeansMalformedCount;
    private Float percentage;
    private List<TronNamingBeanInfo> malformedBeans;

}
