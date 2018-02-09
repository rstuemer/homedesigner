package ui.theme;

public class Theme {

    private String mainViewFile;
    private String directory;
    private String cssFile;
    private String name;


    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getCssFile() {
        return cssFile;
    }

    public void setCssFile(String cssFile) {
        this.cssFile = cssFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainViewFile() {
        return mainViewFile;
    }

    public void setMainViewFile(String mainViewFile) {
        this.mainViewFile = mainViewFile;
    }
}
