package com.dhemery.victor.io;

/**
 * Serializes and deserializes objects.
 */
public interface Codec{
    /**
     * Create an object from a serialized representation of the object.
     * @param representation a serialized representation of an object.
     * @param objectType the type of object represented by the serialized representation.
     * @return the object represented by the serialized representation.
     */
    <T> T decode(String representation, Class<T> objectType);

    /**
     * Create a serialized representation of an object.
     */
    String encode(Object object);
}
