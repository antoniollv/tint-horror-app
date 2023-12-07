package com.mapfre.tron.api.cmn.lst.bl;

import java.util.List;
import java.util.Map;
import com.mapfre.nwt.trn.trn.lst.bo.OTrnLstValS;

/**
 * The CmnLstQry business logic service interface.
 *
 * @author PARLAGA
 * @since 1..8
 * @version 4 nov. 2020 16:53:08
 *
 */
public interface IBlCmnLstQry {

    
    
    
    /**
     * 
     * @author PARLAGA
     * @purpose Servicio que permite recuperar la informacion de todas las listas de valores
     * @param lstTyp : Tipo de la Lista de Valores ("V","O")
     * @param lstIdn : Identificador de la lista de valores
     * @param insVal : Instalacion de la lista de valores
     * @param lstVrs : Version de la lista
     * @param lstVal : Lista de Parametros de entrada(Nombre/Valor) y Lista de datos de Salida (OTrnValS)
     * @param cchChc : Marca para cache. (true (recuperar cache), false (llamar BD))
     * @param cmpVal 
     * @return List<OTrnLstValS> Lista de datos Salida.
     * 
     **/
    List<OTrnLstValS> getLstVal(String lstIdn, String lstTyp, String insVal, String lstVrs, Map<String,Object> lstVal, boolean cchChc, Integer cmpVal);
    
}
