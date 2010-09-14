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

package org.fluttercode.giftwrap;

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.giftwrap.elements.ClassElement;
import org.fluttercode.giftwrap.elements.Packages;

/**
 * @author Andy Gibson
 * 
 */
public class ElementHolder extends AbstractArchiveElement {

	List<ArchiveElement> elements = new ArrayList<ArchiveElement>();

	@Override
	public void doAppend(DeploymentContext context) {
		for (ArchiveElement element : elements) {
			element.append(context);
		}		
	}

	public ElementHolder addElement(ArchiveElement element) {
		elements.add(element);
		return this;
	}

	public ElementHolder removeElement(ArchiveElement element) {
		elements.remove(element);
		return this;
	}

	public ElementHolder addClass(Class<?> clazz) {
		return addClass(clazz, false);
	}

	public ElementHolder addClass(Class<?> clazz, boolean recursive) {
		return addElement(new ClassElement(clazz, recursive));
	}

	public ElementHolder addPackage(Package pkge, boolean recursive) {
		return addElement(new Packages(pkge, recursive));
	}

	public ElementHolder addPackage(Package pkge) {
		return addElement(new Packages(pkge));
	}
	
	public ElementHolder addPackage(String packageName) {
		Package p = Package.getPackage(packageName);
		if (p == null) {
			throw new IllegalArgumentException("Package "+packageName+" not found");
		}
		return addPackage(p);
	}

}
