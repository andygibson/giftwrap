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

package org.fluttercode.giftwrap.impl.producer;

import org.fluttercode.giftwrap.api.ContentProducer;

/**
 * @author Andy Gibson
 * 
 */
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
