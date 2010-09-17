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

package org.fluttercode.giftwrap.impl;

import org.fluttercode.giftwrap.api.ArchiveElement;
import org.fluttercode.giftwrap.api.DeploymentContext;

/**
 * @author Andy Gibson
 * 
 */
public abstract class AbstractArchiveElement implements ArchiveElement {

	private ProfileHolder profiles = new ProfileHolder();

	@Override
	public ArchiveElement includeIn(String profile) {
		profiles.addToIncludeProfile(profile);		
		return this; 
	}

	@Override
	public ArchiveElement excludeFrom(String profile) {
		profiles.addToExcludeProfile(profile);
		return this;
	}
	
	
	public boolean isActive(DeploymentContext context) {
		return profiles.isActive(context);
	}

	
	public abstract void doAppend(DeploymentContext context);
	
	@Override
	public void append(DeploymentContext context) {
		if (isActive(context)) {
			doAppend(context);
		}		
	}
	
	public ElementContainer addTo(ElementContainer container) {
		return container.addElement(this);		
	}
}
