package dev.melissamorgan.splatoonweapons.service;

import dev.melissamorgan.splatoonweapons.dao.WeaponDAO;
import dev.melissamorgan.splatoonweapons.entities.*;
import dev.melissamorgan.splatoonweapons.searchMethods.WeaponCategoryExclusionSearch;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponServiceImplementation implements WeaponService {

    @Autowired
    WeaponDAO weaponDao;

    // Model population methods
    @Override
    public List<Subweapon> populateSubweaponList() {
        return weaponDao.getAllSubweapons();
    }

    @Override
    public List<SpecialWeapon> populateSpecialWeaponList() {
        return weaponDao.getAllSpecialWeapons();
    }

    @Override
    public List<WeaponType> populateWeaponTypeList() {
        return weaponDao.getAllWeaponTypes();
    }

    @Override
    public List<Weight> populateWeightList() {
        return weaponDao.getAllWeights();
    }

    @Override
    public List<Weapon> populateAllWeapons() {
        return weaponDao.getAllWeapons();
    }


    // Object manipulation methods
    @Override
    @Transactional
    public void saveWeapon(Weapon newWeapon) {
        weaponDao.saveWeapon(newWeapon);
    }

    /**************************
    * Object retrieval methods
    * *************************/
    @Override
    public Subweapon getSubweaponById(Integer id) {
        return weaponDao.getSubweaponById(id);
    }

    @Override
    public SpecialWeapon getSpecialWeaponById(Integer id) {
        return weaponDao.getSpecialWeaponById(id);
    }

    @Override
    public WeaponType getWeaponTypeById(Integer id) {
        return weaponDao.getWeaponTypeById(id);
    }

    @Override
    public Weight getWeightById(Integer id) {
        return weaponDao.getWeightById(id);
    }

    @Override
    public Weapon getRandomWeapon(WeaponCategoryExclusionSearch exclusionSearch) {
        return weaponDao.getRandomWeapon(exclusionSearch);
    }
}
