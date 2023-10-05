package cz.bicenc.asset.service;

import com.querydsl.core.types.Predicate;
import cz.bicenc.asset.exception.AssetException;
import cz.bicenc.asset.persistence.entity.Gps;
import cz.bicenc.asset.persistence.repository.GpsRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@Slf4j
public class GpsService extends AbstractService<Gps, UUID>{

    private final GpsRepository repository;

    public GpsService(GpsRepository repository) {
        super(repository, log);
        this.repository = repository;
    }

    @Override
    public Page<Gps> findAll(@NonNull Predicate predicate, @NonNull Pageable pageable) {
        throw new AssetException("Searching in rules is prohibited");
    }

    public Gps update(UUID gpsId, Gps gps) {
        return saveWithHistory(gps);
    }
}
