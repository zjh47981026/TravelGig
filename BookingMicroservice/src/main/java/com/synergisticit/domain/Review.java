package com.synergisticit.domain;


import jakarta.persistence.*;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="review")
public class Review {
		
		@Id
		@GeneratedValue(strategy= GenerationType.IDENTITY)
		private int reviewId;
		
		private String text;
		private double overallRating;

		public Review() {}

		public int getReviewId() {
			return reviewId;
		}

		public void setReviewId(int reviewId) {
			this.reviewId = reviewId;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public double getOverallRating() {
			return overallRating;
		}

		public void setOverallRating(double overallRating) {
			this.overallRating = overallRating;
		}

}
