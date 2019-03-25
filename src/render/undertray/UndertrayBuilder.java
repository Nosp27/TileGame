package render.undertray;

import render.UI.UI_Panel;

public class UndertrayBuilder {
    private Undertray u = new Undertray();

    public UndertrayBuilder background(String path){
        u.background = new UI_Panel(path);
        u.background.plant(0,0);
        return this;
    }

    public Undertray build(){
        return u;
    }
}
