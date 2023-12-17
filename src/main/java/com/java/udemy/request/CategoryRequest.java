package com.java.udemy.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@ToString
public class CategoryRequest extends BaseRequest implements Serializable {
    private static final long serialVersionUID = -248986479752746539L;
    private Integer id;
    private String category;

    public CategoryRequest(Integer id, String category) {
        this.id = id;
        this.category = category;
    }

    public CategoryRequest() {
    }

}