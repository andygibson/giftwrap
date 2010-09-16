package org.fluttercode.giftwrap.elements;

import java.util.Enumeration;

import org.fluttercode.giftwrap.api.ContentProducer;
import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.impl.AbstractArchiveElement;
import org.fluttercode.giftwrap.impl.NamedResourceElement;
import org.fluttercode.giftwrap.impl.producer.StringContentProducer;
import org.fluttercode.giftwrap.impl.producer.XmlProducer;
import org.xmldsl.model.DocumentNode;

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
