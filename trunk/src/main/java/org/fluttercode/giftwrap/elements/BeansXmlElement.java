package org.fluttercode.giftwrap.elements;

import org.fluttercode.giftwrap.AbstractStringResource;
import org.fluttercode.giftwrap.DeploymentContext;

public class BeansXmlElement extends StringManifestResource {

	public BeansXmlElement() {
		super("beans.xml");
	}

	@Override
	protected String doGenerateContent() {
		return "<beans/>";
	}	

}
