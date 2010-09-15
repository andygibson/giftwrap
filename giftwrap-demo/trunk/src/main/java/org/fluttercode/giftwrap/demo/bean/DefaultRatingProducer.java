package org.fluttercode.giftwrap.demo.bean;

import javax.enterprise.inject.Alternative;

import org.fluttercode.giftwrap.demo.model.RatingProducer;

@Alternative
public class DefaultRatingProducer implements RatingProducer{

	public int getRating() {
		return 28;
	}

}
