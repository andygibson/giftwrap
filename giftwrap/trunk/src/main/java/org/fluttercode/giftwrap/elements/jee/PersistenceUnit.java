package org.fluttercode.giftwrap.elements.jee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.impl.ProfileHolder;
import org.fluttercode.giftwrap.impl.producer.MultiLineContentProducer;

public class PersistenceUnit {

	public enum TransactionType {
		JTA("JTA"), RESOURCE_LOCAL("RESOURCE_LOCAL");

		private String xml;

		TransactionType(String xml) {
			this.xml = xml;
		}
	};

	private Logger log = Logger.getLogger(PersistenceUnit.class.getName());

	private List<PersistenceUnit> children = new ArrayList<PersistenceUnit>();

	private Map<String, String> properties = new HashMap<String, String>();
	private String jtaDataSource;
	private String provider;
	private ProfileHolder profileInfo = new ProfileHolder();
	private TransactionType transactionType;
	private List<String> classes = new ArrayList<String>();

	public Map<String, String> getProperties() {
		return properties;
	}

	public PersistenceUnit set(String key, String value) {
		properties.put(key, value);
		return this;
	}

	public PersistenceUnit dataSource(String name) {
		jtaDataSource = name;
		return this;
	}

	public PersistenceUnit provider(String name) {
		this.provider = name;
		return this;
	}

	public PersistenceUnit useHibernate() {
		return provider("org.hibernate.ejb.HibernatePersistence");
	}

	public PersistenceUnit useTopLink() {
		return provider("org.eclipse.persistence.jpa.PersistenceProvider");
	}

	public PersistenceUnit includeIn(String profile) {
		profileInfo.addToIncludeProfile(profile);
		return this;
	}

	public PersistenceUnit excludeFrom(String profile) {
		profileInfo.addToExcludeProfile(profile);
		return this;
	}

	public boolean isActive(DeploymentContext context) {
		return profileInfo.isActive(context);
	}

	protected void mergeInto(PersistenceUnit unit,DeploymentContext context) {
		if (!isActive(context)) {
			return;
		}
		// copy properties
		unit.getProperties().putAll(properties);
		// if this version has ds name, overwrite but warn
		unit.jtaDataSource = mergeValue(unit.jtaDataSource, jtaDataSource);
		unit.provider = mergeValue(unit.provider, provider);
		unit.transactionType = mergeValue(unit.transactionType, transactionType);
		//merge classes
		for (String c : classes) {
			if (!unit.classes.contains(c)) {
				unit.classes.add(c);
			}
		}
		// now merge any child units
		for (PersistenceUnit child : children) {
			child.mergeInto(unit,context);
		}
	}

	protected <T> T mergeValue(T current, T newValue) {
		if (current == null) {
			return newValue;
		}

		if (newValue == null) {
			return current;
		}
		// current != null && newvalue != null
		log.info(String.format(
				"Replacing '%s' with value '%s' in persistence unit'", current,
				newValue));
		return newValue;
	}

	public PersistenceUnit add() {
		PersistenceUnit unit = new PersistenceUnit();
		children.add(unit);
		return unit;
	}

	public String getProvider() {
		return provider;
	}

	public String getJtaDataSource() {
		return jtaDataSource;
	}

	public PersistenceUnit localTransactions() {
		transactionType = TransactionType.RESOURCE_LOCAL;
		return this;
	}

	public PersistenceUnit jtaTransactions() {
		transactionType = TransactionType.JTA;
		return this;
	}

	public void writeUnit(MultiLineContentProducer producer, String name) {
		// write unit header
		String transactionText = transactionType == null ? "" : String.format(
				"transaction-type=\"%s\"", transactionType.xml);
		producer.add(String.format("    <persistence-unit name=\"%s\" %s>",
				name, transactionText));

		// write provider
		if (provider != null && provider.length() != 0) {
			String template = "        <provider>%s</provider>";
			producer.add(String.format(template, provider));
		}

		// write jta-datasoruce
		if (jtaDataSource != null && jtaDataSource.length() != 0) {
			String template = "        <jta-data-source>%s</jta-data-source>";
			producer.add(String.format(template, jtaDataSource));
		}

		// write classes
		if (!classes.isEmpty()) {
			producer.add("        <classes>");
			String template = "            <class>%s</class>";
			for (String clazz : classes) {
				producer.add(String.format(template,clazz));
			}
			producer.add("        <classes>");
		}

		// write properties
		producer.add("        <properties>");
		String template = "              <property name=\"%s\" value=\"%s\"/>";
		for (Entry<String, String> entry : properties.entrySet()) {
			String value = String.format(template, entry.getKey(), entry
					.getValue());
			producer.add(value);
		}
		producer.add("        </properties>");

		// write end tag
		producer.add("    </persistence-unit>");
	}

	@Override
	public String toString() {
		return super.toString()
				+ String.format("[ds='%s',transaction='%s',provider='%s']",
						jtaDataSource, transactionType, provider);
	}

	public PersistenceUnit addClass(String className) {
		if (!classes.contains(className)) {
			classes.add(className);
		}
		return this;
	}

	public PersistenceUnit addClass(Class<?> clazz) {
		return addClass(clazz.getName());
	}
	
	protected List<String> getClasses() {
		return classes;
	}
}
