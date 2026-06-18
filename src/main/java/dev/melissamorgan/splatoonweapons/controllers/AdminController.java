package dev.melissamorgan.splatoonweapons.controllers;

import dev.melissamorgan.splatoonweapons.entities.weaponFeatures.Weapon;
import dev.melissamorgan.splatoonweapons.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private WeaponService weaponService;

    @ModelAttribute
    public void addWeaponAttributes(Model model) {
        model.addAttribute("weaponTypeList", weaponService.populateWeaponTypeList());
        model.addAttribute("subweaponsList", weaponService.populateSubweaponList());
        model.addAttribute("specialWeaponsList", weaponService.populateSpecialWeaponList());
        model.addAttribute("weightList", weaponService.populateWeightList());
        model.addAttribute("firingModeList", weaponService.populateFiringModes());
        model.addAttribute("weaponList", weaponService.populateAllWeapons());
    }

    @RequestMapping("/home")
    public String home(Model model) {
        return "adminPages/home";
    }

    @GetMapping("/addweapon")
    public String addWeapon(Model model) {
        Weapon weapon = new Weapon();
        model.addAttribute("newWeapon", weapon);
        return "adminPages/addweapon";
    }

    @GetMapping("/modifyweapon/{id}")
    public String modWeapon(@PathVariable Integer id, Model model) {
        Weapon weapon = weaponService.getWeaponById(id);
        model.addAttribute("newWeapon", weapon);
        return "adminPages/addweapon";
    }

    @PostMapping("/processweapon")
    public String processWeapon(
            @ModelAttribute("newWeapon") Weapon weapon
    ) {
        weapon.setWeaponType(weaponService.getWeaponTypeById(weapon.getWeaponType().getId()));
        weapon.setSubweapon(weaponService.getSubweaponById(weapon.getSubweapon().getId()));
        weapon.setSpecialWeapon(weaponService.getSpecialWeaponById(weapon.getSpecialWeapon().getId()));
        weapon.setWeight(weaponService.getWeightById(weapon.getWeight().getId()));
        
        weaponService.saveWeapon(weapon);
        return "redirect:weaponlist";
    }

    @GetMapping("/weaponlist")
    public String weaponList() {
        return "adminPages/weaponlist";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        return "test";
    }
}
