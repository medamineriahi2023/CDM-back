package oga.microservice.athentification.repository.abstracts;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface IExtendedRepository<T, ID> extends Repository<T, ID> {
    void detach(T entity);
}
