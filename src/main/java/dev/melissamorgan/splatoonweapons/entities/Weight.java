package dev.melissamorgan.splatoonweapons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "weight", schema = "splatoon_3_weapons")
public class Weight {
    @Id
    @Column(name = "weight_id", nullable = false)
    private Integer id;

    @Column(name = "weight_name", length = 45)
    private String weightName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

}