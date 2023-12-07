
package com.mapfre.tron.api.cmn.prt.lob.bl.impl.tw;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.prt.lob.bl.IBlCacDelQry;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb cache query implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 8 Jun 2021 - 11:27:11
 *
 */
@Data
@Slf4j
@Service
public class TwCmnBlCacDelQryImpl implements IBlCacDelQry {

    @Override
    @CacheEvict(value = "tablaAccesoUsuario", allEntries = true)
    public void flushCache() {
        log.info("tablaAccesoUsuario evicted");
    }

    @Override
    @CacheEvict(value = "OPrtLobP", allEntries = true)
    public void flushCacheRamo() {
        log.info("tablaRamo evicted");
    }

    @Override
    @CacheEvict(value = "PoC-NewTRON", allEntries = true)
    public void flushCacheNewTRON() {
        log.info("PoC-NewTRON evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OArdAbvCPT", allEntries = true)
    public void flushCacheOArdAbvCPT() {
        log.info("PoC-OArdAbvCPT evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmnColS", allEntries = true)
    public void flushCacheOCmnColS() {
        log.info("PoC-OCmnColS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmnColSList", allEntries = true)
    public void flushCacheOCmnColSList() {
        log.info("PoC-OCmnColSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmnTypS", allEntries = true)
    public void flushCacheOCmnTypS() {
        log.info("PoC-OCmnTypS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmnTypSListAll", allEntries = true)
    public void flushCacheOCmnTypSListAll() {
        log.info("PoC-OCmnTypSListAll evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmnTypSList", allEntries = true)
    public void flushCacheOCmnTypSList() {
        log.info("PoC-OCmnTypSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuCmpSList", allEntries = true)
    public void flushCacheOCmuCmpSList() {
        log.info("PoC-OCmuCmpSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuCmpSList2", allEntries = true)
    public void flushCacheOCmuCmpSList2() {
        log.info("PoC-OCmuCmpSList2 evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuThlS", allEntries = true)
    public void flushCacheOCmuThlS() {
        log.info("PoC-OCmuThlS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuThlSList", allEntries = true)
    public void flushCacheOCmuThlSList() {
        log.info("PoC-OCmuThlSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmnColP", allEntries = true)
    public void flushCacheOCmnColP() {
        log.info("PoC-OCmnColP evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmnColPList", allEntries = true)
    public void flushCacheOCmnColPList() {
        log.info("PoC-OCmnColPList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCrnCrnS", allEntries = true)
    public void flushCacheOCrnCrnS() {
        log.info("PoC-OCrnCrnS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCrnCrnSList", allEntries = true)
    public void flushCacheOCrnCrnSList() {
        log.info("PoC-OCrnCrnSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZnfS", allEntries = true)
    public void flushCacheOGrsZnfS() {
        log.info("PoC-OGrsZnfS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZnfSListAll", allEntries = true)
    public void flushCacheOGrsZnfSListAll() {
        log.info("PoC-OGrsZnfSListAll evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZnfSList", allEntries = true)
    public void flushCacheOGrsZnfSList() {
        log.info("PoC-OGrsZnfSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZnoS", allEntries = true)
    public void flushCacheOGrsZnoS() {
        log.info("PoC-OGrsZnoS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZnoSList", allEntries = true)
    public void flushCacheOGrsZnoSList() {
        log.info("PoC-OGrsZnoSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZntS", allEntries = true)
    public void flushCacheOGrsZntS() {
        log.info("PoC-OGrsZntS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZntSList", allEntries = true)
    public void flushCacheOGrsZntSList() {
        log.info("PoC-OGrsZntSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZntSListAll", allEntries = true)
    public void flushCacheOGrsZntSListAll() {
        log.info("PoC-OGrsZntSListAll evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZofS", allEntries = true)
    public void flushCacheOGrsZofS() {
        log.info("PoC-OGrsZofS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZofSList", allEntries = true)
    public void flushCacheOGrsZofSList() {
        log.info("PoC-OGrsZofSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZofSFiveList", allEntries = true)
    public void flushCacheOGrsZofSFiveList() {
        log.info("PoC-OGrsZofSFiveList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZofSListAll", allEntries = true)
    public void flushCacheOGrsZofSListAll() {
        log.info("PoC-OGrsZofSListAll evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZotS", allEntries = true)
    public void flushCacheOGrsZotS() {
        log.info("PoC-OGrsZotS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZotSList", allEntries = true)
    public void flushCacheOGrsZotSList() {
        log.info("PoC-OGrsZotSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OGrsZotSListAll", allEntries = true)
    public void flushCacheOGrsZotSListAll() {
        log.info("PoC-OGrsZotSListAll evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmnLngP", allEntries = true)
    public void flushCacheOCmnLngP() {
        log.info("PoC-OCmnLngP evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmnLngPListAll", allEntries = true)
    public void flushCacheOCmnLngPListAll() {
        log.info("PoC-OCmnLngPListAll evicted");
    }


    @Override
    @CacheEvict(value = "PoC-OPrtLobS", allEntries = true)
    public void flushCacheOPrtLobS() {
        log.info("PoC-OPrtLobS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtLobSListAll", allEntries = true)
    public void flushCacheOPrtLobSListAll() {
        log.info("PoC-OPrtLobSListAll evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtLobSList", allEntries = true)
    public void flushCacheOPrtLobSList() {
        log.info("PoC-OPrtLobSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtLobS2", allEntries = true)
    public void flushCacheOPrtLobS2() {
        log.info("PoC-OPrtLobS2 evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtLobS2ListAll", allEntries = true)
    public void flushCacheOPrtLobS2ListAll() {
        log.info("PoC-OPrtLobS2ListAll evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtLobS3", allEntries = true)
    public void flushCacheOPrtLobS3() {
        log.info("PoC-OPrtLobS3 evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtLobSListAll3", allEntries = true)
    public void flushCacheOPrtLobSListAll3() {
        log.info("PoC-OPrtLobSListAll3 evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtLobSList3", allEntries = true)
    public void flushCacheOPrtLobSList3() {
        log.info("PoC-OPrtLobSList3 evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtSecS", allEntries = true)
    public void flushCacheOPrtSecS() {
        log.info("PoC-OPrtSecS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtSecSListAll", allEntries = true)
    public void flushCacheOPrtSecSListAll() {
        log.info("PoC-OPrtSecSListAll evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPsfPsfS", allEntries = true)
    public void flushCacheOPsfPsfS() {
        log.info("PoC-OPsfPsfS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPsfPsfSListAll", allEntries = true)
    public void flushCacheOPsfPsfSListAll() {
        log.info("PoC-OPsfPsfSListAll evicted");
    }

    @Override
    @CacheEvict(value = "OArdFxaP", allEntries = true)
    public void flushCacheOArdFxaP() {
        log.info("OArdFxaP evicted");
    }

    @Override
    @CacheEvict(value = "OPrtLobCPT", allEntries = true)
    public void flushCacheOPrtLobCPT() {
        log.info("OPrtLobCPT evicted");
    }

    @Override
    @CacheEvict(value = "Etiquetas", allEntries = true)
    public void flushCacheEtiquetas() {
        log.info("Etiquetas evicted");
    }

    @Override
    @CacheEvict(value = "OCmuThlP", allEntries = true)
    public void flushCacheOCmuThlP() {
        log.info("OCmuThlP evicted");
    }

    @Override
    @CacheEvict(value = "ORcdTmdP", allEntries = true)
    public void flushCacheORcdTmdP() {
        log.info("ORcdTmdP evicted");
    }

    @Override
    @CacheEvict(value = "OTrnLstValS", allEntries = true)
    public void flushCacheOTrnLstValS() {
        log.info("OTrnLstValS evicted");
    }

    @Override
    @CacheEvict(value = "OCmnObjCPT", allEntries = true)
    public void flushCacheOCmnObjCPT() {
        log.info("OCmnObjCPT evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuCmpS", allEntries = true)
    public void flushCachePoCOCmuCmpS() {
        log.info("PoC-OCmuCmpS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuCmpS2", allEntries = true)
    public void flushCachePoCOCmuCmpS2() {
        log.info("PoC-OCmuCmpS2 evicted");
    }

    @Override
    @CacheEvict(value = "DescripcionError", allEntries = true)
    public void flushCacheDescripcionError() {
        log.info("DescripcionError evicted");
    }

    @Override
    @CacheEvict(value = "DescripcionesError", allEntries = true)
    public void flushCacheDescripcionesError() {
        log.info("DescripcionesError evicted");
    }

    @Override
    @CacheEvict(value = "Descripcion", allEntries = true)
    public void flushCacheDescripcion() {
        log.info("Descripcion evicted");
    }

    @Override
    @CacheEvict(value = "Descripciones", allEntries = true)
    public void flushCacheDescripciones() {
        log.info("Descripciones evicted");
    }

    @Override
    @CacheEvict(value = "DescripcionFld", allEntries = true)
    public void flushCacheDescripcionFld() {
        log.info("DescripcionFld evicted");
    }

    @Override
    @CacheEvict(value = "DescripcionesFld", allEntries = true)
    public void flushCacheDescripcionesFld() {
        log.info("DescripcionesFld evicted");
    }

    @Override
    @CacheEvict(value = "Error", allEntries = true)
    public void flushCacheError() {
        log.info("Error evicted");
    }

    @Override
    @CacheEvict(value = "Menu", allEntries = true)
    public void flushCacheMenu() {
        log.info("Menu evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-OLssLsiS", allEntries = true)
    public void flushCachePoCOLssLsiS() {
        log.info("PoC-OLssLsiS evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-OLssFliSDesc", allEntries = true)
    public void flushCacheOLssFliSDesc() {
        log.info("PoC-OLssFliSDesc evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-ORcpPmrS-A5020500", allEntries = true)
    public void flushCache_PoC_ORcpPmrS_A5020500() {
        log.info("PoC-ORcpPmrS-A5020500 evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-ORcpPmrS-A1000400", allEntries = true)
    public void flushCache_PoC_ORcpPmrS_A1000400() {
        log.info("PoC-ORcpPmrS-A1000400 evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-ORcpPmrS", allEntries = true)
    public void flushCachePoCORcpPmrS() {
        log.info("PoC-ORcpPmrS evicted");
    }


    @Override
    @CacheEvict(value = "PoC-SecList-OPrtSecS", allEntries = true)
    public void flushCachePoCSecListOPrtSecS() {
        log.info("PoC-SecList-OPrtSecS evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-OPrtSbs", allEntries = true)
    public void flushCachePoCOPrtSbs() {
        log.info("PoC-OPrtSbs evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtSbsS", allEntries = true)
    public void flushCachePoCOPrtSbsS() {
        log.info("PoC-OPrtSbsS evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-OCmnLngS", allEntries = true)
    public void flushCachePoCOCmnLngS() {
        log.info("PoC-OCmnLngS evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-OCmnTypSgetAllTypes", allEntries = true)
    public void flushCachePoCOCmnTypSgetAllTypes() {
        log.info("PoC-OCmnTypSgetAllTypes evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmnTypSgetType", allEntries = true)
    public void flushCachePoCOCmnTypSgetType() {
        log.info("PoC-OCmnTypSgetType evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-OCrnCrnSCurrency", allEntries = true)
    public void flushCachePoCOCrnCrnSCurrency() {
        log.info("PoC-OCrnCrnSCurrency evicted");
    }

    @Override
    @CacheEvict(value = "PoC-ORcdTsdS", allEntries = true)
    public void flushCachePoCORcdTsdS() {
        log.info("PoC-ORcdTsdS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-ORcdTsdS2", allEntries = true)
    public void flushCachePoCORcdTsdS2() {
        log.info("PoC-ORcdTsdS2 evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OTpdAgtS", allEntries = true)
    public void flushCachePoCOTpdAgtS() {
        log.info("PoC-OTpdAgtS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OTpdCsgS", allEntries = true)
    public void flushCachePoCOTpdCsgS() {
        log.info("PoC-OTpdCsgS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OTpdCsgSList", allEntries = true)
    public void flushCachePoCOTpdCsgSList() {
        log.info("PoC-OTpdCsgSList evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuSnlST", allEntries = true)
    public void flushCachePoCOCmuSnlST() {
        log.info("PoC-OCmuSnlST evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuFslST", allEntries = true)
    public void flushCachePoCOCmuFslST() {
        log.info("PoC-OCmuFslST evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuThlST", allEntries = true)
    public void flushCachePoCOCmuThlST() {
        log.info("PoC-OCmuThlST evicted");
    }
    @Override
    @CacheEvict(value = "PoC-OArdFxaSListAll", allEntries = true)
    public void flushCacheOArdFxaSListAll() {
        log.info("PoC-OArdFxaSListAll evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OAgnCpeNam", allEntries = true)
    public void flushCacheOAgnCpeNam() {
        log.info("PoC-OAgnCpeNam evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuSnlS", allEntries = true)
    public void flushCachePoCOCmuSnlS() {
        log.info("PoC-OCmuSnlS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuFslS", allEntries = true)
    public void flushCacheOCmuFslS() {
        log.info("PoC-OCmuFslS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OCmuThlSNam", allEntries = true)
    public void flushCacheOCmuThlSNam() {
        log.info("PoC-OCmuThlSNam evicted");
    }

    @Override
    @CacheEvict(value = "PoC-ODsrHdcS", allEntries = true)
    public void flushCacheODsrHdcS() {
        log.info("PoC-ODsrHdcS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtDelS", allEntries = true)
    public void flushCacheOPrtDelS() {
        log.info("PoC-OPrtDelS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPrtSblS", allEntries = true)
    public void flushCacheOPrtSblS() {
        log.info("PoC-OPrtSblS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OPidEnrS", allEntries = true)
    public void flushCacheOPidEnrS() {
        log.info("PoC-OPidEnrS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OTrnNtfS", allEntries = true)
    public void flushCacheOTrnNtfS() {
        log.info("PoC-OTrnNtfS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OprIdnNam", allEntries = true)
    public void flushCacheOprIdnNam() {
        log.info("PoC-OprIdnNam evicted");
    }

    @Override
    @CacheEvict(value = "PoC-HpnNam", allEntries = true)
    public void flushCacheHpnNam() {
        log.info("PoC-HpnNam evicted");
    }

    @Override
    @CacheEvict(value = "PoC-FilTypNam", allEntries = true)
    public void flushCacheFilTypNam() {
        log.info("PoC-FilTypNam evicted");
    }

    @Override
    @CacheEvict(value = "PoC-PcsNam", allEntries = true)
    public void flushCachePcsNam() {
        log.info("PoC-PcsNam evicted");
    }

    @Override
    @CacheEvict(value = "PoC-EncNam", allEntries = true)
    public void flushCacheEncVal() {
        log.info("PoC-EncNam evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-getMnrNam", allEntries = true)
    public void flusPocgetMnrNam() {
        log.info("PoC-getMnrNam evicted");
    }
    
    @Override
    @CacheEvict(value = "PoC-LsfFid", allEntries = true)
    public void flusCacheLsfFid() {
        log.info("PoC-LsfFid evicted");
    }

    @Override
    @CacheEvict(value = "PoC-OLsfRekS", allEntries = true)
    public void flushCacheOLsfRekS() {
        log.info("PoC-OLsfRekS evicted");
    }

    @Override
    @CacheEvict(value = "PoC-CtgNam", allEntries = true)
    public void flushCachePoCCtgNam() {
        log.info("PoC-CtgNam evicted");
    }

}
