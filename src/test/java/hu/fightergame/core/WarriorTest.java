package hu.fightergame.core;

import hu.fightergame.util.MathHelper;
import hu.fightergame.util.MathHelperTest;
import org.junit.Test;
import static org.junit.Assert.*;

public class WarriorTest {

    @Test
    public void testChanceAddition() {
        Warrior fast = new Warrior(MathHelper.generateRandomName(), 1, 4, 1);
        Warrior deadly = new Warrior(MathHelper.generateRandomName(), 0, 0, 6);

        fast.addChanceAgainst(fast);
        assertEquals(Double.valueOf(0.5), fast.getChanceAgainst(1, 4, 1));
        fast.addChanceAgainst(deadly);
        assertTrue(fast.getChanceAgainst(0, 0, 6) < 0.4);

        Warrior fastAgain = new Warrior(MathHelper.generateRandomName(), 1, 3, 2);
        fast.addChanceAgainst(fastAgain);
        assertNotNull(fast.getChanceAgainst("132"));

        Warrior theFastest = new Warrior(MathHelper.generateRandomName(), 2, 3, 1);
        fast.addChanceAgainst(theFastest);
        assertTrue(MathHelperTest.isIn10PercentRange(fast.getChanceAgainst(2, 3, 1), 0.585));
        assertNotNull(fast.getChanceAgainst("231"));
    }

    @Test
    public void testUnknownMatch() {
        Warrior fast = new Warrior(MathHelper.generateRandomName(), 1, 4, 1);
        assertNull(fast.getChanceAgainst(0, 0, 6));
    }
}
