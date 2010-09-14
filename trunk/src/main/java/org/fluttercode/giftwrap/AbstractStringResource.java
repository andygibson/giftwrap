package org.fluttercode.giftwrap;


/**
 * Abstract class for adding manifest resources that are based on string content 
 * @author Andy Gibson
 *
 */
public abstract class AbstractStringResource implements ArchiveElement {

	private final String name;

	public AbstractStringResource(String name) {
		this.name = name;
	}

	public void append(DeploymentContext context) {	
		String content = doGenerateContent();
		doAppendContent(content,context);		
	}

	public String getName() {
		return name;
	}
	protected abstract String doGenerateContent();
	protected abstract void doAppendContent(String content,DeploymentContext context);

}
