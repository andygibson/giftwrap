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

package org.fluttercode.giftwrap.impl;

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.giftwrap.api.ArchiveElement;
import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.elements.ClassElement;
import org.fluttercode.giftwrap.elements.Packages;

/**
 * @author Andy Gibson
 * 
 */
public class ElementContainer extends AbstractArchiveElement{

	List<ArchiveElement> elements = new ArrayList<ArchiveElement>();

	@Override
	public void doAppend(DeploymentContext context) {
		for (ArchiveElement element : elements) {
			element.append(context);
		}		
	}

	public ElementContainer addElement(ArchiveElement element) {
		elements.add(element);
		return this;
	}

	public ElementContainer removeElement(ArchiveElement element) {
		elements.remove(element);
		return this;
	}

	public ElementContainer addClass(Class<?> clazz) {
		return addClass(clazz, false);
	}

	public ElementContainer addClass(Class<?> clazz, boolean recursive) {
		return addElement(new ClassElement(clazz, recursive));
	}

	public ElementContainer addPackage(Package pkge, boolean recursive) {
		return addElement(new Packages(pkge, recursive));
	}

	public ElementContainer addPackage(Package pkge) {
		return addElement(new Packages(pkge));
	}
	
	public ElementContainer addPackage(String packageName) {
		Package p = Package.getPackage(packageName);
		if (p == null) {
			throw new IllegalArgumentException("Package "+packageName+" not found");
		}
		return addPackage(p);
	}

}
