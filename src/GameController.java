import heroes.Hero;
import map.MapGenerator;
import mechanics.Logger;
import render.GameFrame;

import java.util.Scanner;

public class GameController {
    public static void main(String[] args) {
        MapGenerator mg = new MapGenerator();

        mg.generateMap(15);
        Hero h = mg.addHero();
        Hero hh = mg.addHero();
        GameFrame gf = new GameFrame(mg);
        gf.mainPanel.startHero = () -> {
            h.start();
            hh.start();
        };

        h.giveOrder("seek");
        hh.giveOrder("seek");
        Scanner sc = new Scanner(System.in);
        while (true) {
            if (sc.hasNextInt()) {
                int I = sc.nextInt();
                if(sc.hasNext())
                switch (I) {
                    case 0:
                        h.giveOrder(sc.next());
                        break;
                    case 1:
                        hh.giveOrder(sc.next());
                        default: break;
                }
            }
        }
    }
}
