package com.mapfre.tron.api.cmn;

import java.util.Comparator;

import com.mapfre.nwt.trn.ply.atc.bo.OPlyAtcC;

/**
 * Ordenar atributos por numero de secuencia
 * 
 * @author fjlorente
 */
public class OPlyAtcCComparator implements Comparator<OPlyAtcC> {

    // Used for sorting in ascending order of sequence number 
    public int compare(OPlyAtcC a, OPlyAtcC b) { 
        return a.getOPlyAtrP().getOPlyAtrS().getSqnVal().intValue() - b.getOPlyAtrP().getOPlyAtrS().getSqnVal().intValue(); 
    } 

}
