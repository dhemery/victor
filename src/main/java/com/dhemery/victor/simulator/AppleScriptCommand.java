package com.dhemery.victor.simulator;

import java.util.ArrayList;
import java.util.List;


public class AppleScriptCommand extends OSCommand {
	private static final String APPLESCRIPT_RUNNER = "osascript";
	public AppleScriptCommand(String...script) {
		super(APPLESCRIPT_RUNNER, script);
	}

	public AppleScriptCommand(List<String> scriptLines) {
		super(APPLESCRIPT_RUNNER, scriptFor(scriptLines));
	}

	private static List<String> scriptFor(List<String> scriptLines) {
		List<String> script = new ArrayList<String>();
		for(String line : scriptLines) {
			script.add("-e");
			script.add(line);
		}
		return script;
	}
}
