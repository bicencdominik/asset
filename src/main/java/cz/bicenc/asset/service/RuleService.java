package cz.bicenc.asset.service;

import com.querydsl.core.types.Predicate;
import cz.bicenc.asset.exception.AssetDuplicateRuleException;
import cz.bicenc.asset.exception.AssetException;
import cz.bicenc.asset.exception.AssetNotFoundException;
import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.persistence.entity.Rule;
import cz.bicenc.asset.persistence.model.RuleType;
import cz.bicenc.asset.persistence.repository.RuleRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class RuleService extends AbstractService<Rule, UUID> {

    private final AssetService assetService;

    public RuleService(RuleRepository repository, AssetService assetService) {
        super(repository, log);
        this.assetService = assetService;
    }

    @Override
    public Page<Rule> findAll(@NonNull Predicate predicate, @NonNull Pageable pageable) {
        throw new AssetException("Searching in rules is prohibited");
    }

    public Rule get(@NonNull UUID assetId, @NonNull RuleType type) {
        Map<RuleType,Rule> ruleMap = getRulesByAssetId(assetId);
        if (!ruleMap.containsKey(type)){
            throw new AssetNotFoundException("There is no \"" +type+"\" rule for Asset ID : " + assetId);
        }
        return ruleMap.get(type);
    }

    public Map<RuleType,Rule> getRulesByAssetId(UUID assetId) {
        Asset asset = assetService.getOrThrowNotFound(assetId);
        return asset.getRules();
    }

    @Transactional
    public Rule save(UUID assetId, Rule rule) {
        Asset asset = assetService.getOrThrowNotFound(assetId);
        Rule savedRule = saveWithHistory(rule);
        saveRuleInAsset(asset, savedRule);
        return savedRule;
    }

    public Rule update(@NonNull UUID assetId,@NonNull Rule rule) {
        Asset asset = assetService.getOrThrowNotFound(assetId);
        if (!asset.getRules().containsKey(rule.getType())){
            throw new AssetNotFoundException("Rule not found in asset " + asset.getId());
        }
        return saveWithHistory(rule);
    }

    private void saveRuleInAsset(@NonNull Asset asset, @NonNull Rule rule) {
        if (asset.getRules().containsKey(rule.getType())) {
            throw new AssetDuplicateRuleException("Rule " + rule.getType() +" alredy exist in Asset ID : " + asset.getId());
        } else {
            asset.getRules().put(rule.getType(), rule);
        }
        assetService.saveWithHistory(asset);
    }
}
