package modules;

import UIController.AppState;
import com.google.inject.AbstractModule;
import services.FXMLFileManager;
import services.FXMLFileManagerImpl;

public class GuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FXMLFileManager.class).to(FXMLFileManagerImpl.class);


    }
}
