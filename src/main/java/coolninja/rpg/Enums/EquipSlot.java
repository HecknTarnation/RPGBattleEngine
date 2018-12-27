package coolninja.rpg.Enums;

public enum EquipSlot {
    
    Feet(0), Legs(1), Arms(2), Chest(3), Head(4), Weapon(5), Mod(6);
    
    public int index;
    
    //index is where it belongs in the equipment array
    private EquipSlot(int index){
        this.index = index;
    }
    
}
