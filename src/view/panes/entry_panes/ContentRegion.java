package view.panes.entry_panes;

import javafx.scene.layout.Region;

public abstract class ContentRegion extends Region{

    public abstract String getContent();

    public abstract void setContent(String content);
}
