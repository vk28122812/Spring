package io.datajek.database_relationships.onetomany.bi;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class PlayerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String twitter;

    @OneToOne(mappedBy = "playerProfile", cascade = CascadeType.ALL)
    @JsonBackReference
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerProfile(int id, String twitter) {
        this.id = id;
        this.twitter = twitter;
    }

    public PlayerProfile(String twitter) {
        this.twitter = twitter;
    }

    public PlayerProfile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public String toString() {
        return "PlayerProfile[" +
                "id=" + id +
                ", twitter='" + twitter + '\'' +
                ", player=" + player +
                ']';
    }
}
