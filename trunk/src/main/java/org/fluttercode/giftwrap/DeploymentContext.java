package org.fluttercode.giftwrap;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

public interface DeploymentContext {

	void addPackage(Package pack, boolean recursive);
	Map<String,Object> getParameterMap();

	void addClass(Class<?> clazz);
	void addClass(String name);
	boolean contains(Class<?> clazz);	

	void addManifestResource(String content, String name);
	void addManifestResource(File file, String name);
	void addManifestResource(URL url, String name);
	
	void addPartialManifestResource(String name,String content,int order);
	void addPartialResource(String name,String content,int order);
	void startConstruction();
	void endConstruction();
	
	List<String> getProfiles();
	
}
