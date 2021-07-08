package com.zl.dao;

import com.zl.bean.Article;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ArticleMapper {

  public int addArticle(Article article);

  public Article getArticleById(Integer id);

  public List<Article> getAllArticle();

  public List<Article> getAllArticleByCategoryId(Integer categoryId);

  //查询符合要求的数据总个数
 public Integer selectCountByArticle(HashMap<String,Object> parm);

 //分页查询符合要求的数据
 public List<Article> selectArticleByPager(HashMap<String,Object> parm);

 public int updateStatue(Article article);

  public void updateCount(@Param("articleId") Integer articleId, @Param("count") Integer count);
}
