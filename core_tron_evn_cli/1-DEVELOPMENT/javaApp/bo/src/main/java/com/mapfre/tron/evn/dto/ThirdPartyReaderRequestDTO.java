package com.mapfre.tron.evn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ThirdPartyReaderRequestDTO {

    private Integer cmpVal;
    private String thpDcmTypVal;
    private String thpDcmVal;
}
