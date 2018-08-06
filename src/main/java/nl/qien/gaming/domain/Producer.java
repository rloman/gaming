package nl.qien.gaming.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Producer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String kvkNumber;
    private String btwNumber;


    @OneToMany(mappedBy = "producer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Boardgame> games = new HashSet<>();

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKvkNumber() {
        return kvkNumber;
    }

    public void setKvkNumber(String kvkNumber) {
        this.kvkNumber = kvkNumber;
    }

    public String getBtwNumber() {
        return btwNumber;
    }

    public void setBtwNumber(String btwNumber) {
        this.btwNumber = btwNumber;
    }
}
