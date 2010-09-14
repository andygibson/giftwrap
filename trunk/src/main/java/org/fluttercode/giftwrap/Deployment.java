package org.fluttercode.giftwrap;

public interface Deployment<T> {

	
	boolean addPackage(Package pack);
	boolean addClass(Class<?> clazz);
	
}
