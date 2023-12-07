package com.mapfre.tron.sfv.rule.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The nav rule step model class.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 24 may 2023 - 16:47:04
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavRuleStep {

    private String op;
    private List<String> values;

    public void addValue(String value) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
    }

}
