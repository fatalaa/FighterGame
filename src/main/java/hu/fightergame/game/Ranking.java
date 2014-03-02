package hu.fightergame.game;

import hu.fightergame.core.Warrior;

public class Ranking implements Comparable{
    private Warrior warrior;
    private double averageWinningRatio;

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }

    public double getAverageWinningRatio() {
        return averageWinningRatio;
    }

    public void setAverageWinningRatio(double averageWinningRatio) {
        this.averageWinningRatio = averageWinningRatio;
    }

    public Ranking(Warrior warrior, double averageWinningRatio) {
        this.warrior = warrior;
        this.averageWinningRatio = averageWinningRatio;
    }

    @Override
    public int compareTo(Object o) {
        Ranking ranking = (Ranking)o;
        double result = this.averageWinningRatio - ranking.averageWinningRatio;
        if (result > 0)
            return 1;
        else if (result < 0)
            return -1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "warrior=" + warrior +
                ", averageWinningRatio=" + averageWinningRatio +
                '}';
    }
}
