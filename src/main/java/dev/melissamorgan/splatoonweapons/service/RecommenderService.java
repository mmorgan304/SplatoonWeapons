package dev.melissamorgan.splatoonweapons.service;

import dev.melissamorgan.splatoonweapons.entities.recommenderTools.TeamRecommenderRequest;
import dev.melissamorgan.splatoonweapons.entities.recommenderTools.TeamRecommenderResponse;
import dev.melissamorgan.splatoonweapons.entities.recommenderTools.TeamWeaponPool;

import java.util.List;

public class RecommenderService {
    // Call the Random Forest (via Python API)
    public TeamRecommenderResponse getRecommendation(TeamRecommenderRequest request) {
        // 1. Call Python API -> Get 'rawTeam' and 'features'
        // 2. return a populated response object
        return new TeamRecommenderResponse();
    }

//    // Call the RAG system
//    public String getExplanation(TeamWeaponPool team, List<String> features) {
//        // 1. Build a prompt using the team composition and features
//        // 2. Call your RAG/LLM endpoint
//        // 3. Return the explanation string
//    }
}
