package cz.bicenc.asset.service.action;

import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.persistence.model.AssetState;
import cz.bicenc.asset.service.AssetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeactivateActionTest {

    @Mock
    private AssetService service;

    @InjectMocks
    private DeactivateAction deactivateAction;


    @Test
    void toInactive_fromInactive(){
        Asset asset = Asset.builder()
                .id(UUID.randomUUID())
                .state(AssetState.INACTIVE)
                .build();

        when(service.getOrThrowNotFound(asset.getId())).thenReturn(asset);

        Asset newAsset = deactivateAction.doIt(asset.getId());

        assertEquals(AssetState.INACTIVE,newAsset.getState());
    }

    @Test
    void toInactive_fromActive(){
        Asset asset = Asset.builder()
                .id(UUID.randomUUID())
                .state(AssetState.ACTIVE)
                .build();

        when(service.getOrThrowNotFound(asset.getId())).thenReturn(asset);
        when(service.save(asset)).thenReturn(asset);

        Asset newAsset = deactivateAction.doIt(asset.getId());

        assertEquals(AssetState.INACTIVE,newAsset.getState());
    }

}