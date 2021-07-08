package com.zl.service;


import com.zl.bean.Tag;
import com.zl.dao.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TagService {
     @Autowired
    private TagMapper tagMapper;

     public List<Tag> selectAllTag(){
     return tagMapper.getAllTag();
     }
}
