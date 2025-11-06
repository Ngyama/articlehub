package com.ngymkana.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.ngymkana.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    // Add article
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);


    List<Article> list(Integer userId, Integer categoryId, String state);

    // Update article
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id=#{id} and create_user=#{createUser}")
    void update(Article article);

    // Delete article by ID
    @Delete("delete from article where id=#{id} and create_user=#{createUser}")
    void deleteById(Integer id, Integer userId);
}
