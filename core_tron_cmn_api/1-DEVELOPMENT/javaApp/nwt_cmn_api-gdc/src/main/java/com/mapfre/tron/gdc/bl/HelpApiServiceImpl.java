package com.mapfre.tron.gdc.bl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.lst.bo.OTrnLstValS;
import com.mapfre.nwt.trn.trn.val.bo.OTrnValS;
import com.mapfre.tron.api.cmn.lst.bl.IBlCmnLstQry;
import com.mapfre.tron.gdc.sr.dto.DataIn;
import com.mapfre.tron.gdc.sr.dto.HelpValue;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HelpApiServiceImpl implements HelpApiService {

    @Autowired
    IBlCmnLstQry blCmnLstQry;

    @Override
    public List<HelpValue> help(DataIn body, String idHelp, Integer cmpVal) {

        String[] help = idHelp.split("@");
        String lstIdn = help[0];
        String lstTyp = help.length >= 3 ? help[2] : "V";
        String insVal = help.length >= 4 ? help[3] : "TRN";
        String lstVrs = help[1];

        log.info(body.toString());

        List<HelpValue> res = new ArrayList<>();

        Map<String, Object> valT = getValT(body.getUser().getLanguage().toUpperCase(), body.getUser().getId(),
                Objects.toString(body.getUser().getCompany()), body.getData());

        try {
            List<OTrnLstValS> lstVal = blCmnLstQry.getLstVal(lstIdn, lstTyp, insVal, lstVrs, valT, Boolean.FALSE,
                    cmpVal);
            performLstVal(lstTyp, res, lstVal);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return res;
    }

    protected void performLstVal(String lstTyp, List<HelpValue> res, List<OTrnLstValS> lstVal) {
        if (lstVal != null && !lstVal.isEmpty()) {
            if (lstTyp.equalsIgnoreCase("O")) {
                for (OTrnLstValS val : lstVal) {
                    List<OTrnValS> valor = val.getValT();
                    for (OTrnValS listavalor : valor) {
                        String code = listavalor.getPrpIdn();
                        String label = listavalor.getPrpVal();
                        HelpValue h = new HelpValue(code, label, null);
                        res.add(h);
                    }
                }
            } else {
                peformLstValElse(res, lstVal);
            }
        }
    }

    protected void peformLstValElse(List<HelpValue> res, List<OTrnLstValS> lstVal) {
        for (OTrnLstValS val : lstVal) {
            List<OTrnValS> valor = val.getValT();
            String code = valor.get(0).getPrpVal();
            String label = null;
            Map<String, String> custom = new HashMap<>();
            if (valor.size() > 1)
                label = valor.get(1).getPrpVal();
            if (valor.size() > 2)
                for (int i = 2; i < valor.size(); i++) {
                    custom.put(valor.get(i).getPrpIdn(), valor.get(i).getPrpVal());
                }
            HelpValue h = new HelpValue(code, label, custom);
            res.add(h);
        }
    }

    private Map<String, Object> getValT(String language, String user, String company, Map<String, String> data) {
        Map<String, Object> valT = new HashMap<>();
        valT.put("LNG_VAL", language);
        valT.put("USR_VAL", user);
        valT.put("COD_CIA", company);
        if (data != null) {
            for (Map.Entry<String, String> r : data.entrySet()) {
                if (!valT.containsKey(r.getKey()) || (valT.containsKey(r.getKey()) && r.getValue() != null))
                    valT.put(r.getKey(), r.getValue());
            }
        }
        return valT;

    }
}
