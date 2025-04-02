package fun.pozzoo.quickwaystones.data;

import fun.pozzoo.quickwaystones.QuickWaystones;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class WaystoneData {
    private int id;
    private String name;
    private String owner;
    private Location location;
    private Vector facingDirection;

    public WaystoneData(Location location, String owner) {
        id = QuickWaystones.getAndIncrementLastWaystoneID();
        name = "Waystone " + id ;
        this.location = location;
        this.owner = owner;
        this.facingDirection = new Vector(0, 0, 0); // Default facing direction
    }
    public WaystoneData(String name, Location location, String owner) {
        id = QuickWaystones.getAndIncrementLastWaystoneID();
        this.name = name;
        this.location = location;
        this.owner = owner;
        this.facingDirection = new Vector(0, 0, 0); // Default facing direction
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }

    public Vector getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Vector facingDirection) {
        this.facingDirection = facingDirection;
    }

    @Override
    public String toString() {
        return "WaystoneData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", owner='" + owner + '\'' +
                ", facingDirection=" + facingDirection +
                '}';
    }
}

