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

package org.fluttercode.giftwrap.elements;

import org.fluttercode.giftwrap.AbstractStringResource;
import org.fluttercode.giftwrap.DeploymentContext;

/**
 * @author Andy Gibson
 * 
 */
public class StringManifestResource extends AbstractStringResource {

	private String value;

	public StringManifestResource(String name) {
		super(name);
	}
	
	public StringManifestResource(String name, String value) {
		super(name);
		this.value = value;
	}

	
	@Override
	protected String doGenerateContent() {
		return getValue();
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	protected void doAppendContent(String content, DeploymentContext context) {
		context.addManifestResource(content, getName());
	}
}
