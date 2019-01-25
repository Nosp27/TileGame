import map.MapGenerator;
import render.GameFrame;

public class GameController {
    public static void main(String[] args) {
        MapGenerator mg = new MapGenerator();

        new GameFrame(mg);
    }
}
