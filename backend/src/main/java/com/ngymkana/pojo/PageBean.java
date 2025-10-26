package com.ngymkana.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Pagination result object
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean <T>{
    private Long total; // Total count
    private List<T> items; // Current page data
}
