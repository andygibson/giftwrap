package org.fluttercode.arqbuilder;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.fluttercode.giftwrap.AbstractDeploymentContext;
import org.fluttercode.giftwrap.PartialFileList;

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
		log.add("Starting construction");
	}

}
