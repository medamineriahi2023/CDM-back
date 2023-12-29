package oga.microservice.athentification.repository.config;

import oga.microservice.athentification.repository.abstracts.IExtendedRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.function.Function;

public class ExtendedRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements IExtendedRepository<T, ID> {

    private final EntityManager entityManager;

    public ExtendedRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public void detach(T entity) {
        entityManager.detach(entity);
    }

    @Override
    public <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
