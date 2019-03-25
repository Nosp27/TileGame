package render.UI;

public class UI_Factory {
    public UI_Panel createHeroPanel(){
        UI_Panel hp = new UI_Panel("res/UI/hero_labels/panel.png");
        UI_Button returnHome = new UI_Button("res/UI/hero_labels/return_btn_idle.png", "res/UI/hero_labels/return_btn_pressed.png");
        hp.addChild(returnHome);
        return hp;
    }
}
