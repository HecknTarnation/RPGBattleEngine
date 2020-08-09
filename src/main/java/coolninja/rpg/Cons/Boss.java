package coolninja.rpg.Cons;

import java.net.URI;

/**
 * The boss object
 *
 * @author Ben Ballard
 * @version 1.0
 * @since 1.0
 */
public class Boss extends Enemy {

    public URI musicLocation;

    public Boss(String name, int health, int attack, int defense, int luck, int mAttack, int mDefense, int expValue, Move[] moves) {
        super(name, health, attack, defense, luck, mAttack, mDefense, expValue, moves);
    }

    public Boss(Enemy en) {
        super(en.name, en.health, en.attack, en.defense, en.luck, en.mAttack, en.mDefense, en.expValue, en.moves);
        this.setAILevel(en.AIID);
        this.setDrop(en.drop);
        this.setWeakness(en.weakness);
    }

    public Boss setMusic(URI location) {
        this.musicLocation = location;
        return this;
    }

    /**
     * Run when the boss is killed (after the exp is given) (Should be
     * overridden)
     *
     * @since 1.0
     */
    @Override
    public void onDeath() {
        System.out.println("Oh noes, you killed me D:");
    }

    static final long serialVersionUID = 9;

}
