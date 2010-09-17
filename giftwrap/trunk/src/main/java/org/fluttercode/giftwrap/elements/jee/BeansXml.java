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

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.impl.NamedResourceElement;
import org.fluttercode.giftwrap.impl.producer.MultiLineContentProducer;

/**
 * @author Andy Gibson
 * 
 */
public class BeansXml extends NamedResourceElement {

	public BeansXml() {
		super("beans.xml");
	}

	private List<Class<?>> alternatives = new ArrayList<Class<?>>();
	private List<Class<?>> decorators = new ArrayList<Class<?>>();
	private List<Class<?>> interceptors = new ArrayList<Class<?>>();
	private List<BeansXml> children = new ArrayList<BeansXml>();

	@Override
	public void doAppend(DeploymentContext context) {
		BeansXml build = new BeansXml();
		mergeBeansXml(build,context);
		context.addManifestResource(build.buildAsset(), getName());
	}

	private byte[] buildAsset() {
		MultiLineContentProducer prod = new MultiLineContentProducer();
		prod.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		prod.add("<beans xmlns=\"http://java.sun.com/xml/ns/javaee\"");
		prod.add("   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		prod.add("   xsi:schemaLocation=\"");
		prod.add("      http://java.sun.com/xml/ns/javaee");
		prod.add("      http://java.sun.com/xml/ns/javaee/beans_1_0.xsd\">");
		addClasses(alternatives, prod, "alternatives");
		addClasses(decorators, prod, "decorators");
		addClasses(interceptors, prod, "interceptors");
		prod.add("</beans>");
		return prod.produce();
	}

	private void addClasses(List<Class<?>> classes,
			MultiLineContentProducer producer, String tag) {
		producer.add("   <" + tag + ">");
		for (Class<?> c : classes) {
			producer.add(String.format("      <class>%s</class>", c.getName()));
		}
		producer.add("   </" + tag + ">");
	}

	public BeansXml addAlternative(Class<?> clazz) {
		if (!alternatives.contains(clazz)) {
			alternatives.add(clazz);
		}
		return this;
	}

	public BeansXml removeAlternative(Class<?> clazz) {
		alternatives.remove(clazz);
		return this;
	}

	public BeansXml addDecorator(Class<?> clazz) {
		if (!decorators.contains(clazz)) {
			decorators.add(clazz);
		}
		return this;
	}

	public BeansXml removeDecorator(Class<?> clazz) {
		decorators.remove(clazz);
		return this;
	}

	public BeansXml addInterceptor(Class<?> clazz) {
		if (!interceptors.contains(clazz)) {
			interceptors.add(clazz);
		}
		return this;
	}

	public BeansXml removeInterceptor(Class<?> clazz) {
		interceptors.remove(clazz);
		return this;
	}

	public BeansXml add(BeansXml child) {
		children.add(child);
		return this;
	}

	/**
	 * Merges the contents of this beans.xml to the one passed in
	 * 
	 * @param beans
	 */
	protected void mergeBeansXml(BeansXml beans, DeploymentContext context) {
		if (beans == null) {
			return;
		}
		mergeList(alternatives, beans.alternatives);
		mergeList(decorators, beans.decorators);
		mergeList(interceptors, beans.interceptors);
		for (BeansXml child : children) {
			if (child.isActive(context)) {
				child.mergeBeansXml(beans,context);
			}
		}
	}

	/**
	 * Mergers the list of classes over from one list to another but only adds
	 * the items not already present in the list. Creates a unique list of
	 * values
	 * 
	 * @param source
	 *            List of items to add from
	 * @param target
	 *            List of items to add to
	 */
	protected void mergeList(List<Class<?>> source, List<Class<?>> target) {
		for (Class<?> c : source) {
			if (!target.contains(c)) {
				target.add(c);
			}
		}
	}

	public BeansXml includeIn(String profile) {
		super.includeIn(profile);
		return this;
	}

	public BeansXml excludeFrom(String profile) {
		super.excludeFrom(profile);
		return this;
	}

}
