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

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "next_freshness_up")
    private Integer nextFreshnessUp;

    @Column(name = "wins")
    private Integer wins;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subweapon_id")
    private Subweapon subweapon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "special_weapon_id")
    private SpecialWeapon specialWeapon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private WeaponType weaponType;

    @Column(name = "matchmaking_range", precision = 10, scale = 1)
    private BigDecimal matchmakingRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_id")
    private Weight weight;

    @Column(name = "special_points")
    private Integer specialPoints;

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

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getNextFreshnessUp() {
        return nextFreshnessUp;
    }

    public void setNextFreshnessUp(Integer nextFreshnessUp) {
        this.nextFreshnessUp = nextFreshnessUp;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
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