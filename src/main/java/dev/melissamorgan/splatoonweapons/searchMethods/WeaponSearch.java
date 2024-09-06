package dev.melissamorgan.splatoonweapons.searchMethods;

import java.util.List;

public class WeaponSearch {
    private boolean isDuplicate;
    private List<Integer> weaponTypeIds;
    private List<Integer> subweaponIds;
    private List<Integer> specialWeaponIds;
    private List<Integer> weightClassIds;
    private List<Integer> weaponIds;
    private boolean isInclusionSearch;

    public WeaponSearch() {}

    @Override
    public String toString() {
        return "WeaponSearch{" +
                "isDuplicate=" + isDuplicate +
                ", weaponTypeIds=" + weaponTypeIds +
                ", subweaponIds=" + subweaponIds +
                ", specialWeaponIds=" + specialWeaponIds +
                ", weightClassIds=" + weightClassIds +
                ", weaponIds=" + weaponIds +
                ", isInclusionSearch=" + isInclusionSearch +
                '}';
    }

    public boolean isInclusionSearch() {
        return isInclusionSearch;
    }

    public void setInclusionSearch(boolean inclusionSearch) {
        isInclusionSearch = inclusionSearch;
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

    public List<Integer> getWeaponIds() {
        return weaponIds;
    }

    public void setWeaponIds(List<Integer> weaponIds) {
        this.weaponIds = weaponIds;
    }
}
