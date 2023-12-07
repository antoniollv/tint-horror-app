
package com.mapfre.tron.yaml.bl;

import java.util.List;

import com.mapfre.tron.yaml.model.SwaggerData;

/**
 * Write a text file.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 28 Feb 2022 - 15:09:27
 */
public interface IBlWriter {

    /**
     * Write a text file from the giving data.
     *
     * @param filename -> The name of the file
     * @param data     -> The data to write
     */
    void givingWritingDataToFile(String filename, List<SwaggerData> data);

}
