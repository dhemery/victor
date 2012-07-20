package com.dhemery.os;

import java.util.List;
import java.util.Map;

public interface OSCommandFactory<T extends OSCommand> {
    T command(String description, String path, List<String> arguments, Map<String,String> environment);
}
