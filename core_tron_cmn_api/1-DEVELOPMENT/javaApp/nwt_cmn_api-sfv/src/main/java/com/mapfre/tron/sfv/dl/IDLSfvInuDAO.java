package com.mapfre.tron.sfv.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmn.sfv.bo.OCmnSfvS;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.rule.model.NavRule;

/**
 * The SfvInu interface repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 8 May 2023 - 12:15:20
 *
 */
public interface IDLSfvInuDAO {

    /**
     * Consultar el flujo que aplica a los datos de entrada IDN_KEY.
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User value
     * @param lngVal -> Language code
     * @param svfIn  -> Input structure data (agent, channel ...)
     * @return       -> Output structure data
     */
    List<OCmnSfvS> inStreamIdnKeyDataQry(BigDecimal cmpVal, String usrVal, String lngVal, SfvIn sfvIn);

    /**
     * Leer la tabla de parametrización de programas java.
     *
     * @param lvOCmnSfvS -> In structure data
     * @return           -> Out structure data list
     */
    List<OCmnSfvS> readParamProgram(OCmnSfvS lvOCmnSfvS);

    /**
     * Recupera el mensaje de error personalizado.
     *
     * @param oCmnSfvS -> In structure data
     * @param cmpVal   -> Company code
     * @param usrVal   -> User value
     * @param lngVal   -> Language code
     * @return         -> Out structure data
     */
    OCmnSfvS readErrMsgTxt(OCmnSfvS lvOCmnSfvS, BigDecimal cmpVal, String usrVal, String lngVal);

    /**
     * Recuperar las reglas de navegacion.
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User value
     * @param lngVal -> Language code
     * @param idnKey -> Identification key
     * @param steIdn -> Step identification
     * @return       -> Navigation rules list
     */
    List<NavRule> readNavRules(BigDecimal cmpVal, String usrVal, String lngVal, String idnKey, String steIdn);

}
