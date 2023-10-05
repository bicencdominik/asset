package cz.bicenc.asset.service;

import com.querydsl.core.types.Predicate;
import cz.bicenc.asset.exception.AssetIllegalChangeException;
import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.persistence.repository.AssetRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AssetService extends AbstractService<Asset,UUID>{

    private final AssetRepository repository;

    public AssetService(AssetRepository repository) {
        super(repository, log);
        this.repository = repository;
    }

    public Page<Asset> findAll(@NonNull Predicate predicate,@NonNull Pageable pageable) {
        return repository.findAll(predicate,pageable);
    }

    public Asset save(@NonNull Asset asset) {
        return saveWithHistory(asset);
    }

    public Asset update(@NonNull UUID id ,@NonNull Asset newAsset){
        Asset oldAsset = getOrThrowNotFound(id);
        checkUpdateChanges(oldAsset,newAsset);
        return saveWithHistory(newAsset);
    }

    private void checkUpdateChanges(@NonNull Asset oldAsset, @NonNull Asset newAsset){
        if (oldAsset.getState() != newAsset.getState()){
            throw new AssetIllegalChangeException("State cannot be changed through general method");
        }
    }
}
