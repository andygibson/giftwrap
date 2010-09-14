package org.fluttercode.giftwrap;

public class DefaultProfile implements DeploymentProfile {

	@Override
	public boolean isActive(DeploymentContext context) {
		return true;
	}

}
