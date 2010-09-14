package org.fluttercode.giftwrap;

import org.fluttercode.giftwrap.AbstractArchiveElement;
import org.fluttercode.giftwrap.ArchiveElement;
import org.fluttercode.giftwrap.ArchiveRoot;
import org.fluttercode.giftwrap.DeploymentContext;
import org.fluttercode.giftwrap.elements.ClassElement;
import org.fluttercode.giftwrap.elements.PartialManifestFileElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestProfiles {

	private MockDeploymentContext dc;
	private ArchiveRoot root;

	@Before
	public void doInit() {
		dc = new MockDeploymentContext();
		root = new ArchiveRoot();
	}

	@Test
	public void testProfileActiveByDefault() {
		ArchiveElement element = new ClassElement(TestProfiles.class);
		Assert.assertTrue(element.isActive(dc));		
	}

	@Test
	public void testProfileInactiveExclusion() {
		ArchiveElement element = new ClassElement(TestProfiles.class);
		element.addExcludeProfile("SOMEVALUE");
		Assert.assertTrue(element.isActive(dc));		
	}

	@Test
	public void testProfileActiveOnDemand() {
		ArchiveElement element = new ClassElement(TestProfiles.class);
		element.addIncludeProfile("PROFILE");
		Assert.assertFalse(element.isActive(dc));
		dc.getProfiles().add("PROFILE");
		Assert.assertTrue(element.isActive(dc));
	}

	@Test
	public void testPartialFileProfile() {
		final String PROFILE = "SOME_PROFILE";
		root.addElement(new PartialManifestFileElement("test", "<beans>",-1000)
		.excludeNewLine());
		//root.addElement(new PartialManifestFileElement("test", "<somexml>").excludeNewLine().addIncludeProfile("USE_ALTERNATES"));
		ArchiveElement elem = new PartialManifestFileElement("test", "<somexml>").excludeNewLine().addIncludeProfile(PROFILE); 
		root.addElement(elem);
		root.addElement(new PartialManifestFileElement("test", "</beans>",1000).excludeNewLine());
		
		//check the middle doesn't appear in the final document
		root.fillArchive(dc);
		String s = dc.getManifestPartialFiles().buildFileContent("test");
		Assert.assertNotNull(s);
		Assert.assertEquals("<beans></beans>",s);
		dc.reset();
		dc.getProfiles().add(PROFILE);
		Assert.assertTrue(elem.isActive(dc));
		root.fillArchive(dc);
		s = dc.getManifestPartialFiles().buildFileContent("test");
		System.out.println("XML + "+s);
		Assert.assertEquals("<beans><somexml></beans>",s);
		

	}

}
