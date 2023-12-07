package com.mapfre.tron.yaml;

import java.io.File;
import java.util.List;

import com.mapfre.tron.yaml.bl.IBlExtract;
import com.mapfre.tron.yaml.bl.IBlWriter;
import com.mapfre.tron.yaml.bl.impl.BlExtractImpl;
import com.mapfre.tron.yaml.bl.impl.BlWriterImpl;
import com.mapfre.tron.yaml.model.SwaggerData;

import lombok.extern.slf4j.Slf4j;

/**
 * Application class.
 *
 * @author architecture - ${user}
 * @since 1.8
 * @version 28 feb 2022 9:21:00
 */
@Slf4j
public class App {

    private static final String HTML = ".html";

    public static void main(String[] args) {

        log.info("App.main: init execution");

        IBlExtract iBlExtract = new BlExtractImpl();
        IBlWriter iBlWriter = new BlWriterImpl();

        // Deleting files of the "out" folder
        File dirOut = new File("out");
        for (File file : dirOut.listFiles()) {
            if (!file.isDirectory() && !"swagger-ui.css".equals(file.getName()) && file.exists()) {
                    boolean isDeleted = file.delete();
                    if (!isDeleted) {
                        log.info(String.format("The file % can not be deleted", file.getName()));
                    }
            }
        }

        // Loading the YAML files from the "in" folder
        File dir = new File("in");
        String name = null;
        List<SwaggerData> datas = null;

        for (File file : dir.listFiles()) {
            name = getFileNameWithoutExtension(file);

            // Extracting the data
            datas = iBlExtract.extract(file);

            if (log.isDebugEnabled()) {
                for (SwaggerData myData : datas) {
                    log.debug("data: " + myData);
                }
            }

            // Writing the HTML
            log.info("Trying to write the file: " + name.concat(HTML));
            iBlWriter.givingWritingDataToFile(name.concat(HTML), datas);
            log.info("File[" + name.concat(HTML) + "] wrote");
        }

        log.info("App.main: end execution");
    }

    private static String getFileNameWithoutExtension(File file) {
        String fileName = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "");
            }
        } catch (Exception e) {
            log.error("Error replacing firtst: ", e);
            fileName = "";
        }

        return fileName;
    }

}
