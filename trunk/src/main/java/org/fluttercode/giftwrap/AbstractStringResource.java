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

import org.fluttercode.giftwrap.api.DeploymentContext;


/**
 * Abstract class for adding manifest resources that are based on string content 
 * @author Andy Gibson
 *
 */
public abstract class AbstractStringResource extends AbstractArchiveElement {

	private final String name;

	public AbstractStringResource(String name) {
		this.name = name;
	}

	@Override
	public void doAppend(DeploymentContext context) {	
		String content = doGenerateContent();
		doAppendContent(content,context);		
	}

	public String getName() {
		return name;
	}
	protected abstract String doGenerateContent();
	protected abstract void doAppendContent(String content,DeploymentContext context);

}
