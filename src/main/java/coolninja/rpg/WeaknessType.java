package coolninja.rpg;

/**
 * List of weakness types
 * @version 1.0
 * @since
 * @author Ben Ballard
 */
public enum WeaknessType {
    
    Fire(0), Water(1), Earth(2), Air(3), Physical(4), Dark(5), Light(6);
    
    int i;
    
    private WeaknessType(int i){
        this.i = i;     
    }
}
