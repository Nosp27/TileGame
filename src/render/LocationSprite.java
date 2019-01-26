package render;

import map.locations.Location;

public class LocationSprite extends Sprite{
    private Location location;

    public Location getLocation() {
        return location;
    }

    public LocationSprite(Location _location, String imagePath){
        super(imagePath, -100);
        location = _location;
    }

    @Override
    void onClick(){
        System.out.println("tile "+ location.toString() + " clicked");
    }
}
