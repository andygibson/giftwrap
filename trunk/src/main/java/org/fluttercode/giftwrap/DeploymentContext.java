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

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author Andy Gibson
 * 
 */
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
