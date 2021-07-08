package com.zl.controller;

import com.zl.bean.Article;
import com.zl.bean.Category;
import com.zl.bean.Tag;
import com.zl.service.ArticleService;
import com.zl.service.CategoryService;
import com.zl.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    //跳转到首页页面
    @RequestMapping("/")
    public String index(Model model){
        List<Category> categories = categoryService.selectAllCategory();
        List<Tag> tags = tagService.selectAllTag();

        model.addAttribute("categoryList",categories);
        model.addAttribute("tagList",tags);

        return "home/index";
    }

    //加载首页内容
    @RequestMapping("/loadPageAll")
    public String loadAll(Model model){
        List<Article> allArticle = articleService.getAllArticle();
        model.addAttribute("articleList",allArticle);
        return "home/articlepager";
    }

    //按照类别加载
    @RequestMapping("/loadPage/{id}")
    public String loadPage(Model model, @PathVariable("id")Integer id){
        //通过类别id加载类别信息
        Category category = categoryService.getCategoryById(id);


        //当前类别信息
         model.addAttribute("ca",category);

        List<Category> categories = categoryService.selectAllCategory();
        List<Tag> tags = tagService.selectAllTag();

        model.addAttribute("categoryList",categories);
        model.addAttribute("tagList",tags);

        return "home/articlepager1";
    }

    //加载指定类别
    @RequestMapping("/loadPage1/{id}")
    public String loadPage1(Model model,@PathVariable("id")Integer id){
        List<Article> articles = articleService.getAllArticleByCategoryId(id);
        model.addAttribute("articleList",articles);

        return "home/articlepager";
    }
}
