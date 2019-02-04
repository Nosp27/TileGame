import heroes.Hero;
import map.MapGenerator;
import render.GameFrame;

import java.util.Scanner;

public class GameController {
    public static void main(String[] args) {
        MapGenerator mg = new MapGenerator();

        mg.generateMap(15);
        Hero h = mg.addHero();
        GameFrame gf = new GameFrame(mg);
        gf.mainPanel.startHero = h::start;

        h.giveOrder("seek");
        Scanner sc = new Scanner(System.in);
        while (true){
            if(sc.hasNext())
                h.giveOrder(sc.next());
        }
    }
}
