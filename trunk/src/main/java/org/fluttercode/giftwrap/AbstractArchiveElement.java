/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Giftwrap.
 *
 * Giftwrap is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Giftwrap is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Giftwrap.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.giftwrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andy Gibson
 * 
 */
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
