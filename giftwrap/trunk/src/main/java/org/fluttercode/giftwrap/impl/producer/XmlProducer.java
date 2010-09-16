package org.fluttercode.giftwrap.impl.producer;

import org.xmldsl.model.DocumentNode;
import org.xmldsl.model.export.StringExporter;

public class XmlProducer extends AbstractStringContentProducer {

	private DocumentNode documentNode;

	public XmlProducer(DocumentNode documentNode) {
		this.documentNode = documentNode;
	}

	@Override
	protected String generateContent() {
		return StringExporter.exportToString(getDocumentNode());
	}

	private DocumentNode getDocumentNode() {
		return documentNode;
	}

}
