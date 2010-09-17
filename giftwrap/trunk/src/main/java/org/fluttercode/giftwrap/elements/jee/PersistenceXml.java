package org.fluttercode.giftwrap.elements.jee;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.impl.AbstractArchiveElement;
import org.fluttercode.giftwrap.impl.producer.MultiLineContentProducer;

public class PersistenceXml extends AbstractArchiveElement {

	private Map<String, PersistenceUnit> units = new HashMap<String, PersistenceUnit>();

	public PersistenceUnit addUnit(String name) {
		PersistenceUnit unit = new PersistenceUnit();
		units.put(name, unit);
		return unit;
	}

	public PersistenceUnit find(String unitName) {
		PersistenceUnit unit = units.get(unitName);
		if (unit == null) {
			unit = addUnit(unitName);
		}
		return unit;
	}

	@Override
	public void doAppend(DeploymentContext context) {
		context.addManifestResource(produceXml(context), "persistence.xml");
	}
	public String produceXml(DeploymentContext context) {
		
		MultiLineContentProducer producer = new MultiLineContentProducer();
		producer.multiLine();
		

		producer.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		producer.add("<persistence version=\"2.0\"");
		producer.add("	xmlns=\"http://java.sun.com/xml/ns/persistence\"");
		producer
				.add("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		producer.add("	xsi:schemaLocation=\"");
		producer.add("      http://java.sun.com/xml/ns/persistence");
		producer
				.add("      http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd\">");

		//add the combined persistence units to the final persistence.xml file
		for (Entry<String, PersistenceUnit> entry : units.entrySet()) {
			PersistenceUnit unit = new PersistenceUnit();
			entry.getValue().mergeInto(unit,context);
			System.out.println("Final unit = "+unit.toString());
			unit.writeUnit(producer, entry.getKey());			
		}
		producer.add("</persistence>");
		return new String(producer.produce());		
	}

}
