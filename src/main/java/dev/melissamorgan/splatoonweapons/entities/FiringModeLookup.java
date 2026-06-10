package dev.melissamorgan.splatoonweapons.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "firing_mode_lookup")
public class FiringModeLookup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "firing_mode_name", nullable = false, length = 50)
    private String firingModeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFiringModeName() {
        return firingModeName;
    }

    public void setFiringModeName(String firingModeName) {
        this.firingModeName = firingModeName;
    }

}