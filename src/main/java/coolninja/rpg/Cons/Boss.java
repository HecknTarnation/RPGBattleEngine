package coolninja.rpg.Cons;

public class Boss extends Enemy{

    public Boss(String name, int health, int attack, int defense, int luck, int mAttack, int mDefense, int expValue, Move[] moves) {
        super(name, health, attack, defense, luck, mAttack, mDefense, expValue, moves);
    }
    
    /**
     * Boss to enemy
     * @since 1.0
     */
    public Enemy BossToEnemy(){
        return (Enemy)this;
    }
}
