package dev.melissamorgan.splatoonweapons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "special_weapons", schema = "splatoon_3_weapons")
public class SpecialWeapon {
    @Id
    @Column(name = "special_weapon_id", nullable = false)
    private Integer id;

    @Column(name = "special_weapon_name", length = 45)
    private String specialWeaponName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecialWeaponName() {
        return specialWeaponName;
    }

    public void setSpecialWeaponName(String specialWeaponName) {
        this.specialWeaponName = specialWeaponName;
    }

}