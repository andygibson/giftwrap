package org.fluttercode.giftwrap;


public abstract class AbstractPartialFileElement extends
		AbstractMultilineStringElement {

	private int order;

	public AbstractPartialFileElement(String name, String content) {
		this(name, content, 0);
	}

	public AbstractPartialFileElement(String name, String content, int order) {
		super(name);
		getLines().add(content);
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
