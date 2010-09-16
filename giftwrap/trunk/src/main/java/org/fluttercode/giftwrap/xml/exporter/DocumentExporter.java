package org.fluttercode.giftwrap.xml.exporter;

import java.util.List;

import org.fluttercode.giftwrap.xml.DocumentNodeAttribute;

public interface DocumentExporter {
	public DocumentExporter initDocument();
	public DocumentExporter startNode(String name,List<DocumentNodeAttribute> attributes);	
	public DocumentExporter startNode(String name);
	public DocumentExporter writeValue(String value);	
	public DocumentExporter endNode(String name);
	public DocumentExporter writeEmptyNode(String name);
	public DocumentExporter writeNode(String name,String value);
	public DocumentExporter finalizeDocument();
	public DocumentExporter formatted();
	public DocumentExporter noFormat();
}
