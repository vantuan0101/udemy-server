package com.java.udemy.config;

public final class Constants {

  private Constants() {
  }

  public static final String MAIN_CURRENCY = "TRY";

  public static final String MESSAGE_REGISTER_WELCOME = "Registered! Welcome.";
  public static final String MESSAGE_LOGIN_SUCCESS = "Login Successfully!.";
  public static final String MESSAGE_INVALID_USERNAME = "Invalid username.";
  public static final String MESSAGE_INVALID_PASSWORD = "Invalid password.";
  public static final String MESSAGE_INVALID_MATCH_PASSWORD = "Passwords don't match.";
  public static final String MESSAGE_INVALID_CURRENCY = "Invalid currency.";
  public static final String MESSAGE_INVALID_AMOUNT = "Invalid amount.";
  public static final String MESSAGE_INVALID_TCNO = "Invalid TC No.";
  public static final String MESSAGE_EXCEEDEDMAXVALUE = "Exceeded Maximum Value Per Transaction.";
  public static final String MESSAGE_EXCEEDEDMAXVALUEFORDAY = "Exceeded Maximum Transaction Value For the Day.";
  public static final String MESSAGE_SAMEUSERTRANSACTION = "You can't send money to yourself.";
  public static final String MESSAGE_SAMEUSERNAMEEXIST = "User with the same name exists.";
  public static final String MESSAGE_SAMETCNOEXIST = "User with the same tc no exists.";
  public static final String MESSAGE_EXCHANGESWITHMAINCURRENCY = "You can't make exchange transactions with "
      + MAIN_CURRENCY;

}
