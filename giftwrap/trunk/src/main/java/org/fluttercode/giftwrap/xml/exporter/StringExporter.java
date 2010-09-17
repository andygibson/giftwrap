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

import org.fluttercode.giftwrap.xml.DocumentNode;

/**
 * @author Andy Gibson
 * 
 */
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
