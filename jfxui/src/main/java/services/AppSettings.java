package services;

import ui.theme.Theme;

import java.net.URI;
import java.net.URL;
import java.util.Locale;

public class AppSettings implements Settings {


    private Locale local;
    private Theme theme;


    public AppSettings() {
        local = new Locale("de");
        theme = new Theme();
        theme.setMainViewFile("../mainView.html");
        //URL path = getClass().getResource("../styles/dark.css");
        //theme.setCssFile(path);
        theme.setName("DEFAULT DARK");
    }


    public Locale getLocal() {
        return local;
    }

    public void setLocal(Locale local) {
        this.local = local;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
