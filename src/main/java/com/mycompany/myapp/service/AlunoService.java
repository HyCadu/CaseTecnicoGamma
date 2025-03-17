package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.criteria.AlunoCriteria;
import com.mycompany.myapp.repository.AlunoRepository;
import com.mycompany.myapp.service.dto.AlunoDTO;
import com.mycompany.myapp.service.mapper.AlunoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Aluno}.
 */
@Service
@Transactional
public class AlunoService {

    private static final Logger LOG = LoggerFactory.getLogger(AlunoService.class);

    private final AlunoRepository alunoRepository;

    private final AlunoMapper alunoMapper;

    public AlunoService(AlunoRepository alunoRepository, AlunoMapper alunoMapper) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
    }

    /**
     * Save a aluno.
     *
     * @param alunoDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<AlunoDTO> save(AlunoDTO alunoDTO) {
        LOG.debug("Request to save Aluno : {}", alunoDTO);
        return alunoRepository.save(alunoMapper.toEntity(alunoDTO)).map(alunoMapper::toDto);
    }

    /**
     * Update a aluno.
     *
     * @param alunoDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<AlunoDTO> update(AlunoDTO alunoDTO) {
        LOG.debug("Request to update Aluno : {}", alunoDTO);
        return alunoRepository.save(alunoMapper.toEntity(alunoDTO)).map(alunoMapper::toDto);
    }

    /**
     * Partially update a aluno.
     *
     * @param alunoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<AlunoDTO> partialUpdate(AlunoDTO alunoDTO) {
        LOG.debug("Request to partially update Aluno : {}", alunoDTO);

        return alunoRepository
            .findById(alunoDTO.getId())
            .map(existingAluno -> {
                alunoMapper.partialUpdate(existingAluno, alunoDTO);

                return existingAluno;
            })
            .flatMap(alunoRepository::save)
            .map(alunoMapper::toDto);
    }

    /**
     * Find alunos by Criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<AlunoDTO> findByCriteria(AlunoCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Alunos by Criteria");
        return alunoRepository.findByCriteria(criteria, pageable).map(alunoMapper::toDto);
    }

    /**
     * Find the count of alunos by criteria.
     * @param criteria filtering criteria
     * @return the count of alunos
     */
    public Mono<Long> countByCriteria(AlunoCriteria criteria) {
        LOG.debug("Request to get the count of all Alunos by Criteria");
        return alunoRepository.countByCriteria(criteria);
    }

    /**
     * Returns the number of alunos available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return alunoRepository.count();
    }

    /**
     * Get one aluno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<AlunoDTO> findOne(Long id) {
        LOG.debug("Request to get Aluno : {}", id);
        return alunoRepository.findById(id).map(alunoMapper::toDto);
    }

    /**
     * Delete the aluno by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Aluno : {}", id);
        return alunoRepository.deleteById(id);
    }
}
