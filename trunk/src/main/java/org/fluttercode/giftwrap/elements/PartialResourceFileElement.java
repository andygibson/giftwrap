package org.fluttercode.giftwrap.elements;

import org.fluttercode.giftwrap.AbstractPartialFileElement;
import org.fluttercode.giftwrap.DeploymentContext;

public class PartialResourceFileElement extends AbstractPartialFileElement {

	public PartialResourceFileElement(String name, String content, int order) {
		super(name, content, order);
	}

	public PartialResourceFileElement(String name, String content) {
		super(name, content);
	}

	@Override
	protected void doAppendContent(String content, DeploymentContext context) {
		context.addPartialResource(getName(), content, getOrder());
	}

}
