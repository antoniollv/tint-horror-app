package com.mapfre.tron.api.cmn.sfmc.bl;

/**
 * The send comunications to SFCMC online process service interface.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 4 jun. 2021 - 13:02:16
 *
 */
public interface IBlSFMCProcessService {

    /**
     * Send communications to SFMC online process.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     */
    void sendToSFMConlineProcess(Integer cmpVal, String lnvVal);

    /**
     * Send communications to SFMC batch process.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     */
    void sendToSFMCbatchProcess(Integer cmpVal, String lnvVal);

}
