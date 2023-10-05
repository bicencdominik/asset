package cz.bicenc.asset.service;

import com.querydsl.core.types.Predicate;
import cz.bicenc.asset.exception.AssetDuplicateRuleException;
import cz.bicenc.asset.exception.AssetException;
import cz.bicenc.asset.exception.AssetNotFoundException;
import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.persistence.entity.Rule;
import cz.bicenc.asset.persistence.model.RuleType;
import cz.bicenc.asset.persistence.repository.RuleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private AssetService assetService;

    @InjectMocks
    private RuleService ruleService;

    @Test
    void findAll_ShouldInvokeContextException() {
        Predicate predicate = mock(Predicate.class);
        Pageable pageable = mock(Pageable.class);
        assertThrows(AssetException.class, () -> ruleService.findAll(predicate, pageable));
    }

    @Test
    void createRule() {
        Rule wantCreate = Rule.builder()
                .id(UUID.randomUUID())
                .type(RuleType.USUAL)
                .text("text pravidla")
                .build();

        Map<RuleType, Rule> ruleMap = new HashMap<>();

        Asset asset = Asset.builder()
                .id(UUID.randomUUID())
                .rules(ruleMap)
                .build();

        when(assetService.getOrThrowNotFound(asset.getId())).thenReturn(asset);
        when(ruleRepository.save(any())).thenReturn(wantCreate);

        Rule created = ruleService.save(asset.getId(),wantCreate);


        assertEquals(wantCreate,created);

    }

    @Test
    void createRule_ShouldInvokeDuplicateException() {
        Rule wantCreate = Rule.builder()
                .id(UUID.randomUUID())
                .type(RuleType.USUAL)
                .text("text pravidla")
                .build();


        Rule rule = Rule.builder()
                .id(UUID.randomUUID())
                .type(RuleType.USUAL)
                .text("text pravidla")
                .build();

        Map<RuleType, Rule> ruleMap = new HashMap<>();
        ruleMap.put(rule.getType(), rule);

        Asset asset = Asset.builder()
                .id(UUID.randomUUID())
                .rules(ruleMap)
                .build();

        when(assetService.getOrThrowNotFound(asset.getId())).thenReturn(asset);
        when(ruleRepository.save(rule)).thenReturn(rule);

        assertThrows(AssetDuplicateRuleException.class, () -> ruleService.save(asset.getId(), rule));
    }

    @Test
    void getRulesByAssetId() {
        UUID id = UUID.randomUUID();

        Rule rule = Rule.builder()
                .id(id)
                .text("text")
                .type(RuleType.IMPORTANT)
                .build();

        Rule rule1 = Rule.builder()
                .id(id)
                .text("text")
                .type(RuleType.USUAL)
                .build();

        Map<RuleType, Rule> ruleMap = new HashMap<>();
        ruleMap.put(rule.getType(), rule);
        ruleMap.put(rule1.getType(), rule1);

        Asset asset = Asset.builder()
                .id(UUID.randomUUID())
                .rules(ruleMap)
                .build();

        when(assetService.getOrThrowNotFound(asset.getId())).thenReturn(asset);

        Map<RuleType, Rule> findRules = ruleService.getRulesByAssetId(asset.getId());

        assertEquals(2, findRules.size());

    }

    @Test
    void deleteRule_ShouldReturnRuleWithDeleted() {
        UUID id = UUID.randomUUID();

        Rule rule = Rule.builder()
                .id(id)
                .text("text")
                .type(RuleType.IMPORTANT)
                .build();

        when(ruleRepository.findById(id)).thenReturn(Optional.of(rule));
        when(ruleRepository.save(rule)).thenReturn(rule);

        Rule deletedRule = ruleService.deleteWithHistory(id);


        assertNotNull(deletedRule.getDeletedBy());
        assertNotNull(deletedRule.getDeletedTime());
        assertTrue(deletedRule.getDeleted());
    }

    @Test
    void updateDeletedRule_ShouldInvokeDeletedException() {
        UUID id = UUID.randomUUID();

        Rule rule = Rule.builder()
                .id(id)
                .text("text")
                .type(RuleType.IMPORTANT)
                .deleted(true)
                .deletedBy("DOMINIK <3")
                .deletedTime(LocalDateTime.now())
                .build();

        Map<RuleType, Rule> ruleMap = new HashMap<>();
        ruleMap.put(rule.getType(), rule);

        Asset asset = Asset.builder()
                .id(UUID.randomUUID())
                .rules(ruleMap)
                .build();

        when(assetService.getOrThrowNotFound(asset.getId())).thenThrow(AssetNotFoundException.class);

        assertThrows(AssetNotFoundException.class, () -> {
            ruleService.update(asset.getId(), rule);
        });
    }

    @Test
    void updateRule_ruleNotExistInAsset_ShouldInvoceNotFound() {
        UUID id = UUID.randomUUID();

        Rule rule = Rule.builder()
                .id(id)
                .text("text")
                .type(RuleType.IMPORTANT)
                .build();

        Map<RuleType, Rule> ruleMap = new HashMap<>();

        Asset asset = Asset.builder()
                .id(UUID.randomUUID())
                .rules(ruleMap)
                .build();

        when(assetService.getOrThrowNotFound(asset.getId())).thenReturn(asset);

        assertThrows(AssetNotFoundException.class, () -> {
            ruleService.update(asset.getId(), rule);
        });
    }


}