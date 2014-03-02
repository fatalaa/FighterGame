package hu.fightergame.core;

import hu.fightergame.util.MathHelper;

import java.util.HashMap;

public class Warrior {
    private String name;
    private int speed;
    private int agility;
    private int strength;

    private HashMap<String, Double> chances;

    public Warrior(String name, int speed, int agility, int strength) {
        this.name = name;
        this.speed = speed;
        this.agility = agility;
        this.strength = strength;
        this.chances = new HashMap<String, Double>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public HashMap<String, Double> getChances() {
        return chances;
    }

    @Override
    public String toString() {
        return "Warrior{" +
                "name='" + name + '\'' +
                ", speed=" + speed +
                ", agility=" + agility +
                ", strength=" + strength +
                ", chances=" + chances +
                '}';
    }

    public Double getChanceAgainst(String opponentAttributeCode) {
        return this.chances.get(opponentAttributeCode);
    }

    public Double getChanceAgainst(int speed, int agility, int strength) {
        return this.chances.get(String.format("%d%d%d", speed, agility, strength));
    }

    public double hitChance() {
        return (double)this.strength / 6.0;
    }

    public double dodgeChance() {
        return (double)this.agility / 6.0;
    }

    public void addChanceAgainst(Warrior anotherWarrior) {
        if ((anotherWarrior.speed == this.speed) && (anotherWarrior.agility == this.agility) && (anotherWarrior.strength == this.strength)) {
            this.chances.put(String.format("%d%d%d",anotherWarrior.speed, anotherWarrior.agility, anotherWarrior.strength), 0.5);
            return;
        }
        double[] thisWinChances = new double[32];
        double thisBaseWinChance;
        double[] anotherWinChances = new double[32];
        double anotherBaseWinChance;
        int whoStarts;

        if (this.speed > anotherWarrior.speed) {
            thisBaseWinChance = this.hitChance() * (1.0 - anotherWarrior.dodgeChance());
            anotherBaseWinChance = anotherWarrior.hitChance() * (1.0 - this.dodgeChance());
            whoStarts = 1;
        } else if (this.speed < anotherWarrior.speed) {
            anotherBaseWinChance = anotherWarrior.hitChance() * (1 - this.dodgeChance());
            thisBaseWinChance = this.hitChance() * (1 - anotherWarrior.dodgeChance());
            whoStarts = 2;
        } else {
            if (MathHelper.rollingDice() <= 3) {
                thisBaseWinChance = this.hitChance() * (1 - anotherWarrior.dodgeChance());
                anotherBaseWinChance = anotherWarrior.hitChance() * (1 - this.dodgeChance());
                whoStarts = 1;
            } else {
                anotherBaseWinChance = anotherWarrior.hitChance() * (1 - this.dodgeChance());
                thisBaseWinChance = this.hitChance() * (1 - anotherWarrior.dodgeChance());
                whoStarts = 2;
            }
        }

        double d;
        if (whoStarts == 1) {
            thisWinChances[0] = thisBaseWinChance;
            anotherWinChances[0] = anotherBaseWinChance * (1.0 - thisWinChances[0]);
            d = Math.floor(thisWinChances[0] + anotherWinChances[0] * 1000) / 1000.0d;
            for (int i = 1; i < 32; i++) {
                thisWinChances[i] = thisWinChances[i - 1] + thisBaseWinChance * (1.0 - (thisWinChances[i - 1] + anotherWinChances[i - 1]));
                anotherWinChances[i] = anotherWinChances[i - 1] + anotherBaseWinChance * (1.0 - (anotherWinChances[i - 1] + thisWinChances[i]));
                d = Math.round((thisWinChances[i - 1] + anotherWinChances[i - 1]) * 1000) / 1000.0d;
            }
        } else {
            anotherWinChances[0] = anotherBaseWinChance;
            thisWinChances[0] = thisBaseWinChance * (1.0 - anotherWinChances[0]);
            d = Math.floor(thisWinChances[0] + anotherWinChances[0] * 1000) / 1000.0d;
            for (int i = 1; i < 32; i++) {
                anotherWinChances[i] = anotherWinChances[i - 1] + anotherBaseWinChance * (1.0 - (anotherWinChances[i - 1] + thisWinChances[i - 1]));
                thisWinChances[i] = thisWinChances[i - 1] + thisBaseWinChance * (1.0 - (thisWinChances[i - 1] + anotherWinChances[i]));
                d = Math.round((thisWinChances[i - 1] + anotherWinChances[i - 1]) * 1000) / 1000.0d;
            }
        }
        this.chances.put(String.format("%d%d%d",anotherWarrior.speed, anotherWarrior.agility, anotherWarrior.strength),
                         Math.round(thisWinChances[thisWinChances.length - 1] * 1000) / 1000.0d);
    }
}
