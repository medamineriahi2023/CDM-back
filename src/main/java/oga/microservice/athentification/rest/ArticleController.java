package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.ArticleDTO;
import oga.microservice.athentification.entities.Article;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "article")
public class ArticleController extends AbstractCrudController<Article, ArticleDTO> {
    public ArticleController() {
        super(Article.class);
    }

}
