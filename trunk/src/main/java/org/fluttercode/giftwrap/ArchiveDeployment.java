package org.fluttercode.giftwrap;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class ArchiveDeployment extends AbstractDeploymentContext {

	private JavaArchive archive;

	private Map<String, Object> parameterMap = new HashMap<String, Object>();

	public ArchiveDeployment(JavaArchive archive) {
		this.archive = archive;
	}

	public ArchiveDeployment() {
		this("test.jar");
	}

	private ArchiveDeployment(String name) {
		this.archive = ShrinkWrap.create(JavaArchive.class, name);
	}

	public JavaArchive getArchive() {
		return archive;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public void addManifestResource(String content, String name) {

		ArchivePath path = ArchivePaths.create(name);
		if (content == null) {
			content = "";
		}
		ByteArrayAsset asset = new ByteArrayAsset(content.getBytes());
		getArchive().addManifestResource(asset, path);
	}

	public void addManifestResource(File file, String name) {
		getArchive().addManifestResource(file, ArchivePaths.create(name));
	}

	public void addPackage(Package pack, boolean recursive) {
		getArchive().addPackages(recursive, pack);
	}

	public void reset() {
		if (archive == null) {
			this.archive = ShrinkWrap.create(JavaArchive.class, "test.jar");
		} else {
			this.archive = ShrinkWrap.create(JavaArchive.class, archive
					.getName());
		}

	}

	public void addManifestResource(URL url, String name) {
		getArchive().addManifestResource(url, name);
	}

	@Override
	public void doAddClass(Class<?> clazz) {
		System.out.println("---------adding class " + clazz.getCanonicalName());
		getArchive().addClass(clazz);

	}

	public void startConstruction() {
		reset();		
	}
	
	public void endConstruction() {
		//todo build and add partial files	
	}
}
