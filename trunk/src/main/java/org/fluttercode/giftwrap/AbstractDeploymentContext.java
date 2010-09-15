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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fluttercode.giftwrap.api.DeploymentContext;

/**
 * @author Andy Gibson
 * 
 */
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

	protected List<Class<?>> getClassesAdded() {
		return classesAdded;
	}

	@Override
	public List<String> getProfiles() {
		return profiles;
	}

	@Override
	public void addProfile(String profile) {
		if (!profiles.contains(profile)) {
			profiles.add(profile);
		}
	}

	@Override
	public void removeProfile(String profile) {
		while (profiles.contains(profile)) {
			profiles.remove(profile);
		}
	}
	
	@Override
	public void endConstruction() {
		//assemble and add partial files
		for (String s : resourcePartialFiles.nameSet()) {
			String content = resourcePartialFiles.buildFileContent(s);
			//addResource(content,s);
		}

		for (String s : manifestPartialFiles.nameSet()) {
			String content = manifestPartialFiles.buildFileContent(s);
			addManifestResource(content, s);
		}
		
	}

}
