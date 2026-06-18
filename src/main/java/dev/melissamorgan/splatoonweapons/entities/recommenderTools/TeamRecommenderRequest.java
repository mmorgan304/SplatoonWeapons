package dev.melissamorgan.splatoonweapons.entities.recommenderTools;

public class TeamRecommenderRequest {
    private TeamWeaponPool alphaTeamPool;
    private TeamWeaponPool bravoTeamPool;
    private String stage;
    private String mode;

    public TeamRecommenderRequest(TeamWeaponPool alphaTeamPool, TeamWeaponPool bravoTeamPool, String stage, String mode) {
        this.alphaTeamPool = alphaTeamPool;
        this.bravoTeamPool = bravoTeamPool;
        this.stage = stage;
        this.mode = mode;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public TeamWeaponPool getAlphaTeamPool() {
        return alphaTeamPool;
    }

    public void setAlphaTeamPool(TeamWeaponPool alphaTeamPool) {
        this.alphaTeamPool = alphaTeamPool;
    }

    public TeamWeaponPool getBravoTeamPool() {
        return bravoTeamPool;
    }

    public void setBravoTeamPool(TeamWeaponPool bravoTeamPool) {
        this.bravoTeamPool = bravoTeamPool;
    }
}
