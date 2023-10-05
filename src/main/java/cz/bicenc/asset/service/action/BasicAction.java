package cz.bicenc.asset.service.action;

import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.persistence.model.AssetState;
import cz.bicenc.asset.service.AssetService;
import lombok.NonNull;
import org.slf4j.Logger;

import java.util.UUID;

public class BasicAction {

    private final AssetService service;
    private final Logger log;
    private final AssetState targetState;

    public BasicAction(AssetService service, Logger log, AssetState targetState) {
        this.service = service;
        this.log = log;
        this.targetState = targetState;
    }

    public Asset doIt(@NonNull UUID assetId){
        Asset asset = service.getOrThrowNotFound(assetId);

        if (targetState.equals(asset.getState())){
            log.debug("Asset ID : {} is alredy is state {}",assetId,targetState);
            return asset;
        }

        asset.setState(targetState);

        checkStatus(asset);

        return service.save(asset);
    }

    private void checkStatus(Asset asset) {
    }
}
