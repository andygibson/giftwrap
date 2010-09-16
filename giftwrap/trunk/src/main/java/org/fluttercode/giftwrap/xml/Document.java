package org.fluttercode.giftwrap.xml;

import org.fluttercode.giftwrap.xml.exporter.DocumentExporter;

public class Document extends DocumentNode {

	private String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Document(String name) {
		super(name);
	}

	@Override
	public void export(DocumentExporter exporter) {
		exporter.initDocument();
		try {
			exporter.writeValue(getHeader());
			super.export(exporter);
		} finally {
			exporter.finalizeDocument();
		}
	}

	
}
