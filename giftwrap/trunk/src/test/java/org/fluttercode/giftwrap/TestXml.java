package org.fluttercode.giftwrap;

import org.fluttercode.giftwrap.impl.ArchiveRoot;
import org.junit.Before;
import org.junit.Test;

public class TestXml {

	private MockDeploymentContext dc;
	private ArchiveRoot root;

	@Before
	public void doInit() {
		dc = new MockDeploymentContext();
		root = new ArchiveRoot();
	}

	@Test
	public void testXML() {
	}

}
