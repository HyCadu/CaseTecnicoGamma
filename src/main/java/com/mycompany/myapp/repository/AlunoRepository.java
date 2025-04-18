package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Aluno;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Aluno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlunoRepository extends ReactiveCrudRepository<Aluno, Long>, AlunoRepositoryInternal {
    Flux<Aluno> findAllBy(Pageable pageable);

    @Override
    <S extends Aluno> Mono<S> save(S entity);

    @Override
    Flux<Aluno> findAll();

    @Override
    Mono<Aluno> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface AlunoRepositoryInternal {
    <S extends Aluno> Mono<S> save(S entity);

    Flux<Aluno> findAllBy(Pageable pageable);

    Flux<Aluno> findAll();

    Mono<Aluno> findById(Long id);

    Flux<Aluno> findByCriteria(com.mycompany.myapp.domain.criteria.AlunoCriteria criteria, Pageable page);

    Mono<Long> countByCriteria(com.mycompany.myapp.domain.criteria.AlunoCriteria criteria);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Aluno> findAllBy(Pageable pageable, Criteria criteria);
}
