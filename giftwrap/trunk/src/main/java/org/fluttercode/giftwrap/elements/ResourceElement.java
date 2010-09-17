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

import org.fluttercode.giftwrap.api.ContentProducer;
import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.impl.AbstractArchiveElement;
import org.fluttercode.giftwrap.impl.NamedResourceElement;

/**
 * @author Andy Gibson
 * 
 */
public class ResourceElement extends NamedResourceElement {

	private ContentProducer producer;

	public ResourceElement(String name, ContentProducer producer) {
		super(name);
		this.producer = producer;
	}

	@Override
	public void doAppend(DeploymentContext context) {
		context.addResource(producer.produce(), getName());
	}
	

}
