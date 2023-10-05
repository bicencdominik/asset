package cz.bicenc.asset.rest.controller;

import cz.bicenc.asset.exception.AssetBadRequestException;
import cz.bicenc.asset.persistence.entity.Rule;
import cz.bicenc.asset.persistence.model.RuleType;
import cz.bicenc.asset.service.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Tag(name = "Rule API")
@RestController
@RequestMapping("${spring.data.rest.base-path}/context/{assetId}/rule")
@RequiredArgsConstructor
public class RuleController {
    
    private final RuleService service;

    @GetMapping("/{type}")
    @ResponseBody
    @Operation(summary = "Get an rule by type")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Rule.class)))
    public Rule getOne(@PathVariable("assetId") UUID assetId,@PathVariable("type") RuleType type) {
        return service.get(assetId,type);
    }

    @GetMapping()
    @ResponseBody
    @Operation(summary = "Get an rules by assetId")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Rule.class)))
    public List<Rule> getRulesByAsset(@PathVariable("assetId") UUID assetId){
        return new ArrayList<>(service.getRulesByAssetId(assetId).values());
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create new rule")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Rule.class)))
    public Rule create(@PathVariable("assetId") UUID assetId ,@RequestBody Rule rule) {
        rule.setId(null);
        rule.setCreated(null);
        rule.setCreatedBy(null);
        return service.save(assetId,rule);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Update an existing rule")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Rule.class)))
    public Rule update(@PathVariable("assetId") UUID assetId,@PathVariable("id") UUID ruleId, @RequestBody Rule rule) {
        if (Objects.isNull(rule.getId())) {
            throw new AssetBadRequestException("Empty ID");
        } else if (!Objects.equals(rule.getId(), ruleId)) {
            throw new AssetBadRequestException("ID doesnt match");
        }
        return service.update(assetId,rule);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Delete an rule by Id")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Rule.class)))
    public void delete(@PathVariable("id") UUID ruleId) {
        service.deleteWithHistory(ruleId);
    }

}
