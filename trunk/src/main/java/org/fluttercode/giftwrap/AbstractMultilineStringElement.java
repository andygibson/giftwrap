/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Giftwrap.
 *
 * Giftwrap is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Giftwrap is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Giftwrap.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.giftwrap;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Andy Gibson
 * 
 */
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
