package com.mapfre.tron.api.thp.mdl.bl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.mapfre.nwt.ins.c.CInsCmp;
import com.mapfre.nwt.ins.c.CInsConstant;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.model.McaMdtThpNewTrueReponsenDefinition;

/**
 * The new thirdparty model business implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 5 Dec 2022 - 16:33:25
 *
 */
@Service
public class BlNewThirdPartymodelImpl extends TwBlCmnBase implements IBlNewThirdPartymodel {

    /**
     * Query Company New Third Party Model.
     *
     * @param cmpVal -> Company code
     * @return       -> It will return S if the company is new third party model, otherwise N
     */
    @Override
    public McaMdtThpNewTrueReponsenDefinition getNewThirdPartyModel(final Integer cmpVal) {
        McaMdtThpNewTrueReponsenDefinition mcaMdtThpNew = new McaMdtThpNewTrueReponsenDefinition();
        mcaMdtThpNew.setMcaMdtThpNew("N");

        try {
            if ("S".equalsIgnoreCase(CInsCmp.get(CInsConstant.MCA_MDT_THP_NEW, new BigDecimal(cmpVal)))) {
                mcaMdtThpNew.setMcaMdtThpNew("S");
            }
        } catch (NoSuchFieldError e) {
            getErrorWithoutPrpIdn(lngDefault, "CMP", cmpValDefault);
        }

        return mcaMdtThpNew;
    }

}
