package com.dhemery.osx;

import com.dhemery.os.Shell;

public class AppleScriptShell {
    private final Shell shell;

    public AppleScriptShell(Shell shell) {
        this.shell = shell;
    }

    public AppleScriptBuilder script(String description) {
        return new AppleScriptBuilder(shell, description);
    }
}
