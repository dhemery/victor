package com.dhemery.os;

import java.util.List;
import java.util.Map;

public interface OSCommand {
    String path();
    List<String> arguments();
    Map<String, String> environment();
    String description();
}
