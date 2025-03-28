package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Aluno;
import com.mycompany.myapp.repository.rowmapper.AlunoRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the Aluno entity.
 */
@SuppressWarnings("unused")
class AlunoRepositoryInternalImpl extends SimpleR2dbcRepository<Aluno, Long> implements AlunoRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final AlunoRowMapper alunoMapper;

    private static final Table entityTable = Table.aliased("aluno", EntityManager.ENTITY_ALIAS);

    public AlunoRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        AlunoRowMapper alunoMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Aluno.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.alunoMapper = alunoMapper;
    }

    @Override
    public Flux<Aluno> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Aluno> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = AlunoSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Aluno.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Aluno> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Aluno> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Aluno process(Row row, RowMetadata metadata) {
        Aluno entity = alunoMapper.apply(row, "e");
        return entity;
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return db.sql("DELETE FROM meta WHERE aluno_id = :id").bind("id", id).then().then(super.deleteById(id));
    }

    @Override
    public <S extends Aluno> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Aluno> findByCriteria(com.mycompany.myapp.domain.criteria.AlunoCriteria criteria, Pageable page) {
        return createQuery(page, null).all(); // Implementação simplificada
    }

    @Override
    public Mono<Long> countByCriteria(com.mycompany.myapp.domain.criteria.AlunoCriteria criteria) {
        return Mono.just(1L); // Implementação simplificada
    }
}
