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

import org.fluttercode.giftwrap.api.ArchiveElement;
import org.fluttercode.giftwrap.demo.bean.DefaultRatingProducer;
import org.fluttercode.giftwrap.demo.bean.NorthEastRatingProducer;
import org.fluttercode.giftwrap.demo.bean.PersonManager;
import org.fluttercode.giftwrap.elements.PartialManifestFileElement;
import org.fluttercode.giftwrap.impl.ArchiveRoot;
import org.fluttercode.giftwrap.shrinkwrap.ArchiveDeployment;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
/**
 * @author Andy Gibson
 * 
 */
public class TestPersonManager {

	private final static String BEANS_FILE = "beans.xml";
	
	@Inject
	private PersonManager manager;
	
	
	public static Archive<?> createDeploy() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar");
	}
	
	@Deployment
	public static Archive<?> createDeploymentPackage() {
		
		ArchiveRoot root = new ArchiveRoot();		
		root.addClass(PersonManager.class,true);
		root.addClass(DefaultRatingProducer.class);
		root.addClass(NorthEastRatingProducer.class);
		
				
		
		root.addElement(new PartialManifestFileElement(BEANS_FILE, "<beans>",-99))
		.addElement(new PartialManifestFileElement(BEANS_FILE, "<alternatives>",-10))
		.addElement(new PartialManifestFileElement(BEANS_FILE, "<class>org.fluttercode.giftwrap.demo.bean.NorthEastRatingProducer</class>",0).addIncludeProfile("NE"))				
		.addElement(new PartialManifestFileElement(BEANS_FILE, "<class>org.fluttercode.giftwrap.demo.bean.DefaultRatingProducer</class>",0).addExcludeProfile("NE"))
		.addElement(new PartialManifestFileElement(BEANS_FILE, "</alternatives>",10))
		.addElement(new PartialManifestFileElement(BEANS_FILE, "</beans>",100));

		//ArchiveDeployment dp = new ArchiveDeployment();
		//dp.addProfile("NE");
		
		JavaArchive ar = ArchiveDeployment.generate(root);
		System.out.println("Archive  = "+ar);
		return ar;
		
	}

	@Test
	public void testManager() {
		manager.applyRating();
		assertEquals(28, manager.getPerson().getRating());
	}
	
}
