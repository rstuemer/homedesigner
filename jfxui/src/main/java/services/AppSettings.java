package services;

import ui.theme.Theme;

import java.util.Locale;

public class AppSettings implements Settings {



   private  Locale local ;
   private Theme theme;




    public AppSettings (){
        local = new Locale("de");
        theme = new Theme();
        theme.setMainViewFile("../mainView.html");
        theme.setCssFile(getClass().getResource("../styles/dark.css").getPath());
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
