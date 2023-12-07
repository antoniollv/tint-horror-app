/**
 * 
 */
package com.mapfre.tron.api.cmn.typ.bl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypCPT;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypP;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;

/**
 * The CmnTypQry business logic service interface.
 *
 * @author pvraul1
 * @since 1.8
 * @version 16 sept. 2019 13:05:08
 *
 */
public interface IBLCmnTypQry {

    /**
     * prc : CMN-2199 - OFRECER CONSULTAR tipo conjunto.
     *
     * @author pvraul1
     * @purpose Servicio de integracion que consulta la definición de tipo conjunto.
     *
     * @param fldNam       -> Campo
     * @param lobVal       -> Ramo
     * @param lngVal       -> Idioma
     * @param getNamTypVal -> Mostrar nombre
     *@param cmpVal 	   -> Company Code
     * @return             -> Tipo Conjunto
     */
    OCmnTypCPT prc(String fldNam, BigDecimal lobVal, String lngVal, String getNamTypVal, Integer cmpVal);

    /**
     * fld : CMN-2579 - OFRECER CONSULTAR tipo.
     *
     * @author pvraul1
     * @purpose Servicio de integracion que consulta la definición de tipo.
     *
     * @param fldNam       -> Campo
     * @param lobVal       -> Ramo
     * @param lngVal       -> Idioma
     * @param getNamTypVal -> Mostrar nombre
     * @param valVal       -> Valor
     * @param cmpVal 
     *
     * @return             -> Tipo Conjunto
     */
    OCmnTypP fld(String fldNam, BigDecimal lobVal, String lngVal, String getNamTypVal, String valVal, Integer cmpVal);
    
    
    /**
     * Query collector types
     * 
     * @author Cristian Saball
     * 
     * @param cmpVal 	  -> Company Code
     * @param lngVal       -> Idioma
     * @return OCmnTypCPT -> Collector Types
     */
    OCmnTypCPT Collect(String lngVal, Integer cmpVal);
    
    /**
     * Query collector type
     * 
     * @author Javier Sangil
     * 
     * @param cmpVal 	   -> Company Code
     * @param lngVal       -> Idioma
     * @param valVal       -> value
     * @return OCmnTypS -> Collector Type
     */
    OCmnTypS getCollectorType(String usrVal, String lngVal, BigDecimal cmpVal, String valVal);

    /**
     * Query Types
     * 
     * @author Javier Sangil
     * 
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param fldNam -> Field code
     * @param itcVal -> Intermediary Action Type
     * @param lobVal -> Line of Business
     * @param cmpVal -> Company code
     * 
     * @return OCmnTypP -> Type
     */
    
    OCmnTypS getType(String usrVal, String lngVal, String fldNam, String itcVal, BigDecimal lobVal, Integer cmpVal);
}
