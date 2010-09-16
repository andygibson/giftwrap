package org.fluttercode.giftwrap.impl;

public abstract class NamedResourceElement extends AbstractArchiveElement {

	private String name;

	public NamedResourceElement(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
