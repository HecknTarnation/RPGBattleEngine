package coolninja.rpg.Cons;

import coolninja.rpg.Enums.EquipSlot;

/**
 * The equipment object constructor
 * @author Ben Ballard
 * @version 1.0
 * @since 1.0
 */
public class Equipment extends Item{
    
    public int maxHealth, maxMana, attack, defense, luck, mAttack, mDefense;
    public Weakness weakness;
    public EquipSlot slot;
    
    /**
     * Creates new equipment
     * @param String name
     * @param String description
     * @param int maxHealth
     * @param int maxMana
     * @param int attack
     * @param int defense
     * @param int luck
     * @param int Magic Attack
     * @param int Magic Defense
     * @param EquipSlot The slot it goes into when equipped
     */
    public Equipment(String name, String desc, int maxHealth, int maxMana, int attack, int defense, int luck, int mAttack, int mDefense, EquipSlot slot){
        super(name, desc);
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.attack = attack;
        this.defense = defense;
        this.luck = luck;
        this.mAttack = mAttack;
        this.mDefense = mDefense;
        this.slot = slot;
    }
    
    /**
     * Sets the armors type
     * @since 1.0
     * @param Weakness the type
     */
    public Equipment setType(Weakness weakness){
        this.weakness = weakness;
        return this;
    }
    
}
