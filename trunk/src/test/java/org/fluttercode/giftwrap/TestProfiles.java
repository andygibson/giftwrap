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

import org.fluttercode.giftwrap.AbstractArchiveElement;
import org.fluttercode.giftwrap.ArchiveElement;
import org.fluttercode.giftwrap.ArchiveRoot;
import org.fluttercode.giftwrap.DeploymentContext;
import org.fluttercode.giftwrap.elements.ClassElement;
import org.fluttercode.giftwrap.elements.PartialManifestFileElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andy Gibson
 * 
 */
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
