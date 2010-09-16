package org.fluttercode.giftwrap.xml;

public class DocumentFactory {

	public static DocumentNode node(String name) {
		return new DocumentNode(name);
	}

	public static DocumentNodeAttribute attribute(String name,String value) {
		return new DocumentNodeAttribute(name, value);
	}
}
