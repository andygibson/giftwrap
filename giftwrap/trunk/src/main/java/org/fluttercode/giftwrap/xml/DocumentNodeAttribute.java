package org.fluttercode.giftwrap.xml;

public class DocumentNodeAttribute {

	private final String name;
	private final String value;

	public DocumentNodeAttribute(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public String asString() {
		return name + "=\"" + value + "\"";
	}

}
