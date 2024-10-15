package dev.melissamorgan.splatoonweapons.controllers;

import dev.melissamorgan.splatoonweapons.entities.Weapon;
import dev.melissamorgan.splatoonweapons.searchMethods.WeaponSearch;
import dev.melissamorgan.splatoonweapons.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    WeaponService weaponService;

    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("weaponTypeList", weaponService.populateWeaponTypeList());
        model.addAttribute("subweaponsList", weaponService.populateSubweaponList());
        model.addAttribute("specialWeaponsList", weaponService.populateSpecialWeaponList());
        model.addAttribute("weightList", weaponService.populateWeightList());
        model.addAttribute("weaponList", weaponService.populateAllWeapons());
    }

    @GetMapping("/randomizer")
    public String randomizer(Model model) {
        Weapon randomWeapon = weaponService.getRandomWeapon(new WeaponSearch());
        model.addAttribute("randomWeapon", randomWeapon);
        return "publicPages/randomizer";
    }

    @GetMapping("/login")
    public String login() {
        return "publicPages/login";
    }

    @GetMapping("/generateRandomWeapon")
    @ResponseBody
    public Weapon generateRandomWeapon(
            @RequestParam(required = false) List<Integer> weaponTypeIds,
            @RequestParam(required = false) List<Integer> subweaponIds,
            @RequestParam(required = false) List<Integer> specialWeaponIds,
            @RequestParam(required = false) List<Integer> weightClassIds,
            @RequestParam(required = false) boolean isDuplicate,
            @RequestParam(required = false) List<Integer> weaponIds,
            @RequestParam boolean isInclusionSearch
    ) {
        WeaponSearch weaponSearch = new WeaponSearch();
        weaponSearch.setWeaponTypeIds(weaponTypeIds);
        weaponSearch.setSubweaponIds(subweaponIds);
        weaponSearch.setSpecialWeaponIds(specialWeaponIds);
        weaponSearch.setWeightClassIds(weightClassIds);
        weaponSearch.setDuplicate(isDuplicate);
        weaponSearch.setWeaponIds(weaponIds);
        weaponSearch.setInclusionSearch(isInclusionSearch);

        return weaponService.getRandomWeapon(weaponSearch);
    }

    @GetMapping("/devnotes")
    public String devNotes() {
        return "publicPages/devnotes";
    }

}
