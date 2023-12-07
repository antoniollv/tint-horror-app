package com.mapfre.tron.evn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Environment {

    private String name;

    private String baseUrl;

    private String userName;

    private String password;


}
