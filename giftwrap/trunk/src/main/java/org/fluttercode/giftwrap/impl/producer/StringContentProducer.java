package org.fluttercode.giftwrap.impl.producer;



public class StringContentProducer extends AbstractStringContentProducer {

	private String content;

	public StringContentProducer(String content) {
		this.content = content;
	}

	@Override
	protected String generateContent() {
		return content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
