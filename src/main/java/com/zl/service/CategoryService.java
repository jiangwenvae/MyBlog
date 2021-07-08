package com.zl.service;

import com.zl.bean.Category;
import com.zl.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> selectAllCategory(){
        return categoryMapper.getAllCategory();
    }

    public Category getCategoryById(Integer id){
        return  categoryMapper.getCategoryById(id);
    }
}
