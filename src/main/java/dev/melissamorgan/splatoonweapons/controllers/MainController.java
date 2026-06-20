package dev.melissamorgan.splatoonweapons.controllers;

import dev.melissamorgan.splatoonweapons.entities.recommenderTools.TeamRecommenderRequest;
import dev.melissamorgan.splatoonweapons.entities.recommenderTools.TeamRecommenderResponse;
import dev.melissamorgan.splatoonweapons.entities.recommenderTools.TeamWeaponPool;
import dev.melissamorgan.splatoonweapons.entities.weaponFeatures.SpecialWeapon;
import dev.melissamorgan.splatoonweapons.entities.weaponFeatures.Subweapon;
import dev.melissamorgan.splatoonweapons.entities.weaponFeatures.Weapon;
import dev.melissamorgan.splatoonweapons.searchMethods.WeaponSearch;
import dev.melissamorgan.splatoonweapons.service.LambdaPredictionClient;
import dev.melissamorgan.splatoonweapons.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    WeaponService weaponService;
    @Autowired
    LambdaPredictionClient lambdaPredictionClient;

    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("weaponTypeList", weaponService.populateWeaponTypeList());
        model.addAttribute("subweaponsList", weaponService.populateSubweaponList());
        model.addAttribute("specialWeaponsList", weaponService.populateSpecialWeaponList());
        model.addAttribute("weightList", weaponService.populateWeightList());
        model.addAttribute("weaponList", weaponService.populateAllWeapons());
        model.addAttribute("stageList", weaponService.populateStageList());
        model.addAttribute("modeList", weaponService.populateModeList());
    }

    /***************
     * General Pages
     * **************/

    @GetMapping("/")
    public String index(Model model) {
        return "publicPages/home";
    }

    @GetMapping("/devnotes")
    public String devNotes() {
        return "publicPages/devnotes";
    }

    @GetMapping("/login")
    public String login() {
        return "publicPages/login";
    }

    /*****************************
     * Weapon Randomizer Tool Pages
     **************************** */

    @GetMapping("/randomizer")
    public String randomizer(Model model) {
        Weapon randomWeapon = weaponService.getRandomWeapon(new WeaponSearch());
        if (randomWeapon == null) {
            // if db returns null, create dummy weapon data
            Subweapon dummySub = new Subweapon();
            dummySub.setId(0);
            SpecialWeapon dummySpec = new SpecialWeapon();
            dummySpec.setId(0);
            randomWeapon = new Weapon();
            randomWeapon.setId(0);
            randomWeapon.setSubweapon(dummySub);
            randomWeapon.setSpecialWeapon(dummySpec);
        }
        model.addAttribute("randomWeapon", randomWeapon);
        return "publicPages/randomizer";
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

    /*****************************
     * Team Recommender Tool Pages
     * ****************************/

    @GetMapping("/recommender")
    public String recommender(Model model) {
        TeamWeaponPool alpha = new TeamWeaponPool();
        TeamWeaponPool bravo = new TeamWeaponPool();
        TeamRecommenderRequest request = new TeamRecommenderRequest(alpha, bravo, null, null);
        model.addAttribute("request", request);
        return "publicPages/recommender";
    }

    @GetMapping("/generateTeam")
    @ResponseBody
    public TeamRecommenderResponse generateTeam(@ModelAttribute TeamRecommenderRequest request, Model model) {
        TeamRecommenderResponse response = lambdaPredictionClient.fetchPrediction(request);

        // getting images
        Map<String, String> imagePaths = new HashMap<>();
        for (List<String> pool : response.getRecommendedTeam().values()) {
            if (pool != null && !pool.isEmpty()) {
                String weaponName = pool.getFirst();

                try {
                    Weapon weapon = weaponService.getWeaponByName(weaponName);
                    System.out.println(weapon.getId());
                    String imgUrl = "/weaponImages/Spl3_Weapon_" + weapon.getId() + ".png";
                    imagePaths.put(weaponName, imgUrl);
                } catch (Exception e) {
                    System.out.println("Could not load image for: " );
                }
            }
        }

        response.setWeaponImages(imagePaths);

        String dummyExplanation = "This team is meta.";
        return response;
    }
}

