package dev.melissamorgan.splatoonweapons.service;

import dev.melissamorgan.splatoonweapons.entities.*;

import java.util.List;

public interface WeaponService {
    // Model population methods
    List<Subweapon> populateSubweaponList();
    List<SpecialWeapon> populateSpecialWeaponList();
    List<WeaponType> populateWeaponTypeList();
    List<Weight> populateWeightList();
    List<Weapon> populateAllWeapons();

    // Object manipulation methods
    void saveWeapon(Weapon newWeapon);

    /************************* *
    * Object retrieval methods
    * *************************/
    Subweapon getSubweaponById(Integer id);
    SpecialWeapon getSpecialWeaponById(Integer id);
    WeaponType getWeaponTypeById(Integer id);
    Weight getWeightById(Integer id);

    Weapon getRandomWeapon();
}
