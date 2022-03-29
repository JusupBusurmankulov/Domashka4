package com.company;

import java.util.Random;

public class Main {

    public static int[] heroesHealth = {270, 280, 260, 300};
    public static int[] heroesDamage = {20, 15, 25, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medical"};

    public static int hillMedical = 25;
    public static int indexMedical = 3;

    public static int bossHealth = 720;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int roundNumber = 0;

    public static void medic() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] < 100 && heroesHealth[i] > 0 && heroesHealth[i] != heroesHealth[indexMedical]) {
                Random random = new Random();
                int randomMedic = random.nextInt(heroesHealth.length);
                if (heroesHealth[randomMedic] != heroesHealth[indexMedical]) {
                    heroesHealth[randomMedic] = heroesHealth[randomMedic] + hillMedical ;
                    System.out.println("Help " + hillMedical + " " + heroesAttackType[i]);

                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        printDStatistics();
        isGameFinished();
        while (!isGameFinished()) {
            round();
            medic();
        }
    }

    private static void round() {
        roundNumber++;
        chooseBossDefenceType();
        bossHits();
        heroesHit();
        printDStatistics();
    }

    public static void chooseBossDefenceType() {
        Random random = new Random();
        int randNum = random.nextInt(heroesAttackType.length);  // 0 1 2
        bossDefenceType = heroesAttackType[randNum];
        System.out.println("Boss choose: " + bossDefenceType);
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] < bossDamage) {
                heroesHealth[i] = 0;
            } else {
                heroesHealth[i] = heroesHealth[i] - bossDamage;
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefenceType) {
                    Random r = new Random();
                    int coefficient = r.nextInt(8) + 2;

                    if (bossHealth < heroesDamage[i] * coefficient) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coefficient;
                    }
                    System.out.println("Critical Damage: " + heroesDamage[i] * coefficient);
                } else {
                    if (bossHealth < heroesDamage[i]) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static Boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int j : heroesHealth) {
            if (j > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("BOSS WON!");
        }
        return allHeroesDead;
    }

    public static void printDStatistics() {
        System.out.println("----" + roundNumber + " ROUND----");
        System.out.println("Boss health: " + bossHealth + " [" + bossDamage + "]");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " [" + heroesDamage[i] + "]");
        }
    }
}