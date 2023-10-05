package cz.bicenc.asset.service;

import com.querydsl.core.types.Predicate;
import cz.bicenc.asset.exception.AssetException;
import cz.bicenc.asset.persistence.entity.Address;
import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.persistence.entity.Place;
import cz.bicenc.asset.persistence.mapper.Mapper;
import cz.bicenc.asset.persistence.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AddressService extends AbstractService<Address,UUID> {

    private final AddressRepository repository;
    private final AssetService assetService;
    private final Mapper mapper;

    public AddressService(AddressRepository repository, AssetService assetService, Mapper mapper) {
        super(repository, log);
        this.repository = repository;
        this.assetService = assetService;
        this.mapper = mapper;
    }

    @Transactional
    public Address save(@NonNull UUID assetId,@NonNull Address address) {
        Asset asset = assetService.getOrThrowNotFound(assetId);
        address = saveWithHistory(address);
        Place place = mapper.toPlace(asset);
        place.setAddress(address);
        assetService.save(place);
        return place.getAddress();
    }

    public Address getAddressByAssetId(@NonNull UUID assetId) {
        Asset asset = assetService.getOrThrowNotFound(assetId);
        Place place = assetIsPlace(asset);
        return place.getAddress();
    }

    public Address update(@NonNull UUID assetId,@NonNull Address address) {
        return saveWithHistory(address);
    }

    public Address get(@NonNull UUID assetId,@NonNull UUID addressId) {
        return getOrThrowNotFound(addressId);
    }

    @Override
    public Page<Address> findAll(@NonNull Predicate predicate, @NonNull Pageable pageable) {
        return findAll(predicate,pageable);
    }

    private Place assetIsPlace(@NonNull Asset asset){
        if (!(asset instanceof Place)){
            throw new AssetException("asset id " + asset.getId() + " is not place ");
        }
        return (Place) asset;
    }
}
