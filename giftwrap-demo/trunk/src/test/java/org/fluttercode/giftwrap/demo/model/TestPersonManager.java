package org.fluttercode.giftwrap.demo.model;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fluttercode.giftwrap.demo.bean.DefaultRatingProducer;
import org.fluttercode.giftwrap.demo.bean.NorthEastRatingProducer;
import org.fluttercode.giftwrap.demo.bean.PersonManager;
import org.fluttercode.giftwrap.elements.BeansXmlElement;
import org.fluttercode.giftwrap.elements.PartialManifestFileElement;
import org.fluttercode.giftwrap.impl.ArchiveRoot;
import org.fluttercode.giftwrap.shrinkwrap.ArchiveDeployment;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TestPersonManager {

	private final static String BEANS_FILE = "beans.xml";
	
	@Inject
	private PersonManager manager;
	
	@Deployment
	public static Archive<?> createDeploymentPackagex() {
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

		ArchiveDeployment dp = new ArchiveDeployment();
		dp.addProfile("NE");
		
		return ArchiveDeployment.generate(root);
	}

	@Test
	public void testManager() {
		manager.applyRating();
		assertEquals(28, manager.getPerson().getRating());
	}
	
}
