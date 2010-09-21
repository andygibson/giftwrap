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
import org.fluttercode.giftwrap.demo.bean.PersonManager;
import org.fluttercode.giftwrap.demo.model.Person;
import org.fluttercode.giftwrap.demo.model.RatingProducer;
import org.fluttercode.giftwrap.elements.jee.BeansXml;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * Simple test class the doesn't have any profiles
 * 
 * @author Andy Gibson
 * 
 */
@RunWith(Arquillian.class)
public class ShrinkwrapTest {


	@Inject
	private PersonManager personManager;

	@Deployment
	public static Archive<?> createDeploy() {
		//hard coded alternatives
		String beans = "<beans><alternatives><class>"+DefaultRatingProducer.class.getName()+"</class></alternatives></beans>";
		BeansXml b = new BeansXml().addAlternative(RatingProducer.class);
		
		return ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addManifestResource(new ByteArrayAsset(beans.getBytes()),ArchivePaths.create("beans.xml"))
				.addPackages(true,DefaultRatingProducer.class.getPackage())
				.addPackages(true,Person.class.getPackage());
				
				
	
		
	}


	@Test
	public void testManager() {
		Assert.assertNotNull(personManager);
		personManager.applyRating();
		assertEquals(28, personManager.getPerson().getRating());
	}


}
