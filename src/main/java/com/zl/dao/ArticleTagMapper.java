package com.zl.dao;

import com.zl.bean.Article;
import com.zl.bean.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleTagMapper {

    /**
     * 通过博客id获取博客标签名称
     * @param articleId
     * @return
     */
    public List<Tag> getTagByArticleId(Integer articleId);

//     public void insertArticleTag(@Param("articleId") Integer articleId, @Param("tagList") List<Integer> tagList);
}
