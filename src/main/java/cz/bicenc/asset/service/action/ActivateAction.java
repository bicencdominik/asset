package cz.bicenc.asset.service.action;

import cz.bicenc.asset.persistence.model.AssetState;
import cz.bicenc.asset.service.AssetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivateAction extends BasicAction{
    public ActivateAction(AssetService service) {
        super(service, log, AssetState.ACTIVE);
    }
}
