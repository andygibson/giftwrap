package org.fluttercode.giftwrap;


public interface ArchiveElement {

	public void append(DeploymentContext context);
	public ArchiveElement addIncludeProfile(String profile);
	public ArchiveElement addExcludeProfile(String profile);
	public boolean isActive(DeploymentContext context);
}
