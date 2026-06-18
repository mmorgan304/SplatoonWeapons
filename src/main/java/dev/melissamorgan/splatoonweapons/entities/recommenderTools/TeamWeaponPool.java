package dev.melissamorgan.splatoonweapons.entities.recommenderTools;

import java.util.List;

public class TeamWeaponPool {
    private List<String> player1Pool;
    private List<String> player2Pool;
    private List<String> player3Pool;
    private List<String> player4Pool;

    public TeamWeaponPool() {
    }

    public TeamWeaponPool(List<String> player1Pool, List<String> player2Pool, List<String> player3Pool, List<String> player4Pool) {
        this.player1Pool = player1Pool;
        this.player2Pool = player2Pool;
        this.player3Pool = player3Pool;
        this.player4Pool = player4Pool;
    }

    public List<String> getPlayer1Pool() {
        return player1Pool;
    }

    public void setPlayer1Pool(List<String> player1Pool) {
        this.player1Pool = player1Pool;
    }

    public List<String> getPlayer2Pool() {
        return player2Pool;
    }

    public void setPlayer2Pool(List<String> player2Pool) {
        this.player2Pool = player2Pool;
    }

    public List<String> getPlayer3Pool() {
        return player3Pool;
    }

    public void setPlayer3Pool(List<String> player3Pool) {
        this.player3Pool = player3Pool;
    }

    public List<String> getPlayer4Pool() {
        return player4Pool;
    }

    public void setPlayer4Pool(List<String> player4Pool) {
        this.player4Pool = player4Pool;
    }
}
