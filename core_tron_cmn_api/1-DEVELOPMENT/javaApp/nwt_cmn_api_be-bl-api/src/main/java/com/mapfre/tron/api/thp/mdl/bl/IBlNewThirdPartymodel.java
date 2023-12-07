package com.mapfre.tron.api.thp.mdl.bl;

import com.mapfre.tron.api.cmn.model.McaMdtThpNewTrueReponsenDefinition;

/**
 * The new thirdparty model business interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 5 Dec 2022 - 16:20:54
 *
 */
public interface IBlNewThirdPartymodel {

    /**
     * Query Company New Third Party Model.
     *
     * @param cmpVal -> Company code
     * @return       -> It will return S if the company is new third party model, otherwise N
     */
    McaMdtThpNewTrueReponsenDefinition getNewThirdPartyModel(Integer cmpVal);

}
