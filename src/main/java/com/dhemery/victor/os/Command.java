package com.dhemery.victor.os;

import java.util.List;
import java.util.Map;

public interface Command {
    List<String> arguments();
    void buildTo(ProcessBuilder builder);
    String description();
    Map<String,String> environment();
    String path();
}
