package com.zl.controller;

import com.zl.bean.*;
import com.zl.config.Config;
import com.zl.service.ArticleService;
import com.zl.service.CategoryService;
import com.zl.service.TagService;
import com.zl.util.JsonUtil;
import com.zl.util.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {
@Autowired
private TagService tagService;

@Autowired
    private  CategoryService categoryService;

@Autowired
private ArticleService articleService;

    @RequestMapping("/add")
    public String add(Model model, HttpSession session){
        //只有登录情况才能完成新增功能
        User user = (User) session.getAttribute(Config.LoginKey);
        if(user == null){
            //用户未登录
            return  "redirect:../login";
        }

        //获取类别数据
        List<Category> categories = categoryService.selectAllCategory();


        //获取标签数据
        List<Tag> tags = tagService.selectAllTag();
        model.addAttribute("categoryList",categories);
        model.addAttribute("tagList",tags);
        return "article/add_article";
    }

    //提交博客内容
    @RequestMapping("/addContent")
    @ResponseBody
    public Result addContent(String param,String content,String description) throws UnsupportedEncodingException {
        System.out.println(param);
        System.out.println("content1"+content);
        System.out.println("desc1"+description);

        String param1 = URLDecoder.decode(param,"utf-8");
        String content1 = URLDecoder.decode(content, "utf-8");
        String desc = URLDecoder.decode(description, "utf-8");
        System.out.println(param1);
       //将String字符串解析为对象
        Article article =  JsonUtil.fromJson(param1, Article.class);
        article.setStatue(0);
        article.setContent(content1);
        article.setDescription(desc);
        article.setCreateTime(new Date());

        article.setAuthor("tmp user");

        //将数据插入数据库
        int i = articleService.addArticle(article);
        if(i>0){
            return new Result("success","处理成功");
        }else {
            return new Result("fail","插入数据库失败");
        }
    }

   //获取文章详情
    @RequestMapping("/{id}")
    public  String detail(@PathVariable("id") Integer id,Model model){
        Article article = articleService.getArticleById(id);
        articleService.updateCount(article.getId(),article.getShowCount()+1);
        System.out.println("article:"+article);
        model.addAttribute("article",article);
        return "article/article_detail";
    }

    //后台主页面
    @RequestMapping("/back")
    public String back(Model model){

        //类别列表
        List<Category> categories = categoryService.selectAllCategory();
        //标签列表
        List<Tag> tags = tagService.selectAllTag();

        model.addAttribute("categoryList",categories);
        model.addAttribute("tagList",tags);

        return "article/article_list";
    }

    //后台分页页面
    @RequestMapping("/load")
    public String load(Model model, Pager<Article> pager,String param) throws UnsupportedEncodingException {

        System.out.println("参数：param："+param+",Pager:"+pager);

        Article article = JsonUtil.fromJson(param, Article.class);

        if(article!=null&&article.getTitle()!=null&&"".equals(article.getTitle())){
            //标题参数不为空，需要进行解码处理
            String title = URLDecoder.decode(article.getTitle(), "utf-8");
            article.setTitle(title);
        }

        //分页相关
        //需要获取当前条件下数据的个数
        if(pager.getPage() == 1) {
            Integer articleCount = articleService.getArticleCount(article, pager);
            pager.setTotalCount(articleCount);
        }

        List<Article> articles = articleService.getArticlesByPage(article, pager);
        model.addAttribute("articleList",articles);

        return "article/article_pager";
    }

    @RequestMapping("/updateStatue")
    @ResponseBody
    public Result updateStatue(@Param("id")Integer id,@Param("statue")Integer statue){
        System.out.println("id:"+id+",statue:"+statue);
        Article article = new Article();
        article.setId(id);
        article.setStatue(statue);
        if (articleService.updateStatue(article)){
        return new Result("success","成功");
    }else {
            return new Result("fail","修改失败");
        }

    }
}
