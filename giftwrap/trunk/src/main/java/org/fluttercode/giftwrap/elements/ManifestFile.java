package org.fluttercode.giftwrap.elements;

import java.io.File;

import org.fluttercode.giftwrap.api.DeploymentContext;
import org.fluttercode.giftwrap.impl.NamedResourceElement;

public class ManifestFile extends NamedResourceElement {

	private String fileName;
	
	public ManifestFile(String name,String fileName) {
		super(name);
		this.fileName = fileName;
	}



	@Override
	public void doAppend(DeploymentContext context) {		
		context.addManifestResource(new File(fileName), getName());
	}

}
