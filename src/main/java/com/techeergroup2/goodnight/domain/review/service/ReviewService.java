package com.techeergroup2.goodnight.domain.review.service;

import com.techeergroup2.goodnight.domain.restaurant.domain.Restaurant;
import com.techeergroup2.goodnight.domain.restaurant.repository.RestaurantRepository;
import com.techeergroup2.goodnight.domain.review.domain.Review;
import com.techeergroup2.goodnight.domain.review.dto.ReviewCreateRequest;
import com.techeergroup2.goodnight.domain.review.dto.ReviewCreateResponse;
import com.techeergroup2.goodnight.domain.review.dto.ReviewGetResponse;
import com.techeergroup2.goodnight.domain.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final RestaurantRepository restaurantRepository;

    public ReviewCreateResponse createReview(Long id, ReviewCreateRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No restaurant is found. id=" + id));
        Review review = Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .restaurant(restaurant)
                .build();

        reviewRepository.save(review);
        return review.toResponse();
    }

    public ReviewCreateResponse getReview(Long id) {
        return reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No review is found. id=" + id)).toResponse();
    }

    public ReviewCreateResponse updateReview(Long id, ReviewCreateRequest request) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No review is found. id=" + id));
        review.updateTitle(request.getTitle());
        review.updateContent(request.getContent());
        reviewRepository.save(review);
        return review.toResponse();
    }

    public void deleteReview(Long id) {
        reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No review is found. id=" + id));
        reviewRepository.deleteById(id);
    }
}
