package dev.melissamorgan.splatoonweapons.searchMethods;

import java.util.List;

public class WeaponCategoryExclusionSearch {
    private boolean isDuplicate;
    private List<Integer> weaponTypeIds;
    private List<Integer> subweaponIds;
    private List<Integer> specialWeaponIds;
    private List<Integer> weightClassIds;

    public WeaponCategoryExclusionSearch() {
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }

    public List<Integer> getWeaponTypeIds() {
        return weaponTypeIds;
    }

    public void setWeaponTypeIds(List<Integer> weaponTypeIds) {
        this.weaponTypeIds = weaponTypeIds;
    }

    public List<Integer> getSubweaponIds() {
        return subweaponIds;
    }

    public void setSubweaponIds(List<Integer> subweaponIds) {
        this.subweaponIds = subweaponIds;
    }

    public List<Integer> getSpecialWeaponIds() {
        return specialWeaponIds;
    }

    public void setSpecialWeaponIds(List<Integer> specialWeaponIds) {
        this.specialWeaponIds = specialWeaponIds;
    }

    public List<Integer> getWeightClassIds() {
        return weightClassIds;
    }

    public void setWeightClassIds(List<Integer> weightClassIds) {
        this.weightClassIds = weightClassIds;
    }

    @Override
    public String toString() {
        return "WeaponCategoryExclusionSearch{" +
                "isDuplicate=" + isDuplicate +
                ", weaponTypeIds=" + weaponTypeIds +
                ", subweaponIds=" + subweaponIds +
                ", specialWeaponIds=" + specialWeaponIds +
                ", weightClassIds=" + weightClassIds +
                '}';
    }
}
