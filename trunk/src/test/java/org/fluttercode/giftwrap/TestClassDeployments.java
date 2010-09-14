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

import org.fluttercode.giftwrap.ArchiveRoot;
import org.fluttercode.giftwrap.testmodel.Car;
import org.fluttercode.giftwrap.testmodel.Company;
import org.fluttercode.giftwrap.testmodel.Person;
import org.fluttercode.giftwrap.testmodel.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andy Gibson
 * 
 */
public class TestClassDeployments {

	private MockDeploymentContext dc;
	private ArchiveRoot root;

	@Before
	public void doInit() {
		dc = new MockDeploymentContext();
		root = new ArchiveRoot();
	}

	@Test
	public void testSimpleDeployment() {
		root.addClass(Person.class);
		
		root.fillArchive(dc);
		Assert.assertTrue(dc.contains(Person.class));
	}
	
	@Test
	public void testRecursiveClassDeployment() {
		root.addClass(Person.class,true);
		root.fillArchive(dc);
		Assert.assertTrue(dc.contains(Person.class));
		Assert.assertTrue(dc.contains(Car.class));
		Assert.assertTrue(dc.contains(Company.class));
		Assert.assertTrue(dc.contains(Vehicle.class));
	}
	

}
