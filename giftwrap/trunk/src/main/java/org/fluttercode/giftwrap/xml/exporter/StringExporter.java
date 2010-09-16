package org.fluttercode.giftwrap.xml.exporter;

import org.fluttercode.giftwrap.xml.DocumentNode;

public class StringExporter extends AbstractDocumentExporter {
	
	private StringBuilder sb = new StringBuilder();

	@Override
	public DocumentExporter initDocument() {
		sb = new StringBuilder();
		return super.initDocument();
	}
	
	@Override
	protected void doExportLine(String line) {
		sb.append(line);
	}
	
	public String getContent() {
		return sb.toString();		
	}

	public static String exportToString(DocumentNode node) {
		StringExporter exporter = new StringExporter();
		node.export(exporter);
		return exporter.getContent();		
	}

}
