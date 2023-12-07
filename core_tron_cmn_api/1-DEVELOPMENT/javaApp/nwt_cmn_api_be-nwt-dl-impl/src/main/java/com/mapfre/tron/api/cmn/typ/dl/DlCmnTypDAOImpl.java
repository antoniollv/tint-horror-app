package com.mapfre.tron.api.cmn.typ.dl;

import java.math.BigDecimal;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;

@Data
@Repository
public class DlCmnTypDAOImpl implements IDlCmnTypDAO {

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
		jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }


    @Override
    public List<OCmnTypS> get(BigDecimal cmpVal, String fldVal, String itcVal, String usrLngVal) {

		final String query = "SELECT g.COD_CAMPO, g.NUM_ORDEN, g.COD_VALOR, g.COD_IDIOMA, g.NOM_VALOR "
			+ "FROM g1010031 g "
			+ "WHERE g.cod_cia = ? AND g.cod_campo= ? AND g.cod_valor = ?";
	
		List<OCmnTypS> oCmnTypPT;
	
		oCmnTypPT = jdbcTemplate.query(query,
			new Object[]{
				cmpVal,
				fldVal,
				itcVal,
			},
			new OCmnTypRowMapper());
	
		if (oCmnTypPT.isEmpty()) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "TYP", "FLD_NAM", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
		
		return oCmnTypPT;
    }
}
