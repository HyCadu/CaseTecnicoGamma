package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.criteria.MetaCriteria;
import com.mycompany.myapp.repository.MetaRepository;
import com.mycompany.myapp.service.dto.MetaDTO;
import com.mycompany.myapp.service.mapper.MetaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Meta}.
 */
@Service
@Transactional
public class MetaService {

    private static final Logger LOG = LoggerFactory.getLogger(MetaService.class);

    private final MetaRepository metaRepository;

    private final MetaMapper metaMapper;

    public MetaService(MetaRepository metaRepository, MetaMapper metaMapper) {
        this.metaRepository = metaRepository;
        this.metaMapper = metaMapper;
    }

    /**
     * Save a meta.
     *
     * @param metaDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<MetaDTO> save(MetaDTO metaDTO) {
        LOG.debug("Request to save Meta : {}", metaDTO);
        return metaRepository.save(metaMapper.toEntity(metaDTO)).map(metaMapper::toDto);
    }

    /**
     * Update a meta.
     *
     * @param metaDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<MetaDTO> update(MetaDTO metaDTO) {
        LOG.debug("Request to update Meta : {}", metaDTO);
        return metaRepository.save(metaMapper.toEntity(metaDTO)).map(metaMapper::toDto);
    }

    /**
     * Partially update a meta.
     *
     * @param metaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<MetaDTO> partialUpdate(MetaDTO metaDTO) {
        LOG.debug("Request to partially update Meta : {}", metaDTO);

        return metaRepository
            .findById(metaDTO.getId())
            .map(existingMeta -> {
                metaMapper.partialUpdate(existingMeta, metaDTO);

                return existingMeta;
            })
            .flatMap(metaRepository::save)
            .map(metaMapper::toDto);
    }

    /**
     * Find metas by Criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<MetaDTO> findByCriteria(MetaCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Metas by Criteria");
        return metaRepository.findByCriteria(criteria, pageable).map(metaMapper::toDto);
    }

    /**
     * Find the count of metas by criteria.
     * @param criteria filtering criteria
     * @return the count of metas
     */
    public Mono<Long> countByCriteria(MetaCriteria criteria) {
        LOG.debug("Request to get the count of all Metas by Criteria");
        return metaRepository.countByCriteria(criteria);
    }

    /**
     * Returns the number of metas available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return metaRepository.count();
    }

    /**
     * Get one meta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<MetaDTO> findOne(Long id) {
        LOG.debug("Request to get Meta : {}", id);
        return metaRepository.findById(id).map(metaMapper::toDto);
    }

    /**
     * Delete the meta by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Meta : {}", id);
        return metaRepository.deleteById(id);
    }
}
