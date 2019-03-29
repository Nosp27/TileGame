package map.locations;

import monsters.Monster;

public interface LocationListener {
    void onDiscover(Location l);

    void onMonsterGot(Location l, Monster m);
}
