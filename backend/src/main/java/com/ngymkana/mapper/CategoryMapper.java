package com.ngymkana.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.ngymkana.pojo.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    // Add category
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time) " +
            "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    // Find all categories
    @Select("select * from category where create_user = #{userId}")
    List<Category> list(Integer userId);

    // Find by ID
    @Select("select * from category where id = #{id}")
    Category findById(Integer id);

    // Update category
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id=#{id}")
    void update(Category category);

    // Delete by ID
    @Delete("delete from category where id=#{id}")
    void deleteById(Integer id);
}
