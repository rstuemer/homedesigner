package ui.theme;

import java.net.URL;

public class Theme {

    private String mainViewFile;
    private String directory;
    private URL cssFile;
    private String name;


    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public URL getCssFile() {
        return cssFile;
    }

    public void setCssFile(URL cssFile) {
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


    //TODO LAyer System https://docs.oracle.com/javase/8/javafx/graphics-tutorial/canvas.htm#JFXGR214
}
