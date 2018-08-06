package nl.qien.gaming.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Boardgame implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String name;
    private int maxPlayers;
    private boolean hasDice;

    @ManyToOne
    private Producer producer;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public boolean isHasDice() {
        return hasDice;
    }

    public void setHasDice(boolean hasDice) {
        this.hasDice = hasDice;
    }
}
