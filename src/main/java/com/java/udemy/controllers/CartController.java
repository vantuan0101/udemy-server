package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.Course;
import com.java.udemy.response.CheckUserCartItemResponse;
import com.java.udemy.response.CountMyCartItemsResponse;
import com.java.udemy.response.GenericResponse;
import com.java.udemy.response.GetAllMyCartItemsResponse;
import com.java.udemy.response.GetMyCartBillResponse;
import com.java.udemy.service.abstractions.ICartService;
import com.java.udemy.service.abstractions.IUserService;

import jakarta.servlet.http.HttpSession;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@Secured(value = "ROLE_STUDENT")
@RequestMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController {

  @Autowired
  private ICartService cartService;

  @Autowired
  private IUserService userService;

  @PostMapping(path = "/course/{courseId}")
  @ResponseStatus(HttpStatus.CREATED)
  public GenericResponse addSingleItem(HttpSession session, @PathVariable Integer courseId) {
    try {
      Integer userId = userService.getSessionUserId(session);
      int count = cartService.addToCartCustom(courseId, userId);
      GenericResponse response = new GenericResponse(String.format("Added %d item to Cart", count));
      return response;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add to cart", e);
    }
  }

  @GetMapping(path = "/status/c/{courseId}")
  @ResponseStatus(HttpStatus.OK)
  public CheckUserCartItemResponse checkUserCartItem(@PathVariable @NotNull Integer courseId, HttpSession session) {
    try {
      Integer userId = userService.getSessionUserId(session);
      Map<String, Boolean> checkUserCartItem = cartService.checkIfCourseInCart(userId, courseId);
      CheckUserCartItemResponse response = new CheckUserCartItemResponse();
      response.setCheckUserCartItem(checkUserCartItem);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

  @GetMapping(path = "/mine")
  @ResponseStatus(HttpStatus.OK)
  public GetAllMyCartItemsResponse getAllMyCartItems(@RequestParam(defaultValue = "0") Integer page,
      HttpSession session) {
    try {
      Integer userId = userService.getSessionUserId(session);
      Page<Course> getAllMyCartItems = cartService.getCartListByUser(userId, page);
      GetAllMyCartItemsResponse response = new GetAllMyCartItemsResponse();
      response.setGetAllMyCartItems(getAllMyCartItems);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

  @GetMapping(path = "/mine/bill")
  @ResponseStatus(HttpStatus.OK)
  public GetMyCartBillResponse getMyCartBill(HttpSession session) {
    try {
      Integer userId = userService.getSessionUserId(session);
      Map<String, BigDecimal> getMyCartBill = cartService.getTotalBillForUser(userId);
      GetMyCartBillResponse response = new GetMyCartBillResponse();
      response.setGetMyCartBill(getMyCartBill);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

  @GetMapping(path = "/mine/count")
  @ResponseStatus(HttpStatus.OK)
  public CountMyCartItemsResponse countMyCartItems(HttpSession session) {
    try {
      Integer userId = userService.getSessionUserId(session);
      Map<String, Long> countMyCartItems = cartService.countCartByUserIdEquals(userId);
      CountMyCartItemsResponse response = new CountMyCartItemsResponse();
      response.setCountMyCartItems(countMyCartItems);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

  @DeleteMapping(path = "/course/{courseId}")
  @ResponseStatus(HttpStatus.OK)
  public GenericResponse removeCartByCourseId(@PathVariable @NotNull Integer courseId,
      HttpSession session) {
    try {
      Integer userId = userService.getSessionUserId(session);
      int deletedCount = cartService.deleteByUserIdAndCoursesIn(userId, courseId);
      GenericResponse response = new GenericResponse("Removed from Cart, course " + courseId);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

}
