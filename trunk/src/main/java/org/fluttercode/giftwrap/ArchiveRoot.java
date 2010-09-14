package org.fluttercode.giftwrap;

import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class ArchiveRoot extends ElementHolder {



	public ArchiveRoot() {
		addPackage(this.getClass().getPackage());
	}

	public JavaArchive produceArchive() {
		ArchiveDeployment dc = new ArchiveDeployment();
		fillArchive(dc);
		return dc.getArchive();
	}

	public void fillArchive(DeploymentContext context) {
		System.out.println("******* Starting to fill archive ************");
		context.startConstruction();
		append(context);
		context.endConstruction();
		
	}

}
