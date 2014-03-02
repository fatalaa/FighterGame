package hu.fightergame;

import hu.fightergame.core.Warrior;
import hu.fightergame.game.Ranking;
import hu.fightergame.util.MathHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        List<Warrior> warriors = MathHelper.generateAllCombinations(true);
        List<Ranking> rankings = new ArrayList<Ranking>(warriors.size());
        for (Warrior warrior : warriors) {
            for (int i = 0; i < warriors.size(); i++) {
                warrior.addChanceAgainst(warriors.get(i));
            }
            rankings.add(new Ranking(warrior, MathHelper.averageWinningRatio(warrior)));
        }
        Collections.sort(rankings, Collections.reverseOrder());
        int i = 1;
        for (Ranking ranking : rankings) {
            String text = "%d%sWarrior with %d%d%d attributes got a %.3f%s average winning chance";
            System.out.println(String.format(text, i,".", ranking.getWarrior().getSpeed(),
                                            ranking.getWarrior().getAgility(), ranking.getWarrior().getStrength(),
                                            ranking.getAverageWinningRatio() * 100, "%"));
            i++;
        }
    }
}
