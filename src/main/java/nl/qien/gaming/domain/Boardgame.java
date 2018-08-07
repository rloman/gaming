package nl.qien.gaming.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @JsonIgnoreProperties("playedGames")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Player> players = new HashSet<>();

    public void addPlayer(Player p){
        this.players.add(p);
        p.getPlayedGames().add(this);
    }

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

	public Set<Player> getPlayers() {
        return players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Boardgame boardgame = (Boardgame) o;

        return id == boardgame.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
