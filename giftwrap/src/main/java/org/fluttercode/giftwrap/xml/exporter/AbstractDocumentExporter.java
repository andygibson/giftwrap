/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Giftwrap.
 *
 * Giftwrap is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Giftwrap is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Giftwrap.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.giftwrap.xml.exporter;

import java.util.List;

import org.fluttercode.giftwrap.xml.DocumentNodeAttribute;

/**
 * @author Andy Gibson
 * 
 */
public abstract class AbstractDocumentExporter implements DocumentExporter {

	private boolean newLines;
	private boolean indent;
	private String indentation = "";
	private int indentLevel = 0;

	public AbstractDocumentExporter() {
		this(true, true);
	}

	public AbstractDocumentExporter(boolean newLines, boolean indent) {
		this.newLines = newLines;
		this.indent = indent;
	}

	public DocumentExporter startNode(String name) {
		startNode(name, null);
		return this;
	}

	public DocumentExporter startNode(String name,
			List<DocumentNodeAttribute> attributes) {

		return startNode(name, attributes, null);
	}

	public DocumentExporter endNode(String name) {
		decreaseIndent();
		outputLine("</" + name + ">");
		return this;
	}

	private void decreaseIndent() {
		indentLevel--;
		buildIndentation();
	}

	private void increaseIndent() {
		indentLevel++;
		buildIndentation();
	}

	private void buildIndentation() {
		indentation = "";
		// sloppy but works
		if (indent) {
			for (int i = 0; i < indentLevel; i++) {
				indentation = indentation + " ";
			}
		}
	}

	private void outputLine(String line) {
		if (newLines) {
			line = line + "\n";
		}
		if (indent) {
			line = indentation + line;
		}
		doExportLine(line);
	}

	protected String escapeContent(String content) {
		return content;
	}

	public DocumentExporter writeValue(String value) {
		outputLine(escapeContent(value));
		return this;
	}

	protected abstract void doExportLine(String line);

	public DocumentExporter initDocument() {
		return this;
	}

	public DocumentExporter finalizeDocument() {
		return this;
	}

	public DocumentExporter writeNode(String name, String value) {
		String line = String.format("<%s>%s</%s>", name, escapeContent(value),
				name);
		outputLine(line);
		return this;
	}

	public DocumentExporter writeEmptyNode(String name) {
		String line = String.format("<%s/>", name);
		outputLine(line);
		return this;
	}

	public DocumentExporter formatted() {
		newLines = true;
		indent = true;
		return this;
	}

	public DocumentExporter noFormat() {
		newLines = false;
		indent = false;
		return this;
	}

	public DocumentExporter startNode(String name,
			List<DocumentNodeAttribute> attributes, String namespaces) {

		String line = "<" + name;
		if (attributes != null) {
			for (DocumentNodeAttribute attr : attributes) {
				line = line + " " + attr.asString();
			}
		}
		if (namespaces != null) {
			outputLine(line);
			increaseIndent();
			outputLine(namespaces+">");
			decreaseIndent();
		} else {
			line = line + ">";
			outputLine(line);
		}
		increaseIndent();
		return this;

	}
}
