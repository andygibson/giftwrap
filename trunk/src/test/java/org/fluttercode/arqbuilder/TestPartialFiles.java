package org.fluttercode.arqbuilder;

import org.fluttercode.giftwrap.ArchiveElement;
import org.fluttercode.giftwrap.ArchiveRoot;
import org.fluttercode.giftwrap.DeploymentContext;
import org.fluttercode.giftwrap.PartialFileList;
import org.fluttercode.giftwrap.elements.PartialManifestFileElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		root.addElement(new PartialManifestFileElement("testResource","abc").excludeNewLine());
		root.addElement(new PartialManifestFileElement("testResource","123").excludeNewLine());
		
		//get element
		root.fillArchive(dc);
		PartialFileList files = dc.getManifestPartialFiles();
		Assert.assertNotNull(files);
		String s= files.buildFileContent("testResource");
		
		Assert.assertEquals("abc123", s);
	}
	
	@Test
	public void testPartialFileOrdering() {
		root.addElement(new PartialManifestFileElement("testResource","abc",5).excludeNewLine());
		root.addElement(new PartialManifestFileElement("testResource","123",0).excludeNewLine());
		
		//get element
		root.fillArchive(dc);
		PartialFileList files = dc.getManifestPartialFiles();
		Assert.assertNotNull(files);
		String s= files.buildFileContent("testResource");
		Assert.assertNotNull(s);
		Assert.assertEquals("123abc",s);

	}

}

