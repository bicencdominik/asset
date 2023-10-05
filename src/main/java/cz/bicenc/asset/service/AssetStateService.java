package cz.bicenc.asset.service;

import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.service.action.ActivateAction;
import cz.bicenc.asset.service.action.DeactivateAction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssetStateService {

    private final ActivateAction activateAction;
    private final DeactivateAction deactivateAction;

    public Asset activate(@NonNull UUID assetId){
        return activateAction.doIt(assetId);
    }

    public Asset deactivate(@NonNull UUID assetId){
        return deactivateAction.doIt(assetId);
    }


}
