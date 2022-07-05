package com.xiaoxueqi.blog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "article")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArticleId")
    private Long ArticleId;

    private String title;
    private Long AuthorId;
    private Date createDate;
    private int likes;
    private Text essay;
    private ArrayList<String> comments;

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setArticleId(Long articleId) {
        ArticleId = articleId;
    }

    public void setAuthorId(Long authorId) {
        AuthorId = authorId;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setEssay(Text essay) {
        this.essay = essay;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Long getArticleId() {
        return ArticleId;
    }

    public Long getAuthorId() {
        return AuthorId;
    }

    public Text getEssay() {
        return essay;
    }

    public String getTitle() {
        return title;
    }
}
