package coolninja.rpg.Cons;

import java.io.Serializable;

import coolninja.rpg.Enums.EquipSlot;

/**
 * The equipment object constructor
 * @author Ben Ballard
 * @version 1.0
 * @since 1.0
 */
public class Equipment extends Item implements Serializable{
    
    static final long serialVersionUID = 4;
    
    public int maxHealth, maxMana, attack, defense, luck, mAttack, mDefense;
    public Weakness weakness;
    public EquipSlot slot;
    
    /**
     * Creates new equipment
     * @param name
     * @param desc
     * @param maxHealth
     * @param maxMana
     * @param attack
     * @param defense
     * @param luck
     * @param mAttack
     * @param mDefense
     * @param slot
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
     * @return 
     * @since 1.0
     * @param weakness
     */
    public Equipment setType(Weakness weakness){
        this.weakness = weakness;
        return this;
    }
    
}
