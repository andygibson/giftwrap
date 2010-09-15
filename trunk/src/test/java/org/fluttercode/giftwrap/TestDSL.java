package org.fluttercode.giftwrap;

import org.fluttercode.giftwrap.api.ElementContainer;
import org.fluttercode.giftwrap.shrinkwrap.ArchiveDeployment;
import org.fluttercode.giftwrap.testmodel.Car;
import org.fluttercode.giftwrap.testmodel.Company;
import org.fluttercode.giftwrap.testmodel.Person;
import org.junit.Test;
import static  org.junit.Assert.assertTrue;
import static  org.junit.Assert.assertFalse;

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
