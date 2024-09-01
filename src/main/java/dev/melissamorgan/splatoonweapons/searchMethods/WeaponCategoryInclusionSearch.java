package dev.melissamorgan.splatoonweapons.searchMethods;

import dev.melissamorgan.splatoonweapons.entities.SpecialWeapon;
import dev.melissamorgan.splatoonweapons.entities.Subweapon;
import dev.melissamorgan.splatoonweapons.entities.WeaponType;

import java.util.List;

public class WeaponCategoryInclusionSearch {
    private boolean isDuplicate;
    private List<WeaponType> weaponTypes;
    private List<Subweapon> subweapons;
    private List<SpecialWeapon> specialWeapons;

    public WeaponCategoryInclusionSearch() {
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }

    public List<WeaponType> getWeaponTypes() {
        return weaponTypes;
    }

    public void setWeaponTypes(List<WeaponType> weaponTypes) {
        this.weaponTypes = weaponTypes;
    }

    public List<Subweapon> getSubweapons() {
        return subweapons;
    }

    public void setSubweapons(List<Subweapon> subweapons) {
        this.subweapons = subweapons;
    }

    public List<SpecialWeapon> getSpecialWeapons() {
        return specialWeapons;
    }

    public void setSpecialWeapons(List<SpecialWeapon> specialWeapons) {
        this.specialWeapons = specialWeapons;
    }
}
