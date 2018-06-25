package com.engedaludvikling.sheetr.models;

public class Character {

    public String Name;
    public String PlayerName;
    public String Race;
    public String Class;
    public short Level;
    public int ExperiencePoints;
    public String Background;
    public Alignment Alignment;

    public AbilityScores AbilityScores;

    public short PassiveWisdom;

    public short Inspiration;
    public short ProficiencyBonus;
    public SavingThrows SavingThrows;

    public Skills Skills;

    public short ArmorClass;
    public short Initiative;
    public short Speed;

    public short MaximumHitPoints;
    public short CurrentHitPoints;
    public short TemporaryHitPoints;
    public String HitDiceTotal;
    public String HitDice;
    public DeathSaves DeathSaves;

    public String PersonalityTraits;
    public String Ideals;
    public String Bonds;
    public String Flaws;
    public String FeaturesAndTraits;
    public String OtherProficiencyAndLanguages;

    // Should this be an image?
    public String CharacterAppearance;
    public String CharacterBackstory;
    public String AlliesAndOrganizations;
    public String AdditionalFeaturesAndTraits;
    public String Treasure;
}

enum Alignment {
    LawfulGood,
    LawfulNeutral,
    LawFulEvil,
    NeutralGood,
    Neutral,
    NeutralEvil,
    ChaoticGood,
    ChaoticNeutral,
    ChaoticEvil
}

class AbilityScores {
    public short Strength;
    public short Dexterity;
    public short Constitution;
    public short Intelligence;
    public short Wisdom;
    public short Charisma;

    public short StrengthProficiency;
    public short DexterityProficiency;
    public short ConstitutionProficiency;
    public short IntelligenceProficiency;
    public short WisdomProficiency;
    public short CharismaProficiency;

    public AbilityScores(short strength, short dexterity, short constitution,
                         short intelligence, short wisdom, short charisma) {
        this.Strength = strength;
        this.Dexterity = dexterity;
        this.Constitution = constitution;
        this.Intelligence = intelligence;
        this.Wisdom = wisdom;
        this.Charisma = charisma;

        this.StrengthProficiency = calculateProficiency(this.Strength);
        this.DexterityProficiency = calculateProficiency(this.Dexterity);
        this.ConstitutionProficiency = calculateProficiency(this.Constitution);
        this.IntelligenceProficiency = calculateProficiency(this.Intelligence);
        this.WisdomProficiency = calculateProficiency(this.Wisdom);
        this.CharismaProficiency = calculateProficiency(this.Charisma);
    }

    public AbilityScores() {}

    private short calculateProficiency(short value) {
        if (value <= 0)
            return -5;
        if (value < 3)
            return -4;
        if (value < 5)
            return -3;
        if (value < 7)
            return -2;
        if (value < 9)
            return -1;
        if (value < 11)
            return 0;
        if (value < 13)
            return 1;
        if (value < 15)
            return 2;
        if (value < 17)
            return 3;
        if (value < 19)
            return 4;
        else
            return 5;
    }
}

class SavingThrows {
    public short Strength;
    public short Dexterity;
    public short Constitution;
    public short Intelligence;
    public short Wisdom;
    public short Charisma;
}

class Skills {
    public short Acrobatics;
    public short AnimalHandling;
    public short Arcana;
    public short Athletics;
    public short Deception;
    public short History;
    public short Insight;
    public short Intimidation;
    public short Investigation;
    public short Medicine;
    public short Nature;
    public short Perception;
    public short Performance;
    public short Persuasion;
    public short Religion;
    public short SleightOfHand;
    public short Stealth;
    public short Survival;
}

class DeathSaves {
    public short Successes;
    public short Failures;
}
