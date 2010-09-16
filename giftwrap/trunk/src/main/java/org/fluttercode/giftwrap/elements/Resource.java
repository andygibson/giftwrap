package org.fluttercode.giftwrap.elements;

import org.fluttercode.giftwrap.api.ContentProducer;
import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.impl.AbstractArchiveElement;
import org.fluttercode.giftwrap.impl.NamedResourceElement;

public class Resource extends NamedResourceElement {

	private ContentProducer producer;

	public Resource(String name, ContentProducer producer) {
		super(name);
		this.producer = producer;
	}

	@Override
	public void doAppend(DeploymentContext context) {
		context.addResource(producer.produce(), getName());
	}
	

}