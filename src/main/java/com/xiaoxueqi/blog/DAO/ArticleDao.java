/*package com.xiaoxueqi.blog.DAO;

import com.xiaoxueqi.blog.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ArticleDao extends JpaRepository<Article,Integer> {

    Article findArticleByArticleId(Long articleId);

    Article findArticleByAuthorId(Long authorId);

    ArrayList<Article> findArticleByLikesIsGreaterThanEqual(int min);

}
*/