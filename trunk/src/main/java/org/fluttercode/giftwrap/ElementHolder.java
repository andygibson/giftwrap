package org.fluttercode.giftwrap;

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.giftwrap.elements.ClassElement;
import org.fluttercode.giftwrap.elements.Packages;

public class ElementHolder implements ArchiveElement {

	List<ArchiveElement> elements = new ArrayList<ArchiveElement>();

	public void append(DeploymentContext context) {
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
