package mechanics.quests;

import map.locations.Location;
import monsters.Monster;

import java.util.List;

public class MonsterQuest extends Quest {
    List<Monster> questMonsters;
    public List<Monster> getMonsters(){
        return questMonsters;
    }

    @Override
    public void setDestination(Location destination) {
        super.setDestination(destination);
        destination.setQuestMonsters(this);
    }
}
