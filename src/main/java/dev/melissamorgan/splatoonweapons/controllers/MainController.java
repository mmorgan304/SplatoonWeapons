package dev.melissamorgan.splatoonweapons.controllers;

import dev.melissamorgan.splatoonweapons.entities.Weapon;
import dev.melissamorgan.splatoonweapons.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    WeaponService weaponService;

    @GetMapping("/randomizer")
    public String randomizer(Model model) {
        Weapon randomWeapon = weaponService.getRandomWeapon();
        model.addAttribute("randomWeapon", randomWeapon);
        return "publicPages/randomizer";
    }
}
