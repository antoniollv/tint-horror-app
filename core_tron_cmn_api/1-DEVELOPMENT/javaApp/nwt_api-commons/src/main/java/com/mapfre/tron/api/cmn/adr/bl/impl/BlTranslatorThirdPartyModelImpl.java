package com.mapfre.tron.api.cmn.adr.bl.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypCPT;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypP;
import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;
import com.mapfre.nwt.trn.thp.pcm.bo.OThpPcmS;
import com.mapfre.tron.api.cmn.adr.bl.IBlTranslatorThirdPartyModel;
import com.mapfre.tron.api.cmn.typ.bl.IBLCmnTypQry;

import lombok.extern.slf4j.Slf4j;

/**
 * The translator third pary model service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 15 Feb 2022 - 08:08:24
 *
 */
@Slf4j
@Service
public class BlTranslatorThirdPartyModelImpl implements IBlTranslatorThirdPartyModel {

    /** The address translator home property.*/
    @Value("${app.env.AdrTranslator.home}")
    protected String home;

    /** The address translator office property.*/
    @Value("${app.env.AdrTranslator.office}")
    protected String office;

    /** The address translator correspondence property.*/
    @Value("${app.env.AdrTranslator.correspondence}")
    protected String correspondence;

    /** The address translator correspondence property.*/
    @Value("${app.env.AdrTranslator.defaultAddressCorrespondence:1}")
    protected String defaultAddressCorrespondence;

    /** The contact translator home property.*/
    @Value("${app.env.CntTranslator.home}")
    protected String cntHome;

    /** The contact translator office property.*/
    @Value("${app.env.CntTranslator.office}")
    protected String cntOffice;

    /** The contact translator correspondence property.*/
    @Value("${app.env.CntTranslator.correspondence}")
    protected String cntCorrespondence;


    /** The contact translator correspondence property.*/
    @Value("${app.env.AdrTranslator.defaultAdr}")
    protected String defaultAdr;

    /** The contact translator correspondence property.*/
    @Value("${app.env.CntTranslator.defaultCnt}")
    protected String defaultCnt;

    /** The contact translator correspondence property.*/
    @Value("${app.env.CntTranslator.priorityCnt}")
    protected String priorityCnt;

    @Autowired
    protected IBLCmnTypQry iBLCmnTypQry;

    /**
     * Get the old adress model from the new model.
     *
     * @param oThpAdrST -> The new model address list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The old address model list
     */
    @Override
    public List<OThpAdrS> getAdrOldModelFromNewModel(final List<OThpAdrS> oThpAdrST, final String usrVal,
            final String lngVal, final BigDecimal cmpVal) {

        List<OThpAdrS> returnList = new ArrayList<>();

        if (oThpAdrST != null && !oThpAdrST.isEmpty()) {
            // ----------------------------------------------------------------------------------- loading OCmnTypS map
            Map<String, String> oCmnTypMap = new HashMap<>();

            // calling query types
            OCmnTypCPT lvOCmnTypCPT = iBLCmnTypQry.prc("TIP_DIRECCION",
                    CPrt.GNC_LOB_VAL,
                    lngVal,
                    CTrn.GET_NAM_ALL,
                    Integer.valueOf(cmpVal.intValue()));

            populatingHashMap(oCmnTypMap, lvOCmnTypCPT);

            // ---------- Sorting OThpAdrS list by key (cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal, adrSqnVal, vldDat)
            sortAddressList(oThpAdrST);

            // ------------------------------------------------------------------------------- filtering by last vldDat
            List<OThpAdrS> firstFilterList = new ArrayList<>();
            OThpAdrS oThpAdrS = null;
            filteringByLastVldDat(oThpAdrST, firstFilterList, oThpAdrS);

            // ---------------------------------------------------------------------------------- filtering by sdbRow=S
            List<OThpAdrS> secondFilterList = new ArrayList<>();
            filteringBySdbRow(firstFilterList, secondFilterList);

            // ------------------------------------------------------------------- filtering by duplicated adrUseTypVal
            Map<String, OThpAdrS> lvOThpAdrSAuxMap = new HashMap<>();
            filteringByDuplicatedAdrUserTypVal(secondFilterList, lvOThpAdrSAuxMap);
            List<OThpAdrS> thirdFilterList = new ArrayList<>(lvOThpAdrSAuxMap.values());

            // ------------------------------------------------------------------------------- procesing the adaptation
            performThirFilterList(returnList, oCmnTypMap, thirdFilterList);
        }

        return returnList;
    }

    protected void performThirFilterList(List<OThpAdrS> returnList, Map<String, String> oCmnTypMap,
            List<OThpAdrS> thirdFilterList) {
        for (OThpAdrS lvOThpAdrS : thirdFilterList) {
            if (home.equalsIgnoreCase(lvOThpAdrS.getAdrUseTypVal()) 
                    || office.equalsIgnoreCase(lvOThpAdrS.getAdrUseTypVal()) 
                            || correspondence.equalsIgnoreCase(lvOThpAdrS.getAdrUseTypVal()) )  {


                if (home.equalsIgnoreCase(lvOThpAdrS.getAdrUseTypVal())) {
                    lvOThpAdrS.setAdrTypVal("1");
                    lvOThpAdrS.setAdrTypNam(oCmnTypMap.get("1"));
                } else if (office.equalsIgnoreCase(lvOThpAdrS.getAdrUseTypVal())) {
                    lvOThpAdrS.setAdrTypVal("2");
                    lvOThpAdrS.setAdrTypNam(oCmnTypMap.get("2"));
                } else if (correspondence.equalsIgnoreCase(lvOThpAdrS.getAdrUseTypVal())) {
                    lvOThpAdrS.setAdrTypVal("3");
                    lvOThpAdrS.setAdrTypNam(oCmnTypMap.get("3"));
                }

                performAdrTextNam(lvOThpAdrS);

                if (lvOThpAdrS.getAdrNbrVal() != null) {
                    lvOThpAdrS.setAdrStrVal(lvOThpAdrS.getAdrNbrVal().toString());
                }

                // deleting new model properties
                lvOThpAdrS.setAdrTxtVal(null);
                lvOThpAdrS.setAdrNbrVal(null);
                lvOThpAdrS.setAdrUseTypVal(null);
                lvOThpAdrS.setAdrUseTypNam(null);

                // adding de updated object
                returnList.add(lvOThpAdrS);
            }
        }
    }

    protected void performAdrTextNam(OThpAdrS lvOThpAdrS) {
        String adrTxtVal;
        String frsAdrLinNam;
        String scnAdrLinNam;
        String thrAdrLinNam;
        adrTxtVal = lvOThpAdrS.getAdrTxtVal();
        frsAdrLinNam = null;
        scnAdrLinNam = null;
        thrAdrLinNam = null;

        if (adrTxtVal != null) {
            if (adrTxtVal.length() <= 40) {
                frsAdrLinNam = adrTxtVal;
            } else {
                frsAdrLinNam = adrTxtVal.substring(0, 39);
                if (adrTxtVal.length() > 40 && adrTxtVal.length() <= 80) {
                    scnAdrLinNam = adrTxtVal.substring(40, adrTxtVal.length());
                } else if (adrTxtVal.length() > 80) {
                    scnAdrLinNam = adrTxtVal.substring(40, 79);
                }

                if (adrTxtVal.length() > 80 && adrTxtVal.length() <= 120) {
                    thrAdrLinNam = adrTxtVal.substring(80, adrTxtVal.length());
                } else if (adrTxtVal.length() > 120) {
                    thrAdrLinNam = adrTxtVal.substring(80, 119);
                }
            }
        }

        lvOThpAdrS.setFrsAdrLinNam(frsAdrLinNam);
        lvOThpAdrS.setScnAdrLinNam(scnAdrLinNam);
        lvOThpAdrS.setThrAdrLinNam(thrAdrLinNam);
    }

    protected void populatingHashMap(Map<String, String> oCmnTypMap, OCmnTypCPT lvOCmnTypCPT) {
        for (OCmnTypP oCmnTypP : lvOCmnTypCPT.getOCmnTypPT()) {
            // populating the hashmap
            oCmnTypMap.put(oCmnTypP.getOCmnTypS().getItcVal(), oCmnTypP.getOCmnTypS().getItcNam());
        }
    }

    protected void filteringByDuplicatedAdrUserTypVal(List<OThpAdrS> secondFilterList,
            Map<String, OThpAdrS> lvOThpAdrSAuxMap) {
        for (OThpAdrS lvOThpAdrS : secondFilterList) {
            if (lvOThpAdrSAuxMap.containsKey(lvOThpAdrS.getAdrUseTypVal())) {
                if ("S".equalsIgnoreCase(lvOThpAdrS.getDflAdr()) || (lvOThpAdrS.getVldDat() != null && 
                    lvOThpAdrSAuxMap.get(lvOThpAdrS.getAdrUseTypVal()).getVldDat() != null && 
                    lvOThpAdrS.getVldDat().compareTo(lvOThpAdrSAuxMap.get(lvOThpAdrS.getAdrUseTypVal()).getVldDat()) <=0)) {
                        lvOThpAdrSAuxMap.put(lvOThpAdrS.getAdrUseTypVal(), lvOThpAdrS);
                }
            } else {
                lvOThpAdrSAuxMap.put(lvOThpAdrS.getAdrUseTypVal(), lvOThpAdrS);
            }
        }
    }

    protected void filteringBySdbRow(List<OThpAdrS> firstFilterList, List<OThpAdrS> secondFilterList) {
        for (OThpAdrS lvOThpAdrS : firstFilterList) {
            if (!"S".equalsIgnoreCase(lvOThpAdrS.getDsbRow())) {
                secondFilterList.add(lvOThpAdrS);
            }
        }
    }

    protected void filteringByLastVldDat(final List<OThpAdrS> oThpAdrST, List<OThpAdrS> firstFilterList,
            OThpAdrS oThpAdrS) {
        for (int i = 0; i < oThpAdrST.size(); i++) {

            if ((i > 0) && ((oThpAdrS.getCmpVal().intValue() != oThpAdrST.get(i).getCmpVal().intValue())
                    || (!oThpAdrS.getThpDcmTypVal().equalsIgnoreCase(oThpAdrST.get(i).getThpDcmTypVal()))
                    || (!oThpAdrS.getThpDcmVal().equalsIgnoreCase(oThpAdrST.get(i).getThpDcmVal()))
                    || (oThpAdrS.getThpAcvVal().intValue() != oThpAdrST.get(i).getThpAcvVal().intValue())
                    || ((oThpAdrS.getAdrSqnVal() != null && oThpAdrST.get(i).getAdrSqnVal() != null) && 
                    (oThpAdrS.getAdrSqnVal().intValue() != oThpAdrST.get(i).getAdrSqnVal().intValue())))) {

                // new object has new key, save the old object
                firstFilterList.add(oThpAdrS);
            }

            // refresh the object
            oThpAdrS = oThpAdrST.get(i);

            if (i == oThpAdrST.size() -1) {
                // save the last object
                firstFilterList.add(oThpAdrS);
            }
        }
    }

    protected void sortAddressList(final List<OThpAdrS> oThpAdrST) {
        oThpAdrST.sort((o1,o2) -> {
            int cmp = o1.getCmpVal().compareTo(o2.getCmpVal());
            if (cmp == 0) {
                cmp = o1.getThpDcmTypVal().compareToIgnoreCase(o2.getThpDcmTypVal());
            }
            if (cmp == 0) {
                cmp = o1.getThpDcmVal().compareToIgnoreCase(o2.getThpDcmVal());
            }
            if (cmp == 0) {
                cmp = o1.getThpAcvVal().compareTo(o2.getThpAcvVal());
            }
            if (cmp == 0 && o1.getAdrSqnVal() != null && o2.getAdrSqnVal() != null) {
                cmp = o1.getAdrSqnVal().compareTo(o2.getAdrSqnVal());
            }
            if (cmp == 0 && o1.getVldDat() != null && o2.getVldDat() != null) {
                cmp = o1.getVldDat().compareTo(o2.getVldDat());
            }

            return cmp;
        });
    }

    /**
     * Get the new model adress from the old model.
     *
     * @param oThpAdrST -> The old model address list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The new address model list
     */
    @Override
    public List<OThpAdrS> getAdrNewModelFromOldModel(final List<OThpAdrS> oThpAdrST, final String usrVal,
            final String lngVal, final BigDecimal cmpVal) {

        List<OThpAdrS> returnList = new ArrayList<>();

        if (oThpAdrST != null && !oThpAdrST.isEmpty()) {
            // ----------------------------------------------------------------------------------- loading OCmnTypS map
            Map<String, String> oCmnTypMap = new HashMap<>();

            // calling query types
            OCmnTypCPT lvOCmnTypCPT = iBLCmnTypQry.prc("ADR_USE_TYP_VAL",
                    CPrt.GNC_LOB_VAL,
                    lngVal,
                    CTrn.GET_NAM_ALL,
                    Integer.valueOf(cmpVal.intValue()));

            populatingHashMap(oCmnTypMap, lvOCmnTypCPT);

            // ------------------------------------------------------------------------------ processing the adaptation
            String adrTypVal;
            for (OThpAdrS oThpAdrS : oThpAdrST) {
                adrTypVal = oThpAdrS.getAdrTypVal();
                processAdrTypVal(oCmnTypMap, adrTypVal, oThpAdrS);
                processAdrLinNam(oThpAdrS);

                if (oThpAdrS.getAdrStrVal() != null) {
                    oThpAdrS.setAdrNbrVal(new BigDecimal(Integer.valueOf(oThpAdrS.getAdrStrVal())));
                }

                Date sysdate = null;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    sysdate = sdf.parse(sdf.format(new Date()));
                    oThpAdrS.setVldDat(sysdate);
                    oThpAdrS.setMdfDat(sysdate);
                } catch (ParseException e) {
                    log.error(e.getMessage());
                }
                oThpAdrS.setDsbRow("N");
                oThpAdrS.setDflAdr("N");
 
                if(defaultAdr.equalsIgnoreCase(adrTypVal)){
                    oThpAdrS.setDflAdr("S");
                }
 
                // deleting properties
                oThpAdrS.setAdrTypVal(null);
                oThpAdrS.setFrsAdrLinNam(null);
                oThpAdrS.setScnAdrLinNam(null);
                oThpAdrS.setThrAdrLinNam(null);
                oThpAdrS.setPobVal(null);
                oThpAdrS.setPslCodExtVal(null);
                oThpAdrS.setAdrDrwVal(null);
                oThpAdrS.setAdrFlrVal(null);
                oThpAdrS.setAdrDorVal(null);
                oThpAdrS.setAdrStaVal(null);
                oThpAdrS.setAdrStrVal(null);
                oThpAdrS.setAdrIdsVal(null);
                oThpAdrS.setMdfScoVal(null);

                if (oThpAdrS.getAdrTxtVal() != null) {
                    returnList.add(oThpAdrS);
                }
            }
        }

        return returnList;
    }

    protected void processAdrLinNam(OThpAdrS oThpAdrS) {
        StringBuilder adrTxtVal = null;
        if (oThpAdrS.getFrsAdrLinNam() != null) {
            adrTxtVal = new StringBuilder(oThpAdrS.getFrsAdrLinNam());

            if (oThpAdrS.getScnAdrLinNam() != null) {
                adrTxtVal.append(" ").append(oThpAdrS.getScnAdrLinNam());

                if (oThpAdrS.getThrAdrLinNam() != null) {
                    adrTxtVal.append(" ").append(oThpAdrS.getThrAdrLinNam());
                }
            }

            if (adrTxtVal.length() > 0) {
                oThpAdrS.setAdrTxtVal(adrTxtVal.toString());
            }
        }
    }

    protected void processAdrTypVal(Map<String, String> oCmnTypMap, String adrTypVal, OThpAdrS oThpAdrS) {
        if ("1".equals(adrTypVal)) {
            oThpAdrS.setAdrUseTypVal(home);
            oThpAdrS.setAdrUseTypNam(oCmnTypMap.get(home));
        } else if ("2".equals(adrTypVal)) {
            oThpAdrS.setAdrUseTypVal(office);
            oThpAdrS.setAdrUseTypNam(oCmnTypMap.get(office));
        } else if ("3".equals(adrTypVal)) {
            oThpAdrS.setAdrUseTypVal(correspondence);
            oThpAdrS.setDmlTypVal(defaultAddressCorrespondence);
            oThpAdrS.setAdrUseTypNam(oCmnTypMap.get(correspondence));
        }
    }

    /**
     * Get the old model contact from the new one.
     *
     * @param oThpCntST -> The old model contact list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The old model contact list
     */
    @Override
    public List<OThpCntS> getCntOldModelFromNewModel(final List<OThpCntS> oThpCntST, final String usrVal,
            final String lngVal, final BigDecimal cmpVal) {

        List<OThpCntS> returnList = new ArrayList<>();

        if (oThpCntST != null && !oThpCntST.isEmpty()) {
            // ----------------------------------------------------------------------------------- loading OCmnTypS map
            Map<String, String> oCmnTypMap = new HashMap<>();

            // calling query types
            OCmnTypCPT lvOCmnTypCPT = iBLCmnTypQry.prc("CONTACT_TYPE",
                    CPrt.GNC_LOB_VAL,
                    lngVal,
                    CTrn.GET_NAM_ALL,
                    Integer.valueOf(cmpVal.intValue()));

            populatingHashMap(oCmnTypMap, lvOCmnTypCPT);

            // ----------- Sorting OThpAdrS list by key (cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal, cnhSqnVal, vldDat)
            sortThpCntList(oThpCntST);

            // ------------------------------------------------------------------------------- filtering by last vldDat
            List<OThpCntS> firstFilterList = filterThpCntByLastVldDat(oThpCntST);

            // ---------------------------------------------------------------------------------- filtering by sdbRow=S
            List<OThpCntS> secondFilterList = filterThpCntBySdbRow(firstFilterList);

            // ------------------------------------------------------------------------------- procesing the adaptation
            // group 1 -> Home
            performGroup1(returnList, oCmnTypMap, secondFilterList);

            // group 2 -> Office
            performGroup2(returnList, oCmnTypMap, secondFilterList);

            // group 3 -> Correspondence
            performGroup3(returnList, oCmnTypMap, secondFilterList);
        }

        return returnList;
    }

    protected void performGroup3(List<OThpCntS> returnList, Map<String, String> oCmnTypMap,
            List<OThpCntS> secondFilterList) {
        OThpCntS modelOThpCntS;
        boolean findCorrespondence = false;
        String emlCorrespondence = null;
        String emlCckCorrespondence = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntCorrespondence.equals(lvOThpCntS.getCnhUseTypVal()) && "1".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findCorrespondence = true;
                emlCorrespondence = lvOThpCntS.getCnhTxtVal();
                emlCckCorrespondence = lvOThpCntS.getCnhCck();
                break;
            }
        }

        String tlfCorrespondence = null;
        String tlfCckCorrespondence = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntCorrespondence.equals(lvOThpCntS.getCnhUseTypVal()) && "2".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findCorrespondence = true;
                tlfCorrespondence = lvOThpCntS.getCnhTxtVal();
                tlfCckCorrespondence = lvOThpCntS.getCnhCck();
                break;
            }
        }

        String clrCorrespondence = null;
        String clrCckCorrespondence = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntCorrespondence.equals(lvOThpCntS.getCnhUseTypVal()) && "3".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findCorrespondence = true;
                clrCorrespondence = lvOThpCntS.getCnhTxtVal();
                clrCckCorrespondence = lvOThpCntS.getCnhCck();
                break;
            }
        }

        String faxCorrespondence = null;
        String faxCckCorrespondence = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntCorrespondence.equals(lvOThpCntS.getCnhUseTypVal()) && "5".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findCorrespondence = true;
                faxCorrespondence = lvOThpCntS.getCnhTxtVal();
                faxCckCorrespondence = lvOThpCntS.getCnhCck();
                break;
            }
        }

        // checking group 3 -> correspondence
        if (findCorrespondence) {
            modelOThpCntS = this.createModel(secondFilterList.get(0));
            modelOThpCntS.setCntTypVal("3");
            modelOThpCntS.setCntTypNam(oCmnTypMap.get("3"));
            modelOThpCntS.setTlpVal(tlfCorrespondence);
            modelOThpCntS.setClrTlpVal(clrCorrespondence);
            modelOThpCntS.setEmlAdr(emlCorrespondence);
            modelOThpCntS.setFaxVal(faxCorrespondence);
            modelOThpCntS.setTlpCnyCck(tlfCckCorrespondence);
            modelOThpCntS.setFaxCck(faxCckCorrespondence);
            modelOThpCntS.setEmlAdrCck(emlCckCorrespondence);
            modelOThpCntS.setClrTlpCck(clrCckCorrespondence);

            returnList.add(modelOThpCntS);
        }
    }

    protected void performGroup2(List<OThpCntS> returnList, Map<String, String> oCmnTypMap,
            List<OThpCntS> secondFilterList) {
        OThpCntS modelOThpCntS;
        boolean findOffice = false;
        String emlOffice = null;
        String emlCckOffice = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntOffice.equals(lvOThpCntS.getCnhUseTypVal()) && "1".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findOffice = true;
                emlOffice = lvOThpCntS.getCnhTxtVal();
                emlCckOffice = lvOThpCntS.getCnhCck();
                break;
            }
        }

        String tlfOffice = null;
        String tlfCckOffice = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntOffice.equals(lvOThpCntS.getCnhUseTypVal()) && "2".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findOffice = true;
                tlfOffice = lvOThpCntS.getCnhTxtVal();
                tlfCckOffice = lvOThpCntS.getCnhCck();
                break;
            }
        }

        String clrOffice = null;
        String clrCckOffice = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntOffice.equals(lvOThpCntS.getCnhUseTypVal()) && "3".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findOffice = true;
                clrOffice = lvOThpCntS.getCnhTxtVal();
                clrCckOffice = lvOThpCntS.getCnhCck();
                break;
            }
        }

        String faxOffice = null;
        String faxCckOffice = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntOffice.equals(lvOThpCntS.getCnhUseTypVal()) && "5".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findOffice = true;
                faxOffice = lvOThpCntS.getCnhTxtVal();
                faxCckOffice = lvOThpCntS.getCnhCck();
                break;
            }
        }

        // checking group 2 -> office
        if (findOffice) {
            modelOThpCntS = this.createModel(secondFilterList.get(0));
            modelOThpCntS.setCntTypVal("2");
            modelOThpCntS.setCntTypNam(oCmnTypMap.get("2"));
            modelOThpCntS.setTlpVal(tlfOffice);
            modelOThpCntS.setClrTlpVal(clrOffice);
            modelOThpCntS.setEmlAdr(emlOffice);
            modelOThpCntS.setFaxVal(faxOffice);
            modelOThpCntS.setTlpCnyCck(tlfCckOffice);
            modelOThpCntS.setFaxCck(faxCckOffice);
            modelOThpCntS.setEmlAdrCck(emlCckOffice);
            modelOThpCntS.setClrTlpCck(clrCckOffice);

            returnList.add(modelOThpCntS);
        }
    }

    protected void performGroup1(List<OThpCntS> returnList, Map<String, String> oCmnTypMap,
            List<OThpCntS> secondFilterList) {

        OThpCntS modelOThpCntS;

        // group 1 -> Home
        boolean findHome = false;
        String emlHome = null;
        String emlCckHome = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntHome.equals(lvOThpCntS.getCnhUseTypVal()) && "1".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findHome = true;
                emlHome = lvOThpCntS.getCnhTxtVal();
                emlCckHome = lvOThpCntS.getCnhCck();
                break;
            }
        }

        String tlfHome = null;
        String tlfCckHome = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntHome.equals(lvOThpCntS.getCnhUseTypVal()) && "2".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findHome = true;
                tlfHome = lvOThpCntS.getCnhTxtVal();
                tlfCckHome = lvOThpCntS.getCnhCck();
                break;
            }
        }

        String clrHome = null;
        String clrCckHome = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntHome.equals(lvOThpCntS.getCnhUseTypVal()) && "3".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                clrHome = lvOThpCntS.getCnhTxtVal();
                findHome = true;
                clrCckHome = lvOThpCntS.getCnhCck();
                break;
            }
        }

        String faxHome = null;
        String faxCckHome = null;
        for (OThpCntS lvOThpCntS : secondFilterList) {
            if (cntHome.equals(lvOThpCntS.getCnhUseTypVal()) && "5".equals(lvOThpCntS.getCnhTypVal())
                    && "S".equalsIgnoreCase(lvOThpCntS.getDflCnh())) {
                findHome = true;
                faxHome = lvOThpCntS.getCnhTxtVal();
                faxCckHome = lvOThpCntS.getCnhCck();
                break;
            }
        }

        // checking group 1 -> home
        if (findHome) {
            modelOThpCntS = this.createModel(secondFilterList.get(0));
            modelOThpCntS.setCntTypVal("1");
            modelOThpCntS.setCntTypNam(oCmnTypMap.get("1"));
            modelOThpCntS.setTlpVal(tlfHome);
            modelOThpCntS.setClrTlpVal(clrHome);
            modelOThpCntS.setEmlAdr(emlHome);
            modelOThpCntS.setFaxVal(faxHome);
            modelOThpCntS.setTlpCnyCck(tlfCckHome);
            modelOThpCntS.setFaxCck(faxCckHome);
            modelOThpCntS.setEmlAdrCck(emlCckHome);
            modelOThpCntS.setClrTlpCck(clrCckHome);

            returnList.add(modelOThpCntS);
        }
    }

    protected List<OThpCntS> filterThpCntBySdbRow(List<OThpCntS> firstFilterList) {
        List<OThpCntS> secondFilterList = new ArrayList<>();
        for (OThpCntS lvOThpCntS : firstFilterList) {
            if (!"S".equalsIgnoreCase(lvOThpCntS.getDsbRow())) {
                secondFilterList.add(lvOThpCntS);
            }
        }
        return secondFilterList;
    }

    protected List<OThpCntS> filterThpCntByLastVldDat(final List<OThpCntS> oThpCntST) {
        List<OThpCntS> firstFilterList = new ArrayList<>();
        OThpCntS oThpCntS = null;
        for (int i = 0; i < oThpCntST.size(); i++) {

            if ((i > 0) && ((oThpCntS.getCmpVal().intValue() != oThpCntST.get(i).getCmpVal().intValue())
                    || (!oThpCntS.getThpDcmTypVal().equalsIgnoreCase(oThpCntST.get(i).getThpDcmTypVal()))
                    || (!oThpCntS.getThpDcmVal().equalsIgnoreCase(oThpCntST.get(i).getThpDcmVal()))
                    || (oThpCntS.getThpAcvVal().intValue() != oThpCntST.get(i).getThpAcvVal().intValue())
                    || ((oThpCntS.getCnhSqnVal() != null && oThpCntST.get(i).getCnhSqnVal() != null) && 
                    (oThpCntS.getCnhSqnVal().intValue() != oThpCntST.get(i).getCnhSqnVal().intValue())))) {

                // new object has new key, save the old object
                firstFilterList.add(oThpCntS);
            }

            // refresh the object
            oThpCntS = oThpCntST.get(i);

            if (i == oThpCntST.size() -1) {
                // save the last object
                firstFilterList.add(oThpCntS);
            }
        }
        return firstFilterList;
    }

    protected void sortThpCntList(final List<OThpCntS> oThpCntST) {
        oThpCntST.sort((o1,o2) -> {
            int cmp = o1.getCmpVal().compareTo(o2.getCmpVal());
            if (cmp == 0) {
                cmp = o1.getThpDcmTypVal().compareToIgnoreCase(o2.getThpDcmTypVal());
            }
            if (cmp == 0) {
                cmp = o1.getThpDcmVal().compareToIgnoreCase(o2.getThpDcmVal());
            }
            if (cmp == 0) {
                cmp = o1.getThpAcvVal().compareTo(o2.getThpAcvVal());
            }
            if (cmp == 0 && o1.getCnhSqnVal() != null && o2.getCnhSqnVal() != null) {
                cmp = o1.getCnhSqnVal().compareTo(o2.getCnhSqnVal());
            }
            if (cmp == 0 && o1.getVldDat() != null && o2.getVldDat() != null) {
                cmp = o1.getVldDat().compareTo(o2.getVldDat());
            }

            return cmp;
        });
    }

    /**
     * Get the new model contact from the old one.
     *
     * @param oThpCntST -> The old model contact list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The new model contact list
     */
    @Override
    public List<OThpCntS> getCntNewModelFromOldModel(final List<OThpCntS> oThpCntST, final String usrVal,
            final String lngVal, final BigDecimal cmpVal) {

        List<OThpCntS> returnList = new ArrayList<>();

        if (oThpCntST != null && !oThpCntST.isEmpty()) {
            // ---------------------------------------------------------------------------------- loading OCmnTypS maps
            Map<String, String> oCmnTypMap1 = new HashMap<>();

            // calling query types
            OCmnTypCPT lvOCmnTypCPT = iBLCmnTypQry.prc("CNH_USE_TYP_VAL",
                    CPrt.GNC_LOB_VAL,
                    lngVal,
                    CTrn.GET_NAM_ALL,
                    Integer.valueOf(cmpVal.intValue()));

            populatingHashMap(oCmnTypMap1, lvOCmnTypCPT);

            Map<String, String> oCmnTypMap2 = new HashMap<>();

            // calling query types
            lvOCmnTypCPT = iBLCmnTypQry.prc("CNH_TYP_VAL",
                    CPrt.GNC_LOB_VAL,
                    lngVal,
                    CTrn.GET_NAM_ALL,
                    Integer.valueOf(cmpVal.intValue()));

            populatingHashMap(oCmnTypMap2, lvOCmnTypCPT);

            // ------------------------------------------------------------------------------- procesing the adaptation
            for (OThpCntS oThpCntS: oThpCntST) {
                performCntTypVal1(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS);
                performCntTypVal2(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS);
                performCntTypVal3(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS);
            }

        }

        return returnList;
    }

    protected void performCntTypVal3(final List<OThpCntS> oThpCntST, List<OThpCntS> returnList,
            Map<String, String> oCmnTypMap1, Map<String, String> oCmnTypMap2, OThpCntS oThpCntS) {

        if ("3".equals(oThpCntS.getCntTypVal())) {
            performThpCntTlpVal(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntCorrespondence, "2");
            performThpCntEmlAdr(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntCorrespondence, "1");
            performThpCntFaxVal(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntCorrespondence, "5");
            performThpCntClrTlp(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntCorrespondence, "3"); 
        }
    }

    protected void performCntTypVal2(final List<OThpCntS> oThpCntST, List<OThpCntS> returnList,
            Map<String, String> oCmnTypMap1, Map<String, String> oCmnTypMap2, OThpCntS oThpCntS) {

        if ("2".equals(oThpCntS.getCntTypVal())) {
            performThpCntTlpVal(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntOffice, "2");
            performThpCntEmlAdr(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntOffice, "1");
            performThpCntFaxVal(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntOffice, "5");
            performThpCntClrTlp(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntOffice, "3");
        }
    }

    protected void performCntTypVal1(final List<OThpCntS> oThpCntST, List<OThpCntS> returnList,
            Map<String, String> oCmnTypMap1, Map<String, String> oCmnTypMap2, OThpCntS oThpCntS) {

        if ("1".equals(oThpCntS.getCntTypVal())) {
            performThpCntTlpVal(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntHome, "2");
            performThpCntEmlAdr(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntHome, "1");
            performThpCntFaxVal(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntHome, "5");
            performThpCntClrTlp(oThpCntST, returnList, oCmnTypMap1, oCmnTypMap2, oThpCntS, cntHome, "3"); 
        }
    }

    protected void performThpCntClrTlp(final List<OThpCntS> oThpCntST, List<OThpCntS> returnList,
            Map<String, String> oCmnTypMap1, Map<String, String> oCmnTypMap2, OThpCntS oThpCntS, String cnhUseTypVal,
            String cnhTypVal) {

        OThpCntS modelOThpCntS;
        if (oThpCntS.getClrTlpVal() != null) {
            // ClrTlpVal property with value
            modelOThpCntS = this.createModel(oThpCntST.get(0));
            modelOThpCntS.setCnhTypVal(cnhTypVal);
            modelOThpCntS.setCnhTypNam(oCmnTypMap2.get(cnhTypVal));
            modelOThpCntS.setCnhUseTypVal(cnhUseTypVal);
            modelOThpCntS.setCnhUseTypNam(oCmnTypMap1.get(cnhUseTypVal));
            modelOThpCntS.setCnhTxtVal(oThpCntS.getClrTlpVal());
            modelOThpCntS.setCnhCck(oThpCntS.getClrTlpCck());
            modelOThpCntS.setDflCnh("N");
            modelOThpCntS.setPtyCnh("N");
            if(defaultCnt.equalsIgnoreCase(oThpCntS.getCntTypVal())){
                modelOThpCntS.setDflCnh("S");
            }
            if(defaultCnt.equalsIgnoreCase(oThpCntS.getCntTypVal()) && priorityCnt.equalsIgnoreCase(cnhTypVal)){
                modelOThpCntS.setPtyCnh("S");
            }
            returnList.add(modelOThpCntS);
        }
    }

    protected void performThpCntFaxVal(final List<OThpCntS> oThpCntST, List<OThpCntS> returnList,
            Map<String, String> oCmnTypMap1, Map<String, String> oCmnTypMap2, OThpCntS oThpCntS, String cnhUseTypVal,
            String cnhTypVal) {

        OThpCntS modelOThpCntS;
        if (oThpCntS.getFaxVal() != null) {
            // FaxVal property with value
            modelOThpCntS = this.createModel(oThpCntST.get(0));
            modelOThpCntS.setCnhTypVal(cnhTypVal);
            modelOThpCntS.setCnhTypNam(oCmnTypMap2.get(cnhTypVal));
            modelOThpCntS.setCnhUseTypVal(cnhUseTypVal);
            modelOThpCntS.setCnhUseTypNam(oCmnTypMap1.get(cnhUseTypVal));
            modelOThpCntS.setCnhTxtVal(oThpCntS.getFaxVal());
            modelOThpCntS.setCnhCck(oThpCntS.getFaxCck());
            modelOThpCntS.setDflCnh("N");
            modelOThpCntS.setPtyCnh("N");
            if(defaultCnt.equalsIgnoreCase(oThpCntS.getCntTypVal())){
                modelOThpCntS.setDflCnh("S");
            }
            if(defaultCnt.equalsIgnoreCase(oThpCntS.getCntTypVal()) && priorityCnt.equalsIgnoreCase(cnhTypVal)){
                modelOThpCntS.setPtyCnh("S");
            }
            returnList.add(modelOThpCntS);
        }
    }

    protected void performThpCntEmlAdr(final List<OThpCntS> oThpCntST, List<OThpCntS> returnList,
            Map<String, String> oCmnTypMap1, Map<String, String> oCmnTypMap2, OThpCntS oThpCntS, String cnhUseTypVal,
            String cnhTypVal) {

        OThpCntS modelOThpCntS;
        if (oThpCntS.getEmlAdr() != null) {
            // EmlEdr property with value
            modelOThpCntS = this.createModel(oThpCntST.get(0));
            modelOThpCntS.setCnhTypVal(cnhTypVal);
            modelOThpCntS.setCnhTypNam(oCmnTypMap2.get(cnhTypVal));
            modelOThpCntS.setCnhUseTypVal(cnhUseTypVal);
            modelOThpCntS.setCnhUseTypNam(oCmnTypMap1.get(cnhUseTypVal));
            modelOThpCntS.setCnhTxtVal(oThpCntS.getEmlAdr());
            modelOThpCntS.setCnhCck(oThpCntS.getEmlAdrCck());
            modelOThpCntS.setDflCnh("N");
            modelOThpCntS.setPtyCnh("N");
            if(defaultCnt.equalsIgnoreCase(oThpCntS.getCntTypVal())){
                modelOThpCntS.setDflCnh("S");
            }
            if(defaultCnt.equalsIgnoreCase(oThpCntS.getCntTypVal()) && priorityCnt.equalsIgnoreCase(cnhTypVal)){
                modelOThpCntS.setPtyCnh("S");
            }
            returnList.add(modelOThpCntS);
        }
    }

    protected void performThpCntTlpVal(final List<OThpCntS> oThpCntST, List<OThpCntS> returnList,
            Map<String, String> oCmnTypMap1, Map<String, String> oCmnTypMap2, OThpCntS oThpCntS, String cnhUseTypVal,
            String cnhTypVal) {

        OThpCntS modelOThpCntS;
        StringBuilder cnhTxtVal;
        if (oThpCntS.getTlpVal() != null) {
            // TlpVal property with value
            modelOThpCntS = this.createModel(oThpCntST.get(0));
            modelOThpCntS.setCnhTypVal(cnhTypVal);
            modelOThpCntS.setCnhTypNam(oCmnTypMap2.get(cnhTypVal));
            modelOThpCntS.setCnhUseTypVal(cnhUseTypVal);
            modelOThpCntS.setCnhUseTypNam(oCmnTypMap1.get(cnhUseTypVal));
            if (isThreatOThpCntS(oThpCntS)) {
                cnhTxtVal = new StringBuilder();
                appendTlpCnyVal(oThpCntS, cnhTxtVal);
                appendTlpAreVal(oThpCntS, cnhTxtVal);
                appendTlpVal(oThpCntS, cnhTxtVal);
                modelOThpCntS.setCnhTxtVal(cnhTxtVal.toString().trim());
            }
            modelOThpCntS.setCnhCck(oThpCntS.getTlpCnyCck());
            modelOThpCntS.setDflCnh("N");
            modelOThpCntS.setPtyCnh("N");
            if(defaultCnt.equalsIgnoreCase(oThpCntS.getCntTypVal())){
                modelOThpCntS.setDflCnh("S");
            }
            if(defaultCnt.equalsIgnoreCase(oThpCntS.getCntTypVal()) && priorityCnt.equalsIgnoreCase(cnhTypVal)){
                modelOThpCntS.setPtyCnh("S");
            }
            returnList.add(modelOThpCntS);
        }
    }

    protected void appendTlpVal(OThpCntS oThpCntS, StringBuilder cnhTxtVal) {
        if (oThpCntS.getTlpVal() != null) {
            cnhTxtVal.append(" ");
            cnhTxtVal.append(oThpCntS.getTlpVal());
        }
    }

    protected void appendTlpAreVal(OThpCntS oThpCntS, StringBuilder cnhTxtVal) {
        if (oThpCntS.getTlpAreVal() != null) {
            cnhTxtVal.append(" ");
            cnhTxtVal.append(oThpCntS.getTlpAreVal());
        }
    }

    protected void appendTlpCnyVal(OThpCntS oThpCntS, StringBuilder cnhTxtVal) {
        if (oThpCntS.getTlpCnyVal() != null) {
            cnhTxtVal.append(oThpCntS.getTlpCnyVal());
        }
    }

    boolean isThreatOThpCntS(OThpCntS oThpCntS) {
        boolean perform = false;
        if (oThpCntS.getTlpCnyVal() != null || oThpCntS.getTlpAreVal() != null ||
                oThpCntS.getTlpVal() != null) {
            perform = true;
        }
        return perform;
    }

    /**
     * Create a model of OthpCntS object.
     *
     * @param oThpCntS -> The base model object 
     * @return         -> The created model object
     */
    protected OThpCntS createModel(final OThpCntS baseOThpCntS) {
        OThpCntS modelOThpCntS = new OThpCntS();
        modelOThpCntS.setCmpVal(baseOThpCntS.getCmpVal());
        modelOThpCntS.setThpDcmTypVal(baseOThpCntS.getThpDcmTypVal());
        modelOThpCntS.setThpDcmVal(baseOThpCntS.getThpDcmVal());
        modelOThpCntS.setThpAcvVal(baseOThpCntS.getThpAcvVal());

        return modelOThpCntS;
    }

    /**
     * Get the old model payments methods from the new one.
     *
     * @param OThpPcmST -> The new payments methods list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The old payments methods list
     */
    @Override
    public List<OThpPcmS> getPcmOldModelFromNewModel(final List<OThpPcmS> oThpPcmST, final String usrVal,
            final String lngVal, final BigDecimal cmpVal) {

        List<OThpPcmS> returnList = new ArrayList<>();

        if (oThpPcmST != null && !oThpPcmST.isEmpty()) {

            // ---------- Ordering oThpPcmST list by key (cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal, pcmSqnVal, vldDat)
            sortPaymentMethodList(oThpPcmST);

            // ------------------------------------------------------------------------------- filtering by last vldDat
            List<OThpPcmS> firstFilterList = new ArrayList<>();
            filterByLastVldDatPaymentMethodList(oThpPcmST, firstFilterList);

            // ------------------------------------------------------------------------------- procesing the adaptation
            OThpPcmS modelOThpPcmS = null;
            for (OThpPcmS lvOThpPcmS : firstFilterList) {
                if ("N".equalsIgnoreCase(lvOThpPcmS.getDsbRow())) {
                    modelOThpPcmS = this.createModel(lvOThpPcmS);
                    modelOThpPcmS.setSqnAcoCrdVal(lvOThpPcmS.getPcmSqnVal());
                    modelOThpPcmS.setAcoCrdTypVal(lvOThpPcmS.getPcmTypVal());
                    modelOThpPcmS.setAcoCrdTypNam(lvOThpPcmS.getPcmTypNam());
                    modelOThpPcmS.setCrrAcoDsp(lvOThpPcmS.getMskVal());
                    modelOThpPcmS.setCucHlrNam(lvOThpPcmS.getHlrNam());

                    returnList.add(modelOThpPcmS);
                }
            }

        }

        return returnList;
    }

    protected void filterByLastVldDatPaymentMethodList(final List<OThpPcmS> oThpPcmST, List<OThpPcmS> firstFilterList) {
        OThpPcmS oThpPcmS = null;
        for (int i = 0; i < oThpPcmST.size(); i++) {

            if ((i > 0) && ((oThpPcmS.getCmpVal().intValue() != oThpPcmST.get(i).getCmpVal().intValue())
                    || (!oThpPcmS.getThpDcmTypVal().equalsIgnoreCase(oThpPcmST.get(i).getThpDcmTypVal()))
                    || (!oThpPcmS.getThpDcmVal().equalsIgnoreCase(oThpPcmST.get(i).getThpDcmVal()))
                    || (oThpPcmS.getThpAcvVal().intValue() != oThpPcmST.get(i).getThpAcvVal().intValue())
                    || ((oThpPcmS.getPcmSqnVal() != null && oThpPcmST.get(i).getPcmSqnVal() != null) && 
                    (oThpPcmS.getPcmSqnVal().intValue() != oThpPcmST.get(i).getPcmSqnVal().intValue())))) {

                // new object has new key, save the old object
                firstFilterList.add(oThpPcmS);
            }

            // refresh the object
            oThpPcmS = oThpPcmST.get(i);

            if (i == oThpPcmST.size() -1) {
                // save the last object
                firstFilterList.add(oThpPcmS);
            }
        }
    }

    protected void sortPaymentMethodList(final List<OThpPcmS> oThpPcmST) {
        oThpPcmST.sort((o1,o2) -> {
            int cmp = o1.getCmpVal().compareTo(o2.getCmpVal());
            if (cmp == 0) {
                cmp = o1.getThpDcmTypVal().compareToIgnoreCase(o2.getThpDcmTypVal());
            }
            if (cmp == 0) {
                cmp = o1.getThpDcmVal().compareToIgnoreCase(o2.getThpDcmVal());
            }
            if (cmp == 0) {
                cmp = o1.getThpAcvVal().compareTo(o2.getThpAcvVal());
            }
            if (cmp == 0 && o1.getPcmSqnVal() != null && o2.getPcmSqnVal() != null) {
                cmp = o1.getPcmSqnVal().compareTo(o2.getPcmSqnVal());
            }
            if (cmp == 0 && o1.getVldDat() != null && o2.getVldDat() != null) {
                cmp = o1.getVldDat().compareTo(o2.getVldDat());
            }

            return cmp;
        });
    }

    /**
     * Get the new model payments methods from the old one.
     *
     * @param OThpPcmST -> The old payments methods list
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company value
     * @return          -> The new payments methods list
     */
    @Override
    public List<OThpPcmS> getPcmNewModelFromOldModel(final List<OThpPcmS> oThpPcmST, final String usrVal,
            final String lngVal, final BigDecimal cmpVal) {

        List<OThpPcmS> returnList = new ArrayList<>();

        if (oThpPcmST != null && !oThpPcmST.isEmpty()) {

            // ---------------------------------------------------------------------------------- filtering by sdbRow=S
            List<OThpPcmS> filterList = new ArrayList<>();
            for (OThpPcmS lvOThpPcmS : oThpPcmST) {
                if (!"S".equalsIgnoreCase(lvOThpPcmS.getDsbRow())) {
                    filterList.add(lvOThpPcmS);
                }
            }

            // ------------------------------------------------------------------------------- procesing the adaptation
            OThpPcmS modelOThpPcmS = null;
            for (OThpPcmS lvOThpPcmS : filterList) {
                modelOThpPcmS = this.createModel(lvOThpPcmS);
                modelOThpPcmS.setPcmSqnVal(lvOThpPcmS.getSqnAcoCrdVal());
                modelOThpPcmS.setPcmTypVal(lvOThpPcmS.getAcoCrdTypVal());
                modelOThpPcmS.setPcmTypNam(lvOThpPcmS.getAcoCrdTypNam());
                modelOThpPcmS.setMskVal(lvOThpPcmS.getCrrAcoDsp());
                modelOThpPcmS.setHlrNam(lvOThpPcmS.getCucHlrNam());

                returnList.add(modelOThpPcmS);
            }
        }

        return returnList;
    }

    /**
     * Create a model of OthpCntS object.
     *
     * @param oThpCntS -> The base model object 
     * @return         -> The created model object
     */
    protected OThpPcmS createModel(final OThpPcmS baseOThpPcmS) {
        OThpPcmS modelOThpPcmS = new OThpPcmS();
        modelOThpPcmS.setCmpVal(baseOThpPcmS.getCmpVal());
        modelOThpPcmS.setThpDcmTypVal(baseOThpPcmS.getThpDcmTypVal());
        modelOThpPcmS.setThpDcmVal(baseOThpPcmS.getThpDcmVal());
        modelOThpPcmS.setThpAcvVal(baseOThpPcmS.getThpAcvVal());
        modelOThpPcmS.setCnyVal(baseOThpPcmS.getCnyVal());
        modelOThpPcmS.setCnyNam(baseOThpPcmS.getCnyNam());
        modelOThpPcmS.setBneVal(baseOThpPcmS.getBneVal());
        modelOThpPcmS.setBneNam(baseOThpPcmS.getBneNam());
        modelOThpPcmS.setCrnVal(baseOThpPcmS.getCrnVal());
        modelOThpPcmS.setCrnNam(baseOThpPcmS.getCrnNam());
        modelOThpPcmS.setDsbRow(baseOThpPcmS.getDsbRow());

        return modelOThpPcmS;
    }

}
