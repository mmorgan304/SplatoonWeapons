package dev.melissamorgan.splatoonweapons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subweapons", schema = "splatoon_3_weapons")
public class Subweapon {
    @Id
    @Column(name = "subweapon_id", nullable = false)
    private Integer id;

    @Column(name = "subweapon_name", length = 45)
    private String subweaponName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubweaponName() {
        return subweaponName;
    }

    public void setSubweaponName(String subweaponName) {
        this.subweaponName = subweaponName;
    }

}