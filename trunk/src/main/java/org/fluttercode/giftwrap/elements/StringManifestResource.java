package org.fluttercode.giftwrap.elements;

import org.fluttercode.giftwrap.AbstractStringResource;
import org.fluttercode.giftwrap.DeploymentContext;

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
