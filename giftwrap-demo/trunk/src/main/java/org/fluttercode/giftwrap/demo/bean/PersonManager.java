package org.fluttercode.giftwrap.demo.bean;

import javax.inject.Inject;

import org.fluttercode.giftwrap.demo.model.Person;
import org.fluttercode.giftwrap.demo.model.RatingProducer;

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
