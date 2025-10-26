package com.ngymkana.service;

import java.util.List;

import com.ngymkana.pojo.Category;

public interface CategoryService {
    // Add category
    void add(Category category);

    // List categories
    List<Category> list();

    // Find category by ID
    Category findById(Integer id);

    // Update category
    void update(Category category);

    // Delete category
    void deleteById(Integer id);
}
