package root.demo.service;

import java.util.List;

import root.demo.model.Article;

public interface ArticleService {

	public Article findOne(long id);

	public List<Article> findAll();

	public Article save(Article article);

	public void delete(Article article);

	public Article update(Article article, long id);

}
