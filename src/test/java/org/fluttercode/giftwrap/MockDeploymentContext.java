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
import java.util.ArrayList;
import java.util.List;

import org.fluttercode.giftwrap.impl.AbstractDeploymentContext;
import org.fluttercode.giftwrap.impl.PartialFileList;

/**
 * @author Andy Gibson
 * 
 */
public class MockDeploymentContext extends AbstractDeploymentContext {

	private List<String> log = new ArrayList<String>();

	@Override
	public void doAddClass(Class<?> clazz) {
		log.add("Added class : " + clazz);
	}

	public void addManifestResource(String content, String name) {
		log.add("Added manifest resource : " + name);
		log.add("    content = " + content);
	}

	public void addManifestResource(File file, String name) {
		log.add("Added manifest resource file for : " + name);
		log.add("    file = " + file);

	}

	public void addManifestResource(URL url, String name) {
		log.add("Added manifest resource url for : " + name);
		log.add("    url = " + url);
	}

	public void addPackage(Package pack, boolean recursive) {
		log.add("Added package : " + pack
				+ (recursive ? "recursively" : " non-recursively"));
	}

	public List<String> getLog() {
		return log;
	}

	public void dumpLog() {
		System.out.println("Dumping log to console");
		for (String s : log) {
			System.out.println(s);
		}
	}

	@Override
	public PartialFileList getManifestPartialFiles() {
		return super.getManifestPartialFiles();
	}

	@Override
	protected PartialFileList getResourcePartialFiles() {
		return super.getResourcePartialFiles();
	}

	public void endConstruction() {
		log.add("Ending construction");
	}

	public void startConstruction() {
		reset();
		log.add("Starting construction");
	}
	
	@Override
	public List<Class<?>> getClassesAdded() {
		// TODO Auto-generated method stub
		return super.getClassesAdded();
	}

	@Override
	public void addManifestResource(byte[] content, String name) {
		log.add(String.format("adding manifest resource %s [%s]",name,content));		
	}

	@Override
	public void addResource(byte[] content, String name) {
		log.add(String.format("adding resource %s [%s]",name,content));		
	}
	
}
