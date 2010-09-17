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

package org.fluttercode.giftwrap.impl;

import org.fluttercode.giftwrap.api.ArchiveElement;
import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.elements.ClassElement;
import org.fluttercode.giftwrap.shrinkwrap.ArchiveDeployment;
import org.fluttercode.giftwrap.xml.Document;
import org.fluttercode.giftwrap.xml.exporter.DocumentExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Andy Gibson
 * 
 */
public class ArchiveRoot extends ElementContainer {

 	public ArchiveRoot() {
 		addPackageForClasses(true,new Class<?>[] {
 				DocumentExporter.class,
 				Document.class,
 				ArchiveDeployment.class,
 				ArchiveElement.class,
 				ClassElement.class,
 				ArchiveRoot.class});
	}

	public JavaArchive produceArchive() {
		ArchiveDeployment dc = new ArchiveDeployment();
		produceDeployment(dc);
		return dc.getArchive();
	}

	public void produceDeployment(DeploymentContext context) {
		context.startConstruction();
		append(context);
		context.endConstruction();
		
	}

}
