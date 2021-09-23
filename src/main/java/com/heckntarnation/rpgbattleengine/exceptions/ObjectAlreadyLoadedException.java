package com.heckntarnation.rpgbattleengine.exceptions;

/**
 *
 * @author Ben
 */
public class ObjectAlreadyLoadedException extends Exception {

    public ObjectAlreadyLoadedException(String id) {
        super("ID: " + id);
    }

}
