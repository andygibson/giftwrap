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

package org.fluttercode.giftwrap.shrinkwrap;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fluttercode.giftwrap.AbstractDeploymentContext;
import org.fluttercode.giftwrap.ArchiveRoot;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Andy Gibson
 * 
 */
public class ArchiveDeployment extends AbstractDeploymentContext {

	private JavaArchive archive;

	private Map<String, Object> parameterMap = new HashMap<String, Object>();

	public static JavaArchive generate(ArchiveRoot root) {
		ArchiveDeployment deployment = new ArchiveDeployment();
		root.produceDeployment(deployment);
		return deployment.getArchive();
	}

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
		super.reset();
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
		getArchive().addClass(clazz);
	}

	public void startConstruction() {
		reset();
	}

	public void endConstruction() {
		// todo build and add partial files
	}

}
