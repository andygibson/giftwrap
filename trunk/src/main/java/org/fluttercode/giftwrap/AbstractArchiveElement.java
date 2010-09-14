package org.fluttercode.giftwrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractArchiveElement implements ArchiveElement {

	private List<String> includeProfile = new ArrayList<String>();
	private List<String> excludeProfile = new ArrayList<String>();

	@Override
	public ArchiveElement addIncludeProfile(String profile) {
		includeProfile.add(profile);
		return this; 
	}

	@Override
	public ArchiveElement addExcludeProfile(String profile) {
		excludeProfile.add(profile);
		return this;
	}
	
	
	 /**
	  * This element is active if : 
	  * 
	  * (At least one of the includeProfiles are active 
	  * OR there are no include profiles)
	  * AND
	  * there are no exclude profiles active 
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

	
	public abstract void doAppend(DeploymentContext context);
	
	@Override
	public void append(DeploymentContext context) {
		if (isActive(context)) {
			doAppend(context);
		}
		
	}
}
