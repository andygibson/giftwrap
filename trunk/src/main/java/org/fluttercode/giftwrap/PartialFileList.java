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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Andy Gibson
 * 
 */
public class PartialFileList {

	private class FileFragment implements Comparable<FileFragment> {

		int order;
		String content;

		public FileFragment(String content,int order) {
			this.content = content;
			this.order = order;
		}
		
		public int compareTo(FileFragment fragment) {
			return order - fragment.order;
		}

	}

	private Map<String, List<FileFragment>> fragmentMap = new HashMap<String, List<FileFragment>>();

	protected List<FileFragment> findFragments(String name) {
		List<FileFragment> result = fragmentMap.get(name);
		if (result == null) {
			result = new ArrayList<FileFragment>();
			fragmentMap.put(name, result);
		}
		return result;
	}

	public String buildFileContent(String name) {
		List<FileFragment> result = fragmentMap.get(name);
		if (result == null) {
			throw new IllegalArgumentException(
					"There are no file fragments for a file named " + name);
		}
		Collections.sort(result);
		StringBuilder sb = new StringBuilder();
		for (FileFragment f : result) {
			sb.append(f.content);
		}
		return sb.toString();
	}

	public void add(String name, String content,int order) {
		List<FileFragment> fragments = findFragments(name);
		fragments.add(new FileFragment(content,order));
	}
	
	public void clear() {
		fragmentMap.clear();
	}
	
	public Set<String> nameSet() {
		return fragmentMap.keySet();
	}
}
