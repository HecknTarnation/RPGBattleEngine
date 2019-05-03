package coolninja.rpg.Enums;

public enum AIID {
    
    Random((byte)0), Weakness((byte)1), Health((byte)2);
    
    public byte level;
    private AIID(byte level){
        this.level = level;
    }
}
