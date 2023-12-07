package com.mapfre.tron.api.trn.vrb.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.trn.vrb.dl.IDlTrnVrbQryDAO;

import lombok.EqualsAndHashCode;

/**
 * The TrnVrbQry business service interface.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 30 abr. 2021 - 10:26:25
 *
 */
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlTrnVrbQryImpl extends TwBlCmnBase implements IBlTrnVrbQry {

    /** The DlTrnVrbQry repository.*/
    @Autowired
    protected IDlTrnVrbQryDAO iDlTrnVrbQryDAO;

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
     * @return          -> It will return the variable definition filtering by coverage
     */
    @Override
    public List<OTrnVrbS> getVariableDefinitionByCoverage(final String usrVal, final String lngVal,
            final Integer cmpVal, final Integer lobVal, final Integer cvrVal, final String vrbNamVal,
            final Integer mdtVal, final Long vldDat, final String prnVrbVal) {

        try {
            List<OTrnVrbS> lvOTrnVrbST = iDlTrnVrbQryDAO.getVariableDefinitionByCoverage(usrVal, lngVal, cmpVal, lobVal,
                    cvrVal, vrbNamVal, mdtVal, vldDat, prnVrbVal);

            if (lvOTrnVrbST == null || lvOTrnVrbST.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOTrnVrbST;
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "VRB", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

}
