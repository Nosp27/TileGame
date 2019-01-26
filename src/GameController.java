import map.MapGenerator;
import render.GameFrame;

public class GameController {
    public static void main(String[] args) {
        MapGenerator mg = new MapGenerator();

        mg.generateMap(15);

        new GameFrame(mg);
    }
}
