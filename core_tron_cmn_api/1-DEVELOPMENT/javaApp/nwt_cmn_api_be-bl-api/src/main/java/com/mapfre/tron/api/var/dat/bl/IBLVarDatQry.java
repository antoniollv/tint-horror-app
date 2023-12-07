/**
 * 
 */
package com.mapfre.tron.api.var.dat.bl;

/**
 * The IBLVarDatQry business logic service interface.
 *
 * @author Javier Sangil
 * @version 27/11/2023
 *
 */
public interface IBLVarDatQry {

	
    /**
     * Post variable data . It will be mandatory send the parameters defined in the
     * service and the service will response with the output object defined.
     * 
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param fldNam    -> Field code
     * @param fldValVal -> Field Value
     * @param qtnVal    -> Quotation value
     * @param cmpVal    -> company
     * 
     */
    void postVariableData(Integer cmpVal, String usrVal, String lngVal, String fldNam, String fldValVal,
	    Long qtnVal);

}
