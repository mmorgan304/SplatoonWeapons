package dev.melissamorgan.splatoonweapons.controllers;

import dev.melissamorgan.splatoonweapons.entities.recommenderTools.*;
import dev.melissamorgan.splatoonweapons.entities.weaponFeatures.SpecialWeapon;
import dev.melissamorgan.splatoonweapons.entities.weaponFeatures.Subweapon;
import dev.melissamorgan.splatoonweapons.entities.weaponFeatures.Weapon;
import dev.melissamorgan.splatoonweapons.searchMethods.WeaponSearch;
import dev.melissamorgan.splatoonweapons.service.GroqTextClient;
import dev.melissamorgan.splatoonweapons.service.LambdaPredictionClient;
import dev.melissamorgan.splatoonweapons.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    WeaponService weaponService;
    @Autowired
    LambdaPredictionClient lambdaPredictionClient;
    @Autowired
    GroqTextClient groqTextClient;

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
        TeamExplainerRequest explainerRequest = new TeamExplainerRequest();
        System.out.println(request.getBravoTeamPool().getPlayer1Pool().getFirst());

        explainerRequest.setRecommendedTeam(response.getRecommendedTeam());
        explainerRequest.setProjectedWinRate(response.getProjectedWinRate());

        List<String> bravoWeapons = new ArrayList<>();
        if (request.getBravoTeamPool() != null) {
            bravoWeapons.add(request.getBravoTeamPool().getPlayer1Pool().getFirst());
            bravoWeapons.add(request.getBravoTeamPool().getPlayer2Pool().getFirst());
            bravoWeapons.add(request.getBravoTeamPool().getPlayer3Pool().getFirst());
            bravoWeapons.add(request.getBravoTeamPool().getPlayer4Pool().getFirst());
        }
        explainerRequest.setBravoTeam(bravoWeapons);

        // Extract raw ML feature values from your FeatureImpacts
        List<String> rawAdvantages = new ArrayList<>();
        List<String> rawDeficits = new ArrayList<>();
        if (response.getFeatures() != null) {
            rawAdvantages = response.getFeatures().getAdvantages();
            rawDeficits = response.getFeatures().getDeficits();
        }
        explainerRequest.setAdvantages(rawAdvantages);
        explainerRequest.setDeficits(rawDeficits);

        System.out.println("explainer request: " + explainerRequest.toString());

        TeamExplainerResponse explainerResponse = groqTextClient.fetchExplanation(explainerRequest);
        System.out.println("explainer justification: " + explainerResponse.getJustificationParagraph());

        response.setExplanation(explainerResponse.getJustificationParagraph());
        response.setHoverRoles(explainerResponse.getHoverRoles());

        if (response.getFeatures() != null) {
            response.getFeatures().setAdvantages(explainerResponse.getRephrasedAdvantages());
            response.getFeatures().setDeficits(explainerResponse.getRephrasedDeficits());
        }

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
                    System.out.println("Could not load image for: " + weaponName);
                }
            }
        }
        response.setWeaponImages(imagePaths);

        return response;
    }
}

