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

package org.fluttercode.giftwrap;

import org.fluttercode.giftwrap.elements.PartialManifestFileElement;
import org.fluttercode.giftwrap.impl.ArchiveRoot;
import org.fluttercode.giftwrap.impl.PartialFileList;
import org.fluttercode.giftwrap.impl.producer.StringContentProducer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andy Gibson
 * 
 */
public class TestPartialFiles {

	private MockDeploymentContext dc;
	private ArchiveRoot root;

	@Before
	public void doInit() {
		dc = new MockDeploymentContext();
		root = new ArchiveRoot();
	}
	
	@Test
	public void testPartialFile() {
		root.addElement(new PartialManifestFileElement("testResource",new StringContentProducer("abc")));
		root.addElement(new PartialManifestFileElement("testResource",new StringContentProducer("123")));
		root.addElement(new PartialManifestFileElement("testResource",new StringContentProducer("xyz")));
		
		//get element
		root.produceDeployment(dc);
		PartialFileList files = dc.getManifestPartialFiles();
		Assert.assertNotNull(files);
		String s= files.buildFileContent("testResource");
		
		Assert.assertEquals("abc123xyz", s);
	}
	
	@Test
	public void testPartialFileOrdering() {
		root.addElement(new PartialManifestFileElement("testResource","abc",5));
		root.addElement(new PartialManifestFileElement("testResource","123",0));
		
		//get element
		root.produceDeployment(dc);
		PartialFileList files = dc.getManifestPartialFiles();
		Assert.assertNotNull(files);
		String s= files.buildFileContent("testResource");
		Assert.assertNotNull(s);
		Assert.assertEquals("123abc",s);

	}

}

