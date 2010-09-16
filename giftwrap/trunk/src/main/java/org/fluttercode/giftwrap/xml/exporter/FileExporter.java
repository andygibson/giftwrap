package org.fluttercode.giftwrap.xml.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileExporter extends AbstractDocumentExporter {

	private File file;
	private OutputStream outputStream;

	public FileExporter(String filename) {
		this(new File(filename));
	}

	public FileExporter(File file) {
		this.file = file;
	}

	@Override
	public DocumentExporter initDocument() {
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	protected void doExportLine(String line)  {
		try {
			outputStream.write(line.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public DocumentExporter finalizeDocument() {
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.finalizeDocument();
	}
	
}
