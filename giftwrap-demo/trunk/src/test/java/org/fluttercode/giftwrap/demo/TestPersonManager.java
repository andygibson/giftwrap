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

package org.fluttercode.giftwrap.demo;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fluttercode.giftwrap.demo.bean.DefaultRatingProducer;
import org.fluttercode.giftwrap.demo.bean.NorthEastRatingProducer;
import org.fluttercode.giftwrap.demo.bean.PersonManager;
import org.fluttercode.giftwrap.elements.jee.BeansXml;
import org.fluttercode.giftwrap.impl.ArchiveRoot;
import org.fluttercode.giftwrap.shrinkwrap.ArchiveDeployment;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andy Gibson
 * 
 */
@RunWith(Arquillian.class)
/**
 * @author Andy Gibson
 * 
 */
public class TestPersonManager {

	@Inject
	private PersonManager manager;

	@Deployment
	public static Archive<?> createDeploymentPackage() {

		ArchiveRoot root = new ArchiveRoot();
		// this adds the person manager and everything it needs
		root.addClass(PersonManager.class, true);

		// two implementations of the same interface (not referenced in code,
		// injected only so we can't automatically pick it up
		root.addClass(DefaultRatingProducer.class);
		root.addClass(NorthEastRatingProducer.class);

		// define beans.xml
		root.addElement(new BeansXml().add(
				new BeansXml().addAlternative(DefaultRatingProducer.class)
						.excludeFrom("NE")).add(
				new BeansXml().addAlternative(NorthEastRatingProducer.class).includeIn("NE")));

		//Create the archive for the North Easy profile		
		JavaArchive ar = ArchiveDeployment.generate(root, "NE");
		return ar;

	}

	@Test
	public void testManager() {
		manager.applyRating();
		assertEquals(1, manager.getPerson().getRating());
	}

}
