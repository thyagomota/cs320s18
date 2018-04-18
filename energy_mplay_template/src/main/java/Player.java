import com.gigaspaces.annotation.pojo.SpaceId;

import java.awt.*;
import java.util.Random;

class Player {

    // note that all fields must be defined using classes (no primitive types allowed)
    private Integer x;
    private Integer y;
    private Integer worldWidth;
    private Integer worldHeight;
    private String  name;
    private Integer size;
    private Integer direction;
    private Boolean alive;
    private Color   color;
    private Integer energy;

    public static final int NONE      = 0;
    public static final int NORTH     = 1;
    public static final int NORTHEAST = 2;
    public static final int EAST      = 3;
    public static final int SOUTHEAST = 4;
    public static final int SOUTH     = 5;
    public static final int SOUTHWEST = 6;
    public static final int WEST      = 7;
    public static final int NORTHWEST = 8;

    public static final int INITIAL_SIZE   = 25;
    public static final int INITIAL_ENERGY = 100;
    public static final int MAXIMUM_ENERGY = 1000;
    public static final int SPEED          = 5;

    // note that you must define a parameterless constructor
    Player() {

    }

    Player(String name, int worldWidth, int worldHeight) {
        this.name = name;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        Random r = new Random();
        this.x = r.nextInt(worldWidth);
        this.y = r.nextInt(worldHeight);
        this.color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        this.size = INITIAL_SIZE;
        this.direction = r.nextInt(8);
        this.alive = true;
        this.energy = INITIAL_ENERGY;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    // TODO: add the @SpaceId annotation to define the field that identifies the object at the space
    @SpaceId
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Integer getWorldWidth() {
        return worldWidth;
    }

    public void setWorldWidth(Integer worldWidth) {
        this.worldWidth = worldWidth;
    }

    public Integer getWorldHeight() {
        return worldHeight;
    }

    public void setWorldHeight(Integer worldHeight) {
        this.worldHeight = worldHeight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public void switchDirection() {
        direction = (direction + 1) % 9;
    }

    public void move() {
        // TODO: if player is not moving, increase its energy by 1 but only up to MAXIMUM_ENERGY; if player is moving but its energy level reached 0, have the player stop; else, decrease the energy level by 1, not allowing it to become negative
        switch (direction) {
            case NORTH:
                y -= SPEED;
                if (y < 0)
                    y = worldHeight - 1;
                break;
            case NORTHEAST:
                y -= SPEED;
                if (y < 0)
                    y = worldHeight - 1;
                x += SPEED;
                if (x >= worldWidth)
                    x = 0;
                break;
            case EAST:
                x += SPEED;
                if (x >= worldWidth)
                    x = 0;
                break;
            case SOUTHEAST:
                x += SPEED;
                if (x >= worldWidth)
                    x = 0;
                y += SPEED;
                if (y >= worldHeight)
                    y = 0;
                break;
            case SOUTH:
                y += SPEED;
                if (y >= worldHeight)
                    y = 0;
                break;
            case SOUTHWEST:
                y += SPEED;
                if (y >= worldHeight)
                    y = 0;
                x -= SPEED;
                if (x < 0)
                    x = worldWidth;
                break;
            case WEST:
                x -= SPEED;
                if (x < 0)
                    x = worldWidth;
                break;
            case NORTHWEST:
                x -= SPEED;
                if (x < 0)
                    x = worldWidth;
                y -= SPEED;
                if (y < 0)
                    y = worldHeight - 1;
                break;
        }
    }

    // TODO: implement collision detection
    public boolean isColliding(Player other) {
        return false;
    }

    @Override
    public String toString() {
        return name + "[" + energy + "]";
    }
}