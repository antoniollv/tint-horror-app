package com.mapfre.tron.api.var.dat.dl;

/**
 * 
 * @author Javier Sangil
 * @version 27/11/2023
 *
 */
public interface IDlVarDatQryDAO {

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
    int postVariableData(Integer cmpVal, String usrVal, String lngVal, String fldNam, String fldValVal,
	    Long qtnVal);

}
