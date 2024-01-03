package com.java.udemy.response;

public class OrderItemResponse {

    private Integer id;
    private Integer courseId;
    private String transactionId;

    public OrderItemResponse(Integer id, Integer courseId, String transactionId) {
        this.id = id;
        this.courseId = courseId;
        this.transactionId = transactionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
