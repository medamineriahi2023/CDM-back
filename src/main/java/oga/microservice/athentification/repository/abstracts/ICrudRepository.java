package oga.microservice.athentification.repository.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICrudRepository<T, ID> extends JpaRepository<T, ID>, IExtendedRepository<T, ID> { }
