package org.fluttercode.giftwrap.impl.producer;

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.giftwrap.api.ContentProducer;

public class MultiLineContentProducer extends AbstractStringContentProducer {

	private List<String> lines = new ArrayList<String>();
	private boolean newline;

	public MultiLineContentProducer add(String value) {
		lines.add(value);
		return this;
	}

	@Override
	protected String generateContent() {
		String result = "";
		for (String s : lines) {
			if (result.length() != 0 && newline) {
				result = result + "\n";				
			}
			result = result + s;
		}
		return result;
	}

	public MultiLineContentProducer singleLine() {
		newline = false;
		return this;
	}
	
	public MultiLineContentProducer multiLine() {
		newline = true;
		return this;
	}
}
