package dev.melissamorgan.splatoonweapons.service;

import dev.melissamorgan.splatoonweapons.Mode;
import dev.melissamorgan.splatoonweapons.Stage;
import dev.melissamorgan.splatoonweapons.entities.*;
import dev.melissamorgan.splatoonweapons.entities.weaponFeatures.*;
import dev.melissamorgan.splatoonweapons.searchMethods.WeaponSearch;

import java.util.List;

public interface WeaponService {
    // Model population methods
    List<Subweapon> populateSubweaponList();
    List<SpecialWeapon> populateSpecialWeaponList();
    List<WeaponType> populateWeaponTypeList();
    List<Weight> populateWeightList();
    List<Weapon> populateAllWeapons();
    List<FiringModeLookup> populateFiringModes();
    List<Stage> populateStageList();
    List<Mode> populateModeList();

    // Object manipulation methods
    void saveWeapon(Weapon newWeapon);

    /************************* *
    * Object retrieval methods
    * *************************/
    Subweapon getSubweaponById(Integer id);
    SpecialWeapon getSpecialWeaponById(Integer id);
    WeaponType getWeaponTypeById(Integer id);
    Weight getWeightById(Integer id);
    Weapon getWeaponById(Integer id);
    Weapon getWeaponBySecretName(String secretName);

    Weapon getRandomWeapon(WeaponSearch inclusionSearch);


}
