package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.ArticleDTO;
import oga.microservice.athentification.entities.Article;
import oga.microservice.athentification.service.IArticleService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService extends AbstractCrudService<Article , ArticleDTO> implements IArticleService {
}
