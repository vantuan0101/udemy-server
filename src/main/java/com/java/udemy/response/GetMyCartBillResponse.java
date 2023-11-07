package com.java.udemy.response;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class GetMyCartBillResponse {
  private Map<String, BigDecimal> getMyCartBill;
}
