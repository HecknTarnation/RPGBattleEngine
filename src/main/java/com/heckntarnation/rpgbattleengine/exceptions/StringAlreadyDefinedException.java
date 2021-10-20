package com.heckntarnation.rpgbattleengine.exceptions;

/**
 *
 * @author Ben
 */
public class StringAlreadyDefinedException extends Exception {

    public StringAlreadyDefinedException(String key) {
        super(key);
    }

}
