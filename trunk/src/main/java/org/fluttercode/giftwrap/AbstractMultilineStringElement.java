package org.fluttercode.giftwrap;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractMultilineStringElement extends AbstractStringResource {

	public AbstractMultilineStringElement(String name) {
		super(name);
	}

	private List<String> lines = new ArrayList<String>();
	private boolean useNewline = true;
	private boolean insertSpace = false;
	
	public List<String> getLines() {
		return lines;
	}

	@Override
	protected String doGenerateContent() {
		StringBuilder sb = new StringBuilder();
		for (String s : lines) {
			sb.append(s);
			if (insertSpace) {
				sb.append(" ");
			}
			if (useNewline) {
				sb.append("\n");
			}			
		}
		return sb.toString();
	}

	public boolean getUseNewline() {
		return useNewline;
	}

	public void setUseNewline(boolean useNewline) {
		this.useNewline = useNewline;
	}

	public boolean getInsertSpace() {
		return insertSpace;
	}

	public void setInsertSpace(boolean insertSpace) {
		this.insertSpace = insertSpace;
	}

	public AbstractMultilineStringElement append(String line) {
		lines.add(line);
		return this;
	}
	
	public AbstractMultilineStringElement includeNewLine() {
		useNewline = true;
		return this;
	}
	
	public AbstractMultilineStringElement excludeNewLine() {
		useNewline = false;
		return this;
	}
	
}
