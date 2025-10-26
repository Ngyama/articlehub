package com.ngymkana.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Unified response result
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code; // Status code: 0-success, 1-failure
    private String message; // Message
    private T data; // Response data

    // Return success response with data
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "Operation successful", data);
    }

    // Return success response without data
    public static Result success() {
        return new Result(0, "Operation successful", null);
    }

    public static Result error(String message) {
        return new Result(1, message, null);
    }
}
