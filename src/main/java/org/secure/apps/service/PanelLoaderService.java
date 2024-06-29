package org.secure.apps.service;

import org.secure.apps.ui.CreateNewFilePanel;
import org.secure.apps.ui.LoadExistingFilePanel;
import org.secure.apps.ui.MainPagePanel;
import org.secure.apps.utils.PanelServiceUtils;

public class PanelLoaderService {

    private static final PanelLoaderService INSTANCE = new PanelLoaderService();

    private PanelLoaderService() {}

    public static PanelLoaderService getInstance() {
        return INSTANCE;
    }

    public void load() {
        PanelServiceUtils.addPanel("createNewFilePanel", new CreateNewFilePanel());
        PanelServiceUtils.addPanel("loadExistingFilePanel", new LoadExistingFilePanel());
        PanelServiceUtils.addPanel("mainPagePanel", new MainPagePanel());
    }
}
