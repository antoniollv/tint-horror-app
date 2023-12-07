
package com.mapfre.tron.api.prt.del.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.prt.del.dl.ISrPrtDelQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The tronweb CmnBL business service implementation.
 *
 * @author pvraul1
 * @since 1.8
 * @version 6 ago. 2019 12:41:13
 *
 */
@Data
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlPrtDelQryImpl extends TwBlCmnBase implements IBlPrtDelQry {

    @Autowired
    protected ISrPrtDelQryDAO iSrPrtDelQryDAO;

    /**
     * Query deals. It will return the deals that belong to a line of business.
     *
     * @param lngVal -> Language code
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lobVal -> Line of Business
     * @param vldDat -> Valid Date
     *
     * @return List<OPrtDelS> 
     */
    @Override
    public List<OPrtDelS> get(final String usrVal, final String lngVal, final Integer cmpVal, final Long vldDat,
            final Integer lobVal) {

        try {
            List<OPrtDelS> oPrtDelSList = iSrPrtDelQryDAO.get(usrVal, lngVal, cmpVal, vldDat, lobVal);

            if (oPrtDelSList == null || oPrtDelSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oPrtDelSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "DEL", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

}
