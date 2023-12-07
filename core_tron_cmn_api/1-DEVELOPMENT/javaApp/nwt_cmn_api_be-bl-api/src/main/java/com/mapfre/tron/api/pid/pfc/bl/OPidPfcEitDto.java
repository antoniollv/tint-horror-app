package com.mapfre.tron.api.pid.pfc.bl;

import java.util.List;

import com.mapfre.nwt.trn.bo.ObjNwt;
import com.mapfre.nwt.trn.pid.ivf.bo.OPidIvfP;
import com.mapfre.nwt.trn.pid.pfc.bo.OPidPfcP;
import com.mapfre.nwt.trn.pid.pfm.bo.OPidPfmP;
import com.mapfre.nwt.trn.pid.psp.bo.OPidPspP;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OPidPfcEitDto implements ObjNwt {
	
	private static final long serialVersionUID = 4664385242772994152L;
	// fondo inversión conjunto
	private List<OPidIvfP> OPidIvfPT;
	// cesta fondo composicion conjunto
	private List<OPidPfcP> OPidPfcPT;
	// cesta fondo distribucion conjunto
	private List<OPidPspP> OPidPspPT;
	// cesta fondo documento conjunto
	private List<OPidPfmP> OPidPfmPT;

}
