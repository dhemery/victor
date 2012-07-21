package com.dhemery.os;

import java.util.List;
import java.util.Map;

/**
 * A factory that creates {@link OSCommand}s.
 * @param <T> the type of {@code OSCommand} created by the factory
 */
public interface OSCommandFactory<T extends OSCommand> {
    /**
     * Create an {@code OSCommand}.
     * See {@link OSCommand} for details about the parameters
     */
    T command(String description, String path, List<String> arguments, Map<String,String> environment);
}
