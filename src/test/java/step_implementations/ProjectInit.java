package step_implementations;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import common.GaugeProject;
import common.Util;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static common.Util.getTempDir;
import static org.junit.Assert.fail;

public class ProjectInit {
    private GaugeProject currentProject = null;

    @Step("In an empty directory initialize a <language> project")
    public void initializeProjectWithLanguage(String language) throws Exception {
        currentProject = GaugeProject.createProject(getTempDir(), language);
        currentProject.initialize();
    }

    @Step({"In an empty directory initialize a project with the current language"})
    public void initializeProject() throws Exception {
        String currentLanguage = Util.getCurrentLanguage();
        initializeProjectWithLanguage(currentLanguage);
    }

    @Step("Project is initialized without example spec")
    public void projectInitWithoutHelloWorldSpec() throws Exception {
        initializeProject();
        currentProject.deleteSpec("hello_world");
    }

    @Step("The following file structure should be created <table>")
    public void ensureInitCreatesSpecifiedStructure(Table table) throws Exception {
        ArrayList<String> failures = new ArrayList<String>();
        for (TableRow row : table.getTableRows()) {
            String fileName = row.getCell("name");
            String fileType = row.getCell("type");
            if (fileType.equalsIgnoreCase("dir")) {
                if (!Util.isDirectoryExists(getPathRelativeToCurrentProjectDir(fileName))) {
                    failures.add(fileName + " is not a valid directory");
                }
            } else if (fileType.equalsIgnoreCase("file")) {
                if (!Util.isFileExists(getPathRelativeToCurrentProjectDir(fileName))) {
                    failures.add(fileName + " is not a valid file");
                }
            } else {
                fail(fileType + " is invalid");
            }
        }

        if (failures.size() > 0) {
            String consolidatedMessage = "";
            for (String failure : failures) {
                consolidatedMessage += failure + "\n";
            }
            fail("Project initialization failed to create required project structure.\n\n" + consolidatedMessage);
        }
    }

    @Step("Verify language specific files are created")
    public void verifyFilesForLanguageIsCreated() {
        ArrayList<String> failures = new ArrayList<String>();
        Map<String, String> files = currentProject.getLanguageSpecificFiles();
        for (String filePath : files.keySet()) {
            String fileType = files.get(filePath);
            if (fileType.equalsIgnoreCase("dir")) {
                if (!Util.isDirectoryExists(getPathRelativeToCurrentProjectDir(filePath))) {
                    failures.add(filePath + " is not a valid directory");
                }
            } else if (fileType.equalsIgnoreCase("file")) {
                if (!Util.isFileExists(getPathRelativeToCurrentProjectDir(filePath))) {
                    failures.add(filePath + " is not a valid file");
                }
            } else {
                fail(fileType + " is invalid");
            }
        }
        if (failures.size() > 0) {
            String consolidatedMessage = "";
            for (String failure : failures) {
                consolidatedMessage += failure + "\n";
            }
            fail("Project initialization failed to create required project structure.\n\n" + consolidatedMessage);
        }
    }

    private String getPathRelativeToCurrentProjectDir(String path) {
        return Util.combinePath(currentProject.getProjectDir().getAbsolutePath(), path);
    }

    @AfterScenario
    public void clearProjectDir() throws IOException {
        if (currentProject.getProjectDir().exists()) {
            FileUtils.deleteQuietly(currentProject.getProjectDir());
        }
    }

}
