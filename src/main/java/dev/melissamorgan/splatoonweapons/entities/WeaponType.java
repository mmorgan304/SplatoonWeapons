package dev.melissamorgan.splatoonweapons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "weapon_type", schema = "splatoon_3_weapons")
public class WeaponType {
    @Id
    @Column(name = "weapon_type_id", nullable = false)
    private Integer id;

    @Column(name = "weapon_type_name", length = 45)
    private String weaponTypeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeaponTypeName() {
        return weaponTypeName;
    }

    public void setWeaponTypeName(String weaponTypeName) {
        this.weaponTypeName = weaponTypeName;
    }

}