package org.fluttercode.giftwrap.elements.jee;

import org.fluttercode.giftwrap.elements.jee.BeansXml;
import org.fluttercode.giftwrap.elements.jee.PersistenceXml;
import org.fluttercode.giftwrap.impl.ArchiveRoot;

public class JeeArchive extends ArchiveRoot {

	private BeansXml beans = new BeansXml();
	private PersistenceXml persistence = new PersistenceXml();

	public JeeArchive() {
		addElement(beans);
		addElement(persistence);
	}

	public BeansXml getBeans() {
		return beans;
	}
	
	public PersistenceXml getPersistence() {
		return persistence;
	}
}
