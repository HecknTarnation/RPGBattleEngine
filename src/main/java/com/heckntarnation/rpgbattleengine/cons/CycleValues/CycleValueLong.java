package com.heckntarnation.rpgbattleengine.cons.CycleValues;

/**
 *
 * @author Ben
 */
public class CycleValueLong {

    public long value;
    public long min;
    public long max;

    public CycleValueLong(long initalValue, long minValue, long maxValue) {
        this.value = initalValue;
        this.min = minValue;
        this.max = maxValue;
        if (initalValue > maxValue || initalValue < minValue) {
            this.value = 0;
        }
    }

    public CycleValueLong(long minValue, long maxValue) {
        this.value = 0;
        this.min = minValue;
        this.max = maxValue;
    }

    public long getValue() {
        adjustValue();
        return this.value;
    }

    public long incValue() {
        this.value++;
        adjustValue();
        return this.value;
    }

    public long decValue() {
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
