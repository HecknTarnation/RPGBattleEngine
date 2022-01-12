package com.heckntarnation.rpgbattleengine.cons.CycleValues;

/**
 *
 * @author Ben
 */
public class CycleValue {

    public int value;
    public int min;
    public int max;

    public CycleValue(int initalValue, int minValue, int maxValue) {
        this.value = initalValue;
        this.min = minValue;
        this.max = maxValue;
    }

    public CycleValue(int minValue, int maxValue) {
        this.value = 0;
        this.min = minValue;
        this.max = maxValue;
    }

    public int getValue() {
        return this.value;
    }

    public int incValue() {
        this.value++;
        adjustValue();
        return this.value;
    }

    public int decValue() {
        this.value--;
        adjustValue();
        return this.value;
    }

    private void adjustValue() {
        if (this.value > max) {
            this.value = min;
        }
        if (this.value < min) {
            this.value = max;
        }
    }

}
