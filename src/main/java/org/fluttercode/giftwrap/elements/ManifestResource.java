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
import org.fluttercode.giftwrap.impl.NamedResourceElement;
import org.fluttercode.giftwrap.impl.producer.StringContentProducer;
import org.fluttercode.giftwrap.impl.producer.XmlProducer;
import org.fluttercode.giftwrap.xml.DocumentNode;

/**
 * @author Andy Gibson
 * 
 */
public class ManifestResource extends NamedResourceElement {

	private ContentProducer producer;

	public ManifestResource(String name, String value) {
		this(name,new StringContentProducer(value));
	}
	
	public ManifestResource(String name, DocumentNode node) {
		this(name,new XmlProducer(node));
	}
	

	public ManifestResource(String name, ContentProducer producer) {
		super(name);
		this.producer = producer;
	}

	@Override
	public void doAppend(DeploymentContext context) {
		context.addManifestResource(producer.produce(), getName());
	}

}
