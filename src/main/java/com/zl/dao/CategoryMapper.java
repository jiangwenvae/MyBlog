package com.zl.dao;

import com.zl.bean.Category;

import java.util.List;

public interface CategoryMapper {


    public List<Category> getAllCategory();

      public Category getCategoryById(Integer id);
}
