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

import org.fluttercode.giftwrap.impl.ArchiveRoot;
import org.fluttercode.giftwrap.impl.ElementContainer;
import org.fluttercode.giftwrap.shrinkwrap.ArchiveDeployment;
import org.fluttercode.giftwrap.testmodel.Car;
import org.fluttercode.giftwrap.testmodel.Company;
import org.fluttercode.giftwrap.testmodel.Person;
import org.junit.Test;
import static  org.junit.Assert.assertTrue;
import static  org.junit.Assert.assertFalse;

/**
 * @author Andy Gibson
 * 
 */
public class TestDSL {

	@Test
	public void testDsl() {
		ArchiveRoot root = new ArchiveRoot();
		root.addElement(new ElementContainer()
				    .addClass(Person.class)
				    .addClass(Car.class)
				    .addIncludeProfile("test")
					.addIncludeProfile("test2")
					)
		.addElement(new ElementContainer()
					.addClass(Company.class)
					.addIncludeProfile("test2"));

		MockDeploymentContext dc = new MockDeploymentContext();
		root.produceDeployment(dc);
		assertFalse(dc.contains(Car.class));
		assertFalse(dc.contains(Person.class));
		assertFalse(dc.contains(Company.class));
		
		dc.addProfile("test");
		root.produceDeployment(dc);
		assertTrue(dc.contains(Car.class));
		assertTrue(dc.contains(Person.class));
		assertFalse(dc.contains(Company.class));

		dc.addProfile("test2");
		root.produceDeployment(dc);
		assertTrue(dc.contains(Car.class));
		assertTrue(dc.contains(Person.class));
		assertTrue(dc.contains(Company.class));
		
		dc.getProfiles().clear();
		dc.addProfile("test2");
		root.produceDeployment(dc);
		assertTrue(dc.contains(Car.class));
		assertTrue(dc.contains(Person.class));
		assertTrue(dc.contains(Company.class));
		

	}
}
