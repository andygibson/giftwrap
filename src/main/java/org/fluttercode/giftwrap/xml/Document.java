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

package org.fluttercode.giftwrap.xml;

import org.fluttercode.giftwrap.xml.exporter.DocumentExporter;

/**
 * @author Andy Gibson
 * 
 */
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
