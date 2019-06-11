package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import root.demo.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{

}
