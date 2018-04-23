import com.gigaspaces.annotation.pojo.SpaceId;

import java.io.Serializable;

public class Player implements Serializable {

    private String  name;
    private Integer points;
    private Integer round;
    private Boolean first;

    Player() {

    }

    Player(String name) {
        this.name = name;
        points = 0;
        round = 0;
        first = false;
    }

    @SpaceId
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", round=" + round +
                ", first=" + first +
                '}';
    }
}
