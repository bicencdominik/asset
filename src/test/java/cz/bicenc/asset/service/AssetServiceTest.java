package cz.bicenc.asset.service;

import com.querydsl.core.types.Predicate;
import cz.bicenc.asset.exception.AssetIllegalChangeException;
import cz.bicenc.asset.exception.AssetNotFoundException;
import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.persistence.entity.Gps;
import cz.bicenc.asset.persistence.model.AssetState;
import cz.bicenc.asset.persistence.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    private AssetRepository repository;

    @InjectMocks
    private AssetService service;

    private Asset asset;


    @BeforeEach
    void setUp() {
        asset = Asset.builder()
                .code("come code")
                .customerId(UUID.randomUUID())
                .id(UUID.randomUUID())
                .name("name of asset")
                .state(AssetState.ACTIVE)
                .location(Gps.builder()
                        .id(UUID.randomUUID())
                        .longitude(15.3321)
                        .latitude(23.3213178)
                        .build())
                .build();
    }

    @Test
    void findAll_ShouldReturnPageOfAssets() {
        Predicate predicate = mock(Predicate.class);
        Pageable pageable = mock(Pageable.class);
        Page<Asset> expectedPage = mock(Page.class);

        when(repository.findAll(predicate, pageable)).thenReturn(expectedPage);

        Page<Asset> actualPage = service.findAll(predicate, pageable);

        assertEquals(expectedPage, actualPage);
        verify(repository, times(1)).findAll(predicate, pageable);
    }

    @Test
    void getOne_ShouldReturnAsset(){
        when(repository.findById(any())).thenReturn(Optional.of(asset));

        Asset actualAsset = service.get(asset.getId());

        assertEquals(asset,actualAsset);
    }

    @Test
    void getOne_ShouldInvokeNotFoundException(){
        UUID wantedId = UUID.randomUUID();

        when(repository.findById(any())).thenReturn(Optional.ofNullable(null));

        assertThrows(AssetNotFoundException.class, () -> service.get(wantedId));
    }
    @Test
    void save_ShouldReturnSavedAsset() {
        when(repository.save(asset)).thenReturn(asset);

        Asset savedAsset = repository.save(asset);

        assertEquals(asset, savedAsset);
        verify(repository, times(1)).save(asset);
    }

    @Test
    void update_ShouldReturnUpdatedAsset() {
        Asset newAsset = Asset.builder()
                .id(asset.getId())
                .code("come code")
                .state(AssetState.ACTIVE)
                .build();
        when(repository.save(newAsset)).thenReturn(newAsset);
        when(repository.findById(asset.getId())).thenReturn(Optional.of(asset));

        Asset updatedAsset = service.update(asset.getId(),newAsset);

        assertEquals(newAsset,updatedAsset);

    }

    @Test
    void update_ShouldInvokeIllegalChangeException() {
        Asset newAsset = Asset.builder()
                .id(asset.getId())
                .code("come code")
                .state(AssetState.INACTIVE)
                .build();

        when(repository.findById(asset.getId())).thenReturn(Optional.of(asset));

        assertThrows(AssetIllegalChangeException.class, () -> service.update(asset.getId(), newAsset));

    }

    @Test
    void delete_ShouldReturnAssetWithDeleted() {
        UUID id = asset.getId();

        when(repository.findById(id)).thenReturn(Optional.of(asset));
        when(repository.save(eq(asset))).thenReturn(asset);

        Asset deletedAsset = service.deleteWithHistory(id);


        assertNotNull(deletedAsset.getDeletedBy());
        assertNotNull(deletedAsset.getDeletedTime());
        assertTrue(deletedAsset.getDeleted());

    }

}