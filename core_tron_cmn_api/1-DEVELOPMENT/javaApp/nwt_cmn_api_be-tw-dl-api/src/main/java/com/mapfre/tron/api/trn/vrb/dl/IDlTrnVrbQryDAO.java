package com.mapfre.tron.api.trn.vrb.dl;

import java.util.List;

import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;

/**
 * TODO General class comments.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 4 may. 2021 - 11:46:38
 *
 */
public interface IDlTrnVrbQryDAO {

    /**
     * Query variable definition by coverage.
     *
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param lobVal    -> Line of Business
     * @param cvrVal    -> Coverage
     * @param vrbNamVal -> Variable name
     * @param mdtVal    -> Modality
     * @param vldDat    -> Validation Date
     * @param prnVrbVal -> Parent Variable Value
     * @return -> It will return the variable definition filtering by coverage
     */
    List<OTrnVrbS> getVariableDefinitionByCoverage(String usrVal, String lngVal, Integer cmpVal, Integer lobVal,
            Integer cvrVal, String vrbNamVal, Integer mdtVal, Long vldDat, String prnVrbVal);

}
