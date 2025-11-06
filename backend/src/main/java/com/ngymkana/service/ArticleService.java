package com.ngymkana.service;

import com.ngymkana.pojo.Article;
import com.ngymkana.pojo.PageBean;

public interface ArticleService {
    // Add article
    void add(Article article);

    // Paginated list query with conditions
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    // Update article
    void update(Article article);

    // Delete article by ID
    void deleteById(Integer id);
}
