package oga.microservice.athentification.repository;

import oga.microservice.athentification.entities.Article;
import oga.microservice.athentification.repository.abstracts.ICrudRepository;

public interface ArticleRepository extends ICrudRepository<Article, Long> {
}
