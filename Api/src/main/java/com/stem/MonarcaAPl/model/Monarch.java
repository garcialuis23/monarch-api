package com.stem.MonarcaAPl.model;

import java.util.Random;

public class Monarch {

    private int idMonarch;
    private String name;
    private String ordinal;
    private String shield;
    private String dynasty;
    private String description;
    private int strategyLevel;
    private int diplomacyLevel;
    private int experienceLevel; // Edad.
    private boolean life;

    // Por defecto
    public Monarch() {

    }

    // Por parametros.
    public Monarch(int idMonarch, String name, String ordinal, String shield, String dynasty, String description,
            int strategyLevel, int diplomacyLevel, int experienceLevel, boolean life) {
        this.idMonarch = idMonarch;
        this.name = name;
        this.ordinal = ordinal;
        this.dynasty = dynasty;
        this.shield = shield;
        this.description = description;
        this.strategyLevel = 1;
        this.setStrategyLevel(strategyLevel);
        this.diplomacyLevel = 1;
        this.setDiplomacyLevel(diplomacyLevel);
        this.experienceLevel = experienceLevel;
        this.setExperienceLevel(experienceLevel);
        this.life = life;

    }

    public int getId() {
        return this.idMonarch;
    }

    public String getName() {
        return this.name;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public String getDynasty() {
        return this.dynasty;
    }

    public String getShield() {
        return this.shield;
    }

    public String getDescription() {
        return this.description;
    }

    public int getStrategyLevel() {
        return this.strategyLevel;
    }

    public int getDiplomacyLevel() {
        return this.diplomacyLevel;
    }

    public int getExperienceLevel() {
        return this.experienceLevel;
    }

    public boolean getLife() {
        return this.life;
    }

    // ----------------------------------------------------------------------------------

    public void setId(int idMonarch) {
        this.idMonarch = idMonarch;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty.toUpperCase();
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStrategyLevel(int strategyLevel) {
        if (strategyLevel > 0 && strategyLevel <= 10)
            this.strategyLevel = strategyLevel;
    }

    public void setDiplomacyLevel(int diplomacyLevel) {
        if (diplomacyLevel > 0 && diplomacyLevel <= 10)
            this.diplomacyLevel = diplomacyLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        if (experienceLevel >= 14 && experienceLevel <= 35)
            this.experienceLevel = experienceLevel;
    }

    public void setLife(boolean life) {
        this.life = life;
    }

    public void generateRandomAge() {
        int min = 14, max = 35;
        Random rand = new Random();
        int age = rand.nextInt((max - min) + 1) + min;
        this.setExperienceLevel(age);
    }

    public boolean validateLevels(int strategyLevel, int diplomaticLevel) {
        int total = this.strategyLevel + this.diplomacyLevel;
        return total <= 5;
    }

    @Override
    public String toString() {
        return "Monarch [ID -> " + this.idMonarch + ", name -> " + this.name + ", ordinal -> " + ordinal
                + ", dynasty -> " + this.dynasty + ", shield=" + this.shield + ", description -> "
                + this.description + ", strategyLevel -> " + this.strategyLevel + ", dynasty -> "
                + this.diplomacyLevel + ", experienceLevel -> " + this.experienceLevel + ", life "
                + this.life + "]";
    }

}
