package org.fluttercode.giftwrap.impl.producer;

import org.fluttercode.giftwrap.api.ContentProducer;

public abstract class AbstractStringContentProducer implements ContentProducer {

	@Override
	public byte[] produce() {

		String content = generateContent();
		if (content == null) {
			return "".getBytes();
		}
		return content.getBytes();
	}

	protected abstract String generateContent();


}
