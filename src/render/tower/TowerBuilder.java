package render.tower;

import render.UI.UI_Button;
import render.UI.UI_Panel;

public class TowerBuilder {
    private TowerPanel tower;

    private void init(){
        if(tower == null)
            tower = new TowerPanel();
    }

    public TowerBuilder background(String backPath){
        init();
        UI_Panel panel = new UI_Panel(backPath);
        panel.plant(0,0);
        tower.background = panel;
        return this;
    }

    public TowerBuilder addButton(UI_Button button){
        init();
        tower.upgradeButtons.add(button);
        //TODO: action

        return this;
    }

    public TowerPanel build(){
        return tower;
    }
}
