package dev.melissamorgan.splatoonweapons.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "inkfish")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "color_chip", length = 45)
    private String colorChip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorChip() {
        return colorChip;
    }

    public void setColorChip(String colorChip) {
        this.colorChip = colorChip;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", colorChip='" + colorChip + '\'' +
                '}';
    }
}