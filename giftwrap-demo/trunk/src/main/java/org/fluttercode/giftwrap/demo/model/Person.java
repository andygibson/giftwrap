package org.fluttercode.giftwrap.demo.model;

public class Person {

	
	private String firstName;
	private String lastName;
	private int age;
	private int rating;
	
	private Car car;
	
	public void changeRating(RatingProducer ratingProducer) {
		this.rating = ratingProducer.getRating();
	}
	
	public int getRating() {
		return rating;
	}
}
