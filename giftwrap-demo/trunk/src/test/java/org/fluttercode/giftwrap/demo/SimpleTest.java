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
import static org.fluttercode.giftwrap.xml.DocumentFactory.*;

import java.util.Map;

import javax.inject.Inject;

import org.fluttercode.giftwrap.api.ArchiveElement;
import org.fluttercode.giftwrap.demo.bean.DefaultRatingProducer;
import org.fluttercode.giftwrap.demo.bean.PersonManager;
import org.fluttercode.giftwrap.elements.ManifestResource;
import org.fluttercode.giftwrap.impl.ArchiveRoot;
import org.fluttercode.giftwrap.shrinkwrap.ArchiveDeployment;
import org.fluttercode.giftwrap.xml.Document;
import org.fluttercode.giftwrap.xml.DocumentNode;
import org.fluttercode.giftwrap.xml.DocumentNodeAttribute;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
/**
 * @author Andy Gibson
 * 
 */
public class SimpleTest {

	private final static String BEANS_FILE = "beans.xml";

	@Inject
	private PersonManager personManager;

	@Deployment
	public static Archive<?> createDeploy() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar").addPackage(
				ArchiveElement.class.getPackage())
				.addClass(PersonManager.class);
	}

	@Deployment
	public static Archive<?> createDeploymentPackage() {

		ArchiveRoot root = new ArchiveRoot();
		root.addClass(PersonManager.class, false);
		root.addClass(DefaultRatingProducer.class);
		root.addPackage(DocumentNode.class.getPackage());
		root.addClass(DocumentNode.class);
		root.addClass(Document.class);
		root.addClass(DocumentNodeAttribute.class);
		
		// root.addClass(ArchiveElement.class);
		// root.addClass(DeploymentContext.class);
		// root.addClass(ElementContainer.class,true);
		// root.addClass(NorthEastRatingProducer.class);
		// root.addClass(DefaultRatingProducer.class);
		// root.addElement(new BeansXmlElement());

		Document beans = new Document("beans.xml");
		beans
				.add(node("alternatives")
						.add(
								node("class")
										.value(
												"org.fluttercode.giftwrap.demo.bean.DefaultRatingProducer")));

		root.addElement(new ManifestResource("beans.xml", beans));

		/*
		 * root.addElement(new PartialManifestFileElement(BEANS_FILE,
		 * "<beans>",-99)) .addElement(new
		 * PartialManifestFileElement(BEANS_FILE, "<alternatives>",-10))
		 * .addElement(new PartialManifestFileElement(BEANS_FILE,
		 * "<class>org.fluttercode.giftwrap.demo.bean.NorthEastRatingProducer</class>"
		 * ,0).addIncludeProfile("NE")) .addElement(new
		 * PartialManifestFileElement(BEANS_FILE,
		 * "<class>org.fluttercode.giftwrap.demo.bean.DefaultRatingProducer</class>"
		 * ,0).addExcludeProfile("NE")) .addElement(new
		 * PartialManifestFileElement(BEANS_FILE, "</alternatives>",10))
		 * .addElement(new PartialManifestFileElement(BEANS_FILE,
		 * "</beans>",100));
		 */

		// ArchiveDeployment dp = new ArchiveDeployment();
		// dp.addProfile("NE");

		JavaArchive ar = ArchiveDeployment.generate(root);
		System.out.println("Archive  = " + ar);
		Map<ArchivePath, Node> map = ar.getContent();
		for (ArchivePath path : map.keySet()) {
			System.out.println(path + " - " + map.get(path));
		}
		return ar;

	}

	@Test
	public void testManager() {
		Assert.assertNotNull(personManager);
		personManager.applyRating();
		assertEquals(28, personManager.getPerson().getRating());
	}

}
