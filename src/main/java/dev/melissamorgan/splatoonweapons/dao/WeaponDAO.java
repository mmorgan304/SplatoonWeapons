package dev.melissamorgan.splatoonweapons.dao;

import dev.melissamorgan.splatoonweapons.entities.*;
import dev.melissamorgan.splatoonweapons.searchMethods.WeaponCategoryExclusionSearch;

import java.util.List;

public interface WeaponDAO {
    //Model population methods
    List<Subweapon> getAllSubweapons();
    List<SpecialWeapon> getAllSpecialWeapons();
    List<WeaponType> getAllWeaponTypes();
    List<Weight> getAllWeights();
    List<Weapon> getAllWeapons();

    // Object manipulation method
    void saveWeapon(Weapon newWeapon);

    // Object retrieval methods
    Subweapon getSubweaponById(Integer id);
    SpecialWeapon getSpecialWeaponById(Integer id);
    WeaponType getWeaponTypeById(Integer id);
    Weight getWeightById(Integer id);

    Weapon getRandomWeapon(WeaponCategoryExclusionSearch exclusionSearch);
}
