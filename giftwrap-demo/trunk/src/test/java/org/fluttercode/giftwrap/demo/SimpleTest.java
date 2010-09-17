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

import static org.fluttercode.giftwrap.xml.DocumentFactory.node;
import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fluttercode.giftwrap.api.ArchiveElement;
import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.demo.bean.DefaultRatingProducer;
import org.fluttercode.giftwrap.demo.bean.NorthEastRatingProducer;
import org.fluttercode.giftwrap.demo.bean.PersonManager;
import org.fluttercode.giftwrap.demo.model.Person;
import org.fluttercode.giftwrap.demo.model.RatingProducer;
import org.fluttercode.giftwrap.elements.ManifestResource;
import org.fluttercode.giftwrap.elements.jee.BeansXml;
import org.fluttercode.giftwrap.impl.ArchiveRoot;
import org.fluttercode.giftwrap.shrinkwrap.ArchiveDeployment;
import org.fluttercode.giftwrap.xml.Document;
import org.fluttercode.giftwrap.xml.DocumentNode;
import org.fluttercode.giftwrap.xml.exporter.DocumentExporter;
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
public class SimpleTest {


	@Inject
	private PersonManager personManager;

	@Deployment
	public static Archive<?> createDeploy() {
		String beans = "<beans><alternatives><class>"+DefaultRatingProducer.class.getName()+"</class></alternatives></beans>";
		return ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addManifestResource(new ByteArrayAsset(beans.getBytes()),ArchivePaths.create("beans.xml"))
				.addPackages(true,DefaultRatingProducer.class.getPackage())
				.addPackages(true,Person.class.getPackage());
				
				
	}

	public static Archive<?> createDeploymentPackage() {

		ArchiveRoot root = new ArchiveRoot();

		// this adds the person manager and everything it needs
		root.addClass(PersonManager.class, true);

		// two implementations of the same interface (not referenced in code,
		// injected only so we can't automatically pick it up
		root.addClass(DefaultRatingProducer.class);

		// define beans.xml
		new BeansXml().addAlternative(DefaultRatingProducer.class).addTo(root);

		// create the list of archive
		JavaArchive ar = ArchiveDeployment.generate(root);
		return ar;

	}

	// @Deployment
	public static Archive<?> workingDeploymentPackage() {

		ArchiveRoot root = new ArchiveRoot();
		root.addClass(PersonManager.class, true);
		root.addClass(DefaultRatingProducer.class);
		root.addClass(RatingProducer.class);
		root.addClass(ArchiveElement.class);
		root.addClass(NorthEastRatingProducer.class);
		root.addPackage(DocumentNode.class.getPackage());

		root.addClass(DeploymentContext.class);
		root.addClass(DocumentExporter.class);
		Document beans = new Document("beans.xml");
		beans
				.add(node("alternatives")
						.add(
								node("class")
										.value(
												"org.fluttercode.giftwrap.demo.bean.DefaultRatingProducer")));

		root.addElement(new ManifestResource("beans.xml", beans));
		JavaArchive ar = ArchiveDeployment.generate(root);
		return ar;

	}

	@Test
	public void testManager() {
		Assert.assertNotNull(personManager);
		personManager.applyRating();
		assertEquals(28, personManager.getPerson().getRating());
	}

	public static Document getBeansDoc() {
		Document beans = new Document("beans.xml");
		beans
				.add(node("alternatives")
						.add(
								node("class")
										.value(
												"org.fluttercode.giftwrap.demo.bean.DefaultRatingProducer")));

		return beans;
	}

}
