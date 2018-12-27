package coolninja.rpg.Cons;

/**
 * The equipment object constructor
 * @author Ben Ballard
 * @version 1.0
 * @since 1.0
 */
public class Equipment extends Item{
    
    public int attack, defense, luck, mAttack, mDefense;
    public int slot;
    
    /**
     * Creates new equipment
     * @param name
     * @param description
     * @param attack
     * @param defense
     * @param luck
     * @param Magic Attack
     * @param Magic Defense
     * @param The slot it goes into when equipped
     */
    public Equipment(String name, String desc, int attack, int defense, int luck, int mAttack, int mDefense, int slot){
        super(name, desc);
        this.attack = attack;
        this.defense = defense;
        this.luck = luck;
        this.mAttack = mAttack;
        this.mDefense = mDefense;
        this.slot = slot;
    }
    
}
