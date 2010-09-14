package org.fluttercode.giftwrap.elements;

import org.fluttercode.giftwrap.AbstractPartialFileElement;
import org.fluttercode.giftwrap.DeploymentContext;

public class PartialManifestFileElement extends AbstractPartialFileElement {

	public PartialManifestFileElement(String name, String content, int order) {
		super(name, content, order);
	}

	public PartialManifestFileElement(String name, String content) {
		super(name, content);
	}

	@Override
	protected void doAppendContent(String content, DeploymentContext context) {
		context.addPartialManifestResource(getName(), content, getOrder());
	}

}
