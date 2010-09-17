package org.fluttercode.giftwrap.impl;

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.giftwrap.api.ArchiveElement;
import org.fluttercode.giftwrap.api.DeploymentContext;

public class ProfileHolder {

	private List<String> includeProfile = new ArrayList<String>();
	private List<String> excludeProfile = new ArrayList<String>();

	public void addToIncludeProfile(String profile) {
		includeProfile.add(profile);
	}

	public void addToExcludeProfile(String profile) {
		excludeProfile.add(profile);
	}

	/**
	 * This element is active if :
	 * 
	 * (At least one of the includeProfiles are active OR there are no include
	 * profiles) AND there are no exclude profiles active
	 * 
	 * @param context
	 * @return whether this element is active in this context
	 */
	public boolean isActive(DeploymentContext context) {
		boolean active = includeProfile.size() == 0;
		for (String profile : includeProfile) {
			if (context.getProfiles().contains(profile)) {
				active = true;
			}
		}

		for (String profile : excludeProfile) {
			if (context.getProfiles().contains(profile)) {
				active = false;
			}
		}
		return active;
	}
}
