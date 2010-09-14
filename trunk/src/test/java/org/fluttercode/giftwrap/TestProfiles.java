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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.fluttercode.giftwrap.elements.ClassElement;
import org.fluttercode.giftwrap.elements.PartialManifestFileElement;
import org.fluttercode.giftwrap.testmodel.Car;
import org.fluttercode.giftwrap.testmodel.Person;
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
		assertTrue(element.isActive(dc));		
	}

	@Test
	public void testProfileInactiveExclusion() {
		ArchiveElement element = new ClassElement(TestProfiles.class);
		element.addExcludeProfile("SOMEVALUE");
		assertTrue(element.isActive(dc));		
	}

	@Test
	public void testProfileActiveOnDemand() {
		ArchiveElement element = new ClassElement(TestProfiles.class);
		element.addIncludeProfile("PROFILE");
		assertFalse(element.isActive(dc));
		dc.getProfiles().add("PROFILE");
		assertTrue(element.isActive(dc));
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
		root.produceDeployment(dc);
		String s = dc.getManifestPartialFiles().buildFileContent("test");
		assertNotNull(s);
		assertEquals("<beans></beans>",s);
		
		dc.reset();
		
		//check that the middle now appears since we have the profile included.
		dc.getProfiles().add(PROFILE);
		assertTrue(elem.isActive(dc));
		root.produceDeployment(dc);
		s = dc.getManifestPartialFiles().buildFileContent("test");
		
		assertEquals("<beans><somexml></beans>",s);
	}
	
	@Test
	public void testProfileExlusions() {

		ArchiveElement element = new ClassElement(this.getClass());
		element.addIncludeProfile("USE");
		root.addElement(element);
		dc.getProfiles().add("IGNORE");
		dc.getProfiles().add("USE");
		root.produceDeployment(dc);
		assertTrue(element.isActive(dc));
		assertTrue(dc.getClassesAdded().contains(this.getClass()));
		
		dc.reset();
		
		element.addExcludeProfile("IGNORE");
		assertFalse(element.isActive(dc));
		assertFalse(dc.getClassesAdded().contains(this.getClass()));
		
	}
	
	@Test
	public void testContainerBranching() {

		ElementContainer container1 = new ElementContainer();
		ElementContainer container2 = new ElementContainer();
		
		ArchiveElement element1 = new ClassElement(Person.class);
		ArchiveElement element2 = new ClassElement(Car.class);
		
		container1.addElement(element1);
		container2.addElement(element2);
		
		container1.addIncludeProfile("C1");
		container2.addIncludeProfile("C2");
		
		root.addElement(container1);
		root.addElement(container2);

		//check they are both included
		assertFalse(container1.isActive(dc));
		assertFalse(container2.isActive(dc));
		assertTrue(element1.isActive(dc));
		assertTrue(element2.isActive(dc));
		
		root.produceDeployment(dc);
		assertFalse(dc.contains(Person.class));
		assertFalse(dc.contains(Car.class));

		dc.addProfile("C2");
		root.produceDeployment(dc);

		assertFalse(container1.isActive(dc));
		assertTrue(container2.isActive(dc));
		assertTrue(element1.isActive(dc));
		assertTrue(element2.isActive(dc));

		assertFalse(dc.contains(Person.class));
		assertTrue(dc.contains(Car.class));
		
	}

	
	

}
