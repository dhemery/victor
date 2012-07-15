package com.dhemery.victor;

import java.util.List;

public interface IosViewAgent {
    List<String> sendMessage(By query, String method, Object... arguments);
    IosView view(By query);
}
