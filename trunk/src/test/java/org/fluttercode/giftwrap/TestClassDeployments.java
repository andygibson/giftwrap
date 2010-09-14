package org.fluttercode.giftwrap;

import org.fluttercode.giftwrap.ArchiveRoot;
import org.fluttercode.giftwrap.testmodel.Car;
import org.fluttercode.giftwrap.testmodel.Company;
import org.fluttercode.giftwrap.testmodel.Person;
import org.fluttercode.giftwrap.testmodel.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
