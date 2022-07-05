/*package com.xiaoxueqi.blog.Service;

import com.xiaoxueqi.blog.DAO.ArticleDao;
import com.xiaoxueqi.blog.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ArticleService {
    private ArticleDao articleDao;
    @Autowired
    public void setArticleDao(ArticleDao articleDao){
        this.articleDao = articleDao;
    }

    public Article getByArticleId(Long articleId){
        return articleDao.findArticleByArticleId(articleId);
    }

    public Article getByAuthorId(Long authorId){
        return articleDao.findArticleByAuthorId(authorId);
    }

    public ArrayList<Article> getByLikesIsGreaterThanEqual(int min){
        return articleDao.findArticleByLikesIsGreaterThanEqual(min);
    }
}
*/