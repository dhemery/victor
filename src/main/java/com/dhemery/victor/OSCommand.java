package com.dhemery.victor;

import java.util.List;
import java.util.Map;

public interface OSCommand {
    List<String> arguments();
    String description();
    Map<String,String> environment();
    String path();
}
