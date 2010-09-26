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

package org.fluttercode.giftwrap.elements.jee;

import static org.junit.Assert.*;

import org.fluttercode.giftwrap.MockDeploymentContext;
import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.testmodel.Person;
import org.junit.Test;


/**
 * @author Andy Gibson
 * 
 */
public class TestPersistence {

	@Test
	public void testPersistenceXml() {
		DeploymentContext context = new MockDeploymentContext();
		PersistenceXml xml = new PersistenceXml();
		xml.addUnit("mainunit")
		  .dataSource("java:/myDS")
		  .useHibernate()
		  .set("hibernate.hbm2ddl.auto", "create-drop")
		  .set("hibernate.show_sql", "true")
		  .set("hibernate.transaction.flush_before_completion", "true")
		  .set("hibernate.cache.provider_class","org.hibernate.cache.HashtableCacheProvider")
		  .add().dataSource("ds2").localTransactions()
		  .addClass(Person.class)
		  .includeIn("test");
		
		PersistenceUnit unit = xml.find("mainunit");
		assertNotNull(unit);

		PersistenceUnit merged = new PersistenceUnit();
		unit.mergeInto(merged, context);
		
		assertEquals(unit.getJtaDataSource(), "java:/myDS");
		assertEquals(0, merged.getClasses().size());
		assertFalse(merged.getClasses().contains(Person.class.getName()));
		
		//now with a profile set
		merged = new PersistenceUnit();
		context.addProfile("test");
		unit.mergeInto(merged, context);
		
		assertEquals("ds2",merged.getJtaDataSource());
		assertEquals(1, merged.getClasses().size());
		assertTrue(merged.getClasses().contains(Person.class.getName()));
		
		
		
		System.out.println(xml.produceXml(context));
	}
}
