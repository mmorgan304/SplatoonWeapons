package dev.melissamorgan.splatoonweapons.controllers;

import dev.melissamorgan.splatoonweapons.entities.Weapon;
import dev.melissamorgan.splatoonweapons.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("weaponList", weaponService.populateAllWeapons());
    }

    @GetMapping("/addWeapon")
    public String addWeapon(Model model) {
        Weapon weapon = new Weapon();
        model.addAttribute("newWeapon", weapon);
        return "adminPages/addWeapon";
    }

    @PostMapping("/processWeapon")
    public String processWeapon(
            @ModelAttribute("newWeapon") Weapon weapon
    ) {
        weapon.setWeaponType(weaponService.getWeaponTypeById(weapon.getWeaponType().getId()));
        weapon.setSubweapon(weaponService.getSubweaponById(weapon.getSubweapon().getId()));
        weapon.setSpecialWeapon(weaponService.getSpecialWeaponById(weapon.getSpecialWeapon().getId()));
        weapon.setWeight(weaponService.getWeightById(weapon.getWeight().getId()));
        weaponService.saveWeapon(weapon);
        return "redirect:weaponList";
    }

    @GetMapping("/weaponList")
    public String weaponList() {
        return "adminPages/weaponList";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        return "test";
    }
}
