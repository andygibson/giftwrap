package org.fluttercode.giftwrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
