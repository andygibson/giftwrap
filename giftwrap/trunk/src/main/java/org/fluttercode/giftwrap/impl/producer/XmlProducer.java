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

package org.fluttercode.giftwrap.impl.producer;

import org.fluttercode.giftwrap.xml.DocumentNode;
import org.fluttercode.giftwrap.xml.exporter.StringExporter;

/**
 * @author Andy Gibson
 * 
 */
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
