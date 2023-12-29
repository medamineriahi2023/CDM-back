package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.ArticleDTO;
import oga.microservice.athentification.entities.Article;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface IArticleService extends ICrudService<Article, ArticleDTO> {
}
