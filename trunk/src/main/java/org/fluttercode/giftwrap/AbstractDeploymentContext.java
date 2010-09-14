package org.fluttercode.giftwrap;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDeploymentContext implements DeploymentContext {

	private List<Class<?>> classesAdded = new ArrayList<Class<?>>();

	private PartialFileList resourcePartialFiles = new PartialFileList();
	private PartialFileList manifestPartialFiles = new PartialFileList();
	private Map<String, Object> parameterMap = new HashMap<String, Object>();
	
	private List<String> profiles = new ArrayList<String>();

	protected PartialFileList getResourcePartialFiles() {
		return resourcePartialFiles;
	}

	protected PartialFileList getManifestPartialFiles() {
		return manifestPartialFiles;
	}

	public boolean contains(Class<?> clazz) {
		return classesAdded.contains(clazz);
	}

	public void reset() {
		classesAdded.clear();
		resourcePartialFiles.clear();
		manifestPartialFiles.clear();		
	}

	public void addPartialManifestResource(String name, String content,
			int order) {
		manifestPartialFiles.add(name, content, order);
	}

	public void addPartialResource(String name, String content, int order) {
		resourcePartialFiles.add(name, content, order);

	}

	public abstract void doAddClass(Class<?> clazz);

	public void addClass(Class<?> clazz) {
		if (clazz == null) {
			return;
		}
		if (!contains(clazz)) {
			classesAdded.add(clazz);
			doAddClass(clazz);
		}
	}

	public void addClass(String name) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		addClass(clazz);

	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	@Override
	public List<String> getProfiles() {	
		return profiles;
	}
}
