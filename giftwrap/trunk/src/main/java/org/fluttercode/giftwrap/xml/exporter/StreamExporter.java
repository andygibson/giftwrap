package org.fluttercode.giftwrap.xml.exporter;

import java.io.IOException;
import java.io.OutputStream;

public class StreamExporter extends AbstractDocumentExporter {

	private final OutputStream stream;

	public StreamExporter(OutputStream stream) {
		this.stream = stream;
	}

	@Override
	protected void doExportLine(String line) {
		try {
			stream.write(line.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
