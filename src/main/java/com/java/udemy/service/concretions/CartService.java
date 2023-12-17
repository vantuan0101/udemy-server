package com.java.udemy.service.concretions;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.models.Course;
import com.java.udemy.repository.CartRepository;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.service.abstractions.ICartService;

@Service
public class CartService implements ICartService {
  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Override
  public int addToCartCustom(Integer courseId, Integer userId) {
    Course course = courseRepository.findById(courseId).orElseThrow();
    int count = cartRepository.addToCartCustom(course.getId(), userId, course.getPrice());
    return count;
  }

  @Override
  public Map<String, Boolean> checkIfCourseInCart(Integer userId, Integer courseId) {
    boolean inCart = cartRepository.checkIfCourseInCart(userId, courseId) > 0;
    Map<String, Boolean> checkUserCartItem = Collections.singletonMap("inCart", inCart);
    return checkUserCartItem;
  }

  @Override
  public Page<Course> getCartListByUser(Integer userId, Integer page) {
    Page<Course> getAllMyCartItems = courseRepository.getCartListByUser(userId, PageRequest.of(Math.abs(page), 5));
    return getAllMyCartItems;
  }

  @Override
  public Map<String, BigDecimal> getTotalBillForUser(Integer userId) {
    BigDecimal totalPrice = cartRepository.getTotalBillForUser(userId);
    Map<String, BigDecimal> getMyCartBill = Collections.singletonMap("totalPrice", totalPrice);
    return getMyCartBill;
  }

  @Override
  public Map<String, Long> countCartByUserIdEquals(Integer userId) {
    long cartCount = cartRepository.countCartByUserIdEquals(userId);
    Map<String, Long> countMyCartItems = Collections.singletonMap("cartCount", cartCount);
    return countMyCartItems;
  }

  @Override
  public int deleteByUserIdAndCoursesIn(Integer userId, Integer courseId) {
    int deletedCount = cartRepository.deleteByUserIdAndCoursesIn(userId, Collections.singleton(courseId));
    if (deletedCount != 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not remove from cart");
    }
    return deletedCount;
  }
}
