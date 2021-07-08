package com.zl.service;

import com.zl.bean.Article;

import com.zl.bean.Tag;
import com.zl.dao.ArticleMapper;
import com.zl.dao.ArticleTagMapper;
import com.zl.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;


@Service
public class ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleTagMapper articleTagMapper;


    public void updateCount( Integer articleId,  Integer count){
        articleMapper.updateCount(articleId,count);
    }

    public int addArticle(Article article){
        //将数据插入到t_Aticle数据库，返回是article对象
        //将tagId和articleid对应关系插入到t_article_tag表中
//        articleTagMapper.insertArticleTag(article.getId(),article.getTagList());
        int i= articleMapper.addArticle(article);
        return 1;
    }

    public Article getArticleById(Integer id){
        return articleMapper.getArticleById(id);
    }

    //获取所有类别的博客信息
    public List<Article> getAllArticle(){
        List<Article> allArticle = articleMapper.getAllArticle();

        return dataHandler(allArticle);
    }

    public List<Article> getAllArticleByCategoryId(Integer categoryId){
        List<Article> articles = articleMapper.getAllArticleByCategoryId(categoryId);
        return dataHandler(articles);
    }


//获取符合条件的数据数量
    public Integer getArticleCount(Article article,Pager pager){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("article",article);
        Integer integer = articleMapper.selectCountByArticle(hashMap);

        return integer;
    }
//对符合条件的数据进行分页处理
    public List<Article> getArticlesByPage(Article article,Pager pager){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("article",article);
        hashMap.put("start",pager.getStart());
        hashMap.put("limit",pager.getLimit());

        List<Article> articles = articleMapper.selectArticleByPager(hashMap);

        return articles;
    }

    //修改状态
    public boolean updateStatue(Article a){
        int i = articleMapper.updateStatue(a);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }

    //封装统一业务逻辑
    private  List<Article> dataHandler(List<Article> articles){
        if(articles!=null && articles.size()>0){
            //时间格式处理
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            for (Article a:articles){
                //处理时间格式
                a.setTime(dateFormat.format(a.getCreateTime()));

                //获取文章标签
                List<Tag> tagList = articleTagMapper.getTagByArticleId(a.getId());
                a.setTags(tagList);
            }
        }
        return articles;
    }
}
