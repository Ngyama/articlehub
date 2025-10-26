package com.ngymkana.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ngymkana.mapper.ArticleMapper;
import com.ngymkana.pojo.Article;
import com.ngymkana.pojo.PageBean;
import com.ngymkana.service.ArticleService;
import com.ngymkana.utils.ThreadLocalUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        // Set additional properties
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);

        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // Create PageBean object
        PageBean<Article> pb = new PageBean<>();

        // Enable pagination with PageHelper
        PageHelper.startPage(pageNum,pageSize);

        // Call mapper
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId,categoryId,state);
        // Get total count and current page data from PageHelper
        Page<Article> p = (Page<Article>) as;

        // Fill data into PageBean object
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
