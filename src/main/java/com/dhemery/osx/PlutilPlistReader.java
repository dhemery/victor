package com.dhemery.osx;

import com.dhemery.os.Shell;

public class PlutilPlistReader implements PlistReader {
    private final Shell shell;

    public PlutilPlistReader(Shell shell) {
        this.shell = shell;
    }

    @Override
    public String read(String path) {
        return shell.command("Plist Reader", "plutil")
                .withArguments("-convert", "json", "-o", "-", "--", path)
                .build().run().output();
    }
}
