package com.dhemery.victor.driver;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.driver.io.MapRequest;
import com.dhemery.victor.driver.io.MapResponse;
import com.dhemery.victor.driver.io.Response;
import com.dhemery.victor.elements.ios.IOSElement;
import com.google.gson.Gson;

public class SymbioteApplicationDriver implements IOSApplicationDriver {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private static final String SERVER_URL = "http://localhost:37265";

	private List<String> each(String locator, String property) {
		try {
			MapRequest request = new MapRequest(locator, property);
			Response response = request.sendTo(SERVER_URL);
			MapResponse mapResponse = new Gson().fromJson(response.body(), MapResponse.class);
			List<String> values = mapResponse.results();
			log.debug("[{}].{}: {}", new Object[] { locator, property, values} );
			return values;
		} catch (IOException e) {
			log.debug("Threw exception", e);
			return Collections.emptyList();
		}
	}

	@Override
	public boolean isPresent(IOSElement element) {
		List<String> matches = each(element.locator(), "accessibilityLabel");
		return matches.size() == 1;
	}

	@Override
	public boolean isVisible(IOSElement element) {
		List<String> matches = each(element.locator(), "isHidden");
		if(matches.size() != 1) return false;
		boolean isHidden = Boolean.parseBoolean(matches.get(0));
		return !isHidden;
	}

	@Override
	public void touch(IOSElement element) {
		each(element.locator(), "touch");
	}

	public void waitUntilReady() {
		new Poll().until(new ApplicationServerResponds(SERVER_URL));
	}
}
