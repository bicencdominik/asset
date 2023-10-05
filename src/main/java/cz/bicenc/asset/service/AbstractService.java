package cz.bicenc.asset.service;

import com.querydsl.core.types.Predicate;
import cz.bicenc.asset.exception.AssetNotFoundException;
import cz.bicenc.asset.persistence.entity.BasicAuditEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractService<E extends BasicAuditEntity, ID> {

    private final JpaRepository<E, ID> repository;
    private final Logger log;

    public E get(@NonNull ID id) {
        return getOrThrowNotFound(id);
    }

    public abstract Page<E> findAll(@NonNull Predicate predicate, @NonNull Pageable pageable);

    protected E saveWithHistory(@NonNull E changedEntity) {
        changedEntity = saveWithoutHistory(changedEntity);
        saveHistory(changedEntity);
        return changedEntity;
    }

    protected void saveHistory(E entity) {
        // TODO we should probably really save it somewhere
        log.info("Saving history for {}...sort of", entity);
    }

    protected E saveWithoutHistory(@NonNull E entity) {
        getReferences(entity);
        checkIfEntityIsNotDeleted(entity);
        return repository.save(entity);
    }

    public E deleteWithHistory(@NonNull ID id){
        E entity = getOrThrowNotFound(id);
        E deletedEntity = delete(entity);
        saveHistory(deletedEntity);
        return deletedEntity;
    }

    public E delete(@NonNull E entity) {
        setEntityDeleted(entity);
        return repository.save(entity);
    }

    public E delete(@NonNull ID entityId){
        return delete(getOrThrowNotFound(entityId));
    }

    protected void getReferences(E entity) {
    }

    public E getOrThrowNotFound(@NonNull ID id) throws AssetNotFoundException {
        Optional<E> byId = findById(id);
        if (byId.isEmpty()) {
            log.debug("Entity {} not found", id);
            throw new AssetNotFoundException();
        }
        checkIfEntityIsNotDeleted(byId.get());
        return byId.get();
    }

    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    protected String getLoggedUser() {
        // TODO neni security
        return "prave lognuty user";
    }

    private void checkIfEntityIsNotDeleted(@NonNull E entity){
        if (entity.getDeleted()){
            throw new AssetNotFoundException("Entity id " + entity.getId() + " is not found");
        }
    }

    private void setEntityDeleted(@NonNull E entity){
        entity.setDeleted(true);
        entity.setDeletedBy(getLoggedUser());
        entity.setDeletedTime(LocalDateTime.now());
    }
}
