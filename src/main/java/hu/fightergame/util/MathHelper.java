package hu.fightergame.util;

import hu.fightergame.core.Warrior;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MathHelper {
    private static final int ROLLINGDICE_MIN = 1;
    private static final int ROLLINGDICE_MAX = 6;

    public static int rollingDice() {
        return ROLLINGDICE_MIN + (int)(Math.random() * ((ROLLINGDICE_MAX - ROLLINGDICE_MIN) + 1));
    }

    public static int rollingDiceWithLimit(int limit) {
        return (ROLLINGDICE_MAX - limit > 0) ? ROLLINGDICE_MIN + (int)(Math.random() * (((ROLLINGDICE_MAX - limit) - ROLLINGDICE_MIN) + 1)) : 0;
    }

    public static List<Warrior> generateAllCombinations(boolean onlyAdd6PointCharacters) {
        List<Warrior> warriors = new ArrayList<Warrior>();
        if (onlyAdd6PointCharacters) {
            for (int strengthVal = 1; strengthVal <= 6; strengthVal++) {
                for (int speedVal = 0; speedVal <= 6 - strengthVal; speedVal++) {
                    for (int agilityVal = 0; agilityVal <= 6 - (strengthVal + speedVal); agilityVal++) {
                        if (strengthVal + speedVal + agilityVal == 6)
                            warriors.add(new Warrior(generateRandomName(), speedVal, agilityVal, strengthVal));
                        continue;
                    }
                }
            }
        } else {
            for (int strengthVal = 1; strengthVal <= 6; strengthVal++) {
                for (int speedVal = 0; speedVal <= 6 - strengthVal; speedVal++) {
                    for (int agilityVal = 0; agilityVal <= 6 - (strengthVal + speedVal); agilityVal++) {
                        warriors.add(new Warrior(generateRandomName(), speedVal, agilityVal, strengthVal));
                    }
                }
            }
        }
        return warriors;
    }

    public static String generateRandomName() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(32, secureRandom).toString(32);
    }

    public static double averageWinningRatio(Warrior warrior) {
        Double sum = 0.0;
        Set<String> opponentAttributeCodes = warrior.getChances().keySet();
        for (String opponentAttributeCode : opponentAttributeCodes) {
            sum += warrior.getChanceAgainst(opponentAttributeCode);
        }
        return sum / opponentAttributeCodes.size();
    }
}
