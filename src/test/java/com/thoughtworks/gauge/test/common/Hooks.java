package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeSuite;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;

public class Hooks {
    @AfterScenario
    public void tearDown() {
        File dir = GaugeProject.getCurrentProject().getProjectDir();
        if (getCurrentProject().getService() != null) {
            try {
                getCurrentProject().getService().getGaugeProcess().destroyForcibly().waitFor();
            } catch (InterruptedException e) {
                System.err.println(String.format("Unable to stop gauge process for %s; reason : %s", dir.getAbsolutePath(), e.getMessage()));
            }
        }
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            System.err.println(String.format("Could not delete project directory %s; reason : %s", dir.getAbsolutePath(), e.getMessage()));
        }
    }
}
