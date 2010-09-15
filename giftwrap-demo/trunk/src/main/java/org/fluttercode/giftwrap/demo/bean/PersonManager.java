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

package org.fluttercode.giftwrap.demo.bean;

import javax.inject.Inject;

import org.fluttercode.giftwrap.demo.model.Person;
import org.fluttercode.giftwrap.demo.model.RatingProducer;

/**
 * @author Andy Gibson
 * 
 */
public class PersonManager {

	@Inject
	private RatingProducer ratingProducer;

	private Person person = new Person();

	public void applyRating() {
		person.changeRating(ratingProducer);
	}

	public Person getPerson() {
		return person;
	}

	public RatingProducer getRatingProducer() {
		return ratingProducer;
	}

	
}
