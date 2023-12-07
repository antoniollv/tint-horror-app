package com.mapfre.tron.sfv.dl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.tron.sfv.rule.model.NavRule;
import com.mapfre.tron.sfv.rule.model.NavRuleStep;

import lombok.extern.slf4j.Slf4j;

/**
 * The nav rule row mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 24 may 2023 - 17:40:19
 *
 */
@Slf4j
public class NavRuleRowMapper implements RowMapper<NavRule> {

    private static final String ERROR_PARSING_NAV_RULE_JSON_S = "error parsing navRule json: %s";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NavRule mapRow(ResultSet rs, int rowNum) throws SQLException {
        NavRule lvNavRule = new NavRule();
        lvNavRule.setNxtSteIdn(rs.getString("nxt_ste_idn"));
        lvNavRule.setOrdRulVal(rs.getBigDecimal("ord_rul_val"));
        lvNavRule.setPmnNvgPrrSte(rs.getString("pmn_nvg_prr_ste"));
        lvNavRule.setPmnNvgWhtPrrSte(rs.getString("pmn_nvg_wht_prr_ste"));

        String jsonTxt = rs.getString("rul_val_txt");
        if (jsonTxt != null && jsonTxt.trim().length() > 0) {
            try {
                List<Map<String, NavRuleStep>> rulesVal = objectMapper.readValue(jsonTxt, new TypeReference<List<Map<String, NavRuleStep>>>() {});
                lvNavRule.setRulVal(rulesVal);
            } catch (JsonParseException e) {
                log.error(String.format(ERROR_PARSING_NAV_RULE_JSON_S, e.getMessage()));
            } catch (JsonMappingException e) {
                log.error(String.format(ERROR_PARSING_NAV_RULE_JSON_S, e.getMessage()));
            } catch (IOException e) {
                log.error(String.format(ERROR_PARSING_NAV_RULE_JSON_S, e.getMessage()));
            }
        }

        return lvNavRule;
    }

}
