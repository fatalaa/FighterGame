package hu.fightergame.util;

import hu.fightergame.core.Warrior;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MathHelperTest {

    @Test
    public void testRollingDice() {
        int number = MathHelper.rollingDice();
        assertTrue(number >= 1 && number <= 6);
    }

    @Test
    public void testRollingDiceWithLimit() {
        assertEquals(0, MathHelper.rollingDiceWithLimit(6));
        assertTrue(MathHelper.rollingDiceWithLimit(2) != 0);
        assertEquals(1, MathHelper.rollingDiceWithLimit(5));
    }

    @Test
    public void testAllGeneratedWarriors() {
        for (Warrior warrior : MathHelper.generateAllCombinations(false)) {
            assertTrue(6 >= warrior.getSpeed() + warrior.getAgility() + warrior.getStrength());
        }
    }

    @Test
    public void test10PercentRange() {
        Double expected = 1.0;
        Double actual = 0.9;
        Double actual2 = 1.21;
        assertTrue(isIn10PercentRange(actual, expected));
        assertFalse(isIn10PercentRange(actual2, expected));
    }

    public static boolean isIn10PercentRange(Double actualValue, Double expectedValue) {
        boolean lesserStatement = actualValue >= expectedValue * 0.9;
        boolean greaterStatement = actualValue <= expectedValue * 1.1;
        return lesserStatement && greaterStatement;
    }
}
