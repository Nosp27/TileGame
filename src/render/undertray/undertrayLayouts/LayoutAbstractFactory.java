package render.undertray.undertrayLayouts;

import heroes.Hero;
import map.locations.Location;
import render.undertray.Undertray;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

public abstract class LayoutAbstractFactory {
    private static UndertrayLayout initBaseLayout(Undertray u) {
        return new UndertrayLayout() {
            @Override
            public void selectLocation(Location l) {
                throw new RuntimeException();
            }

            @Override
            public void selectHero(Hero h) {
                throw new RuntimeException();
            }

            @Override
            public void draw(Graphics g) {

            }
        };
    }

    ;

    public static void selectLocation(Location l, Undertray u) {
        executeCommonAction(LayoutType.LOCATION, u, l);
    }

    public static void selectHero(Hero h, Undertray u) {
        executeCommonAction(LayoutType.HERO, u, h);
    }

    private static void executeCommonAction(LayoutType lt, Undertray u, Object x) {
        while (true)
            try {
                switch (lt) {
                    case LOCATION:
                        u.layout.selectLocation((Location) x);
                        break;
                    case HERO:
                        //TODO: hero layout
                        break;
                }

                return;
            } catch (NullPointerException e) {
                u.layout = initBaseLayout(u);
            } catch (RuntimeException e) {
                u.layout = getSufficientUT(lt);
            }
    }

    private static UndertrayLayout getSufficientUT(LayoutType lt) {
        switch (lt) {
            case LOCATION:
                return new LocationLayout();
            default:
                throw new NotImplementedException();//TODO: hero layout
        }
    }

    private enum LayoutType {
        LOCATION,
        HERO
    }
}
