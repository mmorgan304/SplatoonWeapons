package dev.melissamorgan.splatoonweapons.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "weapons", schema = "splatoon_3_weapons")
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weapon_id", nullable = false)
    private Integer id;

    @Column(name = "weapon_name", length = 45)
    private String weaponName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subweapon_id")
    private Subweapon subweapon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "special_weapon_id")
    private SpecialWeapon specialWeapon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id")
    private WeaponType weaponType;

    @Column(name = "matchmaking_range", precision = 10, scale = 1)
    private BigDecimal matchmakingRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_id")
    private Weight weight;

    @Column(name = "special_points")
    private Integer specialPoints;

    @Column(name = "range_actual")
    private Integer rangeActual;

    @Column(name = "damage_direct")
    private Integer damageDirect;

    @Column(name = "damage_indirect")
    private Integer damageIndirect;

    @Column(name = "fire_rate")
    private Integer fireRate;

    @Column(name = "secret_name", length = 50)
    private String secretName;

    @Column(name = "avg_paint_per_match")
    private Float avgPaintPerMatch;

    @Column(name = "max_damage")
    private Integer maxDamage;

    @Column(name = "min_damage")
    private Integer minDamage;

    @Column(name = "has_oneshot")
    private Boolean hasOneshot;

    public Boolean getHasOneshot() {
        return hasOneshot;
    }

    public void setHasOneshot(Boolean hasOneshot) {
        this.hasOneshot = hasOneshot;
    }

    public Integer getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(Integer minDamage) {
        this.minDamage = minDamage;
    }

    public Integer getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(Integer maxDamage) {
        this.maxDamage = maxDamage;
    }

    public Float getAvgPaintPerMatch() {
        return avgPaintPerMatch;
    }

    public void setAvgPaintPerMatch(Float avgPaintPerMatch) {
        this.avgPaintPerMatch = avgPaintPerMatch;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    public Integer getFireRate() {
        return fireRate;
    }

    public void setFireRate(Integer fireRate) {
        this.fireRate = fireRate;
    }

    public Integer getDamageIndirect() {
        return damageIndirect;
    }

    public void setDamageIndirect(Integer damageIndirect) {
        this.damageIndirect = damageIndirect;
    }

    public Integer getDamageDirect() {
        return damageDirect;
    }

    public void setDamageDirect(Integer damageDirect) {
        this.damageDirect = damageDirect;
    }

    public Integer getRangeActual() {
        return rangeActual;
    }

    public void setRangeActual(Integer rangeActual) {
        this.rangeActual = rangeActual;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public Subweapon getSubweapon() {
        return subweapon;
    }

    public void setSubweapon(Subweapon subweapon) {
        this.subweapon = subweapon;
    }

    public SpecialWeapon getSpecialWeapon() {
        return specialWeapon;
    }

    public void setSpecialWeapon(SpecialWeapon specialWeapon) {
        this.specialWeapon = specialWeapon;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType classField) {
        this.weaponType = classField;
    }

    public BigDecimal getMatchmakingRange() {
        return matchmakingRange;
    }

    public void setMatchmakingRange(BigDecimal matchmakingRange) {
        this.matchmakingRange = matchmakingRange;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public Integer getSpecialPoints() {
        return specialPoints;
    }

    public void setSpecialPoints(Integer specialPoints) {
        this.specialPoints = specialPoints;
    }

    @Override
    public String toString() {
        return "{" + weaponName + "}";
    }
}