package org.fluttercode.giftwrap.xml;

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.giftwrap.xml.exporter.DocumentExporter;

public class DocumentNode {

	private final String name;
	private String content;
	private final List<DocumentNodeAttribute> attributes = new ArrayList<DocumentNodeAttribute>();
	private final List<DocumentNode> nodes = new ArrayList<DocumentNode>();

	public DocumentNode(String name) {
		this.name = name;
	}

	public DocumentNode set(String name, String value) {
		attributes.add(new DocumentNodeAttribute(name, value));
		return this;
	}

	public DocumentNode node(String name) {
		DocumentNode newNode = new DocumentNode(name);
		nodes.add(newNode);
		return newNode;
	}

	public DocumentNode add(DocumentNode node) {
		nodes.add(node);
		return this;
	}

	public DocumentNode value(String value) {
		content = value;
		return this;
	}

	public boolean hasValue() {
		return (content != null && content.length() != 0);
	}

	public boolean isEmpty() {
		return !hasValue() && nodes.isEmpty();
	}

	public void export(DocumentExporter exporter) {
		if (exportHeader(exporter)) {
			exportValue(exporter);
			exportChildren(exporter);
			exporter.endNode(name);
		}
	}

	private void exportChildren(DocumentExporter exporter) {
		for (DocumentNode node : nodes) {
			node.export(exporter);
		}
	}

	private void exportValue(DocumentExporter exporter) {
		if (content != null && content.length() != 0) {
			exporter.writeValue(content);
		}
	}

	/**
	 * Writes out the start of the xml tag checking to see if it can be done in
	 * one line,
	 * 
	 * @param exporter
	 *            target exporter to write to
	 * @return true if the body of the node needs writing, false if the whole
	 *         tag was written
	 */
	protected boolean exportHeader(DocumentExporter exporter) {
		if (isEmpty()) {
			exporter.writeEmptyNode(name);
			return false;
		}

		if (hasValue() && nodes.isEmpty()) {
			exporter.writeNode(name, content);
			return false;
		}

		// write node fully
		exporter.startNode(name, attributes);

		return true;
	}

	public String getName() {
		return name;
	}

	public List<DocumentNodeAttribute> getAttributes() {
		return attributes;
	}
}
