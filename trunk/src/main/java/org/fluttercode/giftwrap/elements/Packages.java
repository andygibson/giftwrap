package org.fluttercode.giftwrap.elements;

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.giftwrap.AbstractArchiveElement;
import org.fluttercode.giftwrap.ArchiveElement;
import org.fluttercode.giftwrap.DeploymentContext;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class Packages extends AbstractArchiveElement {

	private List<Package> packageList = new ArrayList<Package>();
	private boolean recursive;

	public Packages() {
		this(false);
	}

	public Packages(boolean recursive) {
		this.recursive = recursive;
	}
	
	public Packages(Package pack) {
		this(pack,false);
	}
	
	public Packages(Package pack,boolean recursive) {
		packageList.add(pack);
		this.recursive = recursive;
	}

	@Override
	public void doAppend(DeploymentContext context) {
		for (Package p : packageList) {
			context.addPackage(p,recursive);
		}
	}

	public Packages addPackage(Package packge) {
		packageList.add(packge);
		return this;
	}

	public Packages addPackage(String packageName) {
		return addPackage(Package.getPackage(packageName));
	}

	public Packages addPackages(Package... packages) {
		for (Package p : packages) {
			addPackage(p);
		}
		return this;
	}

}
