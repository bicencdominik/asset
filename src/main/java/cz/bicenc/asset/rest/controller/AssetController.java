package cz.bicenc.asset.rest.controller;

import com.querydsl.core.types.Predicate;
import cz.bicenc.asset.exception.AssetBadRequestException;
import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.persistence.repository.AssetRepository;
import cz.bicenc.asset.service.AssetService;
import cz.bicenc.asset.service.AssetStateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Tag(name = "Asset API")
@RestController
@RequestMapping("${spring.data.rest.base-path}/context/asset")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService service;
    private final AssetStateService stateService;
    
    @GetMapping()
    @ResponseBody
    @Operation(summary = "Find assets")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Page.class)))
    public Page<Asset> find(
            @Parameter(description = "Query predicate") @QuerydslPredicate(root = Asset.class, bindings = AssetRepository.class) Predicate predicate,
            @Parameter(description = "Pageable parameters") @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return service.findAll(predicate,pageable);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Get an asset by Id")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
    public Asset getOne(@PathVariable("id") UUID assetId) {
        return service.get(assetId);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create new asset")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
    public Asset create(@RequestBody Asset asset) {
        asset.setId(null);
        asset.setCreated(null);
        asset.setCreatedBy(null);
        return service.save(asset);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Update an existing asset")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
    public Asset update(@PathVariable("id") UUID assetId, @RequestBody Asset asset) {
        if (Objects.isNull(asset.getId())) {
            throw new AssetBadRequestException("Empty ID");
        } else if (!Objects.equals(asset.getId(), assetId)) {
            throw new AssetBadRequestException("ID doesnt match");
        }
        return service.update(assetId,asset);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Delete an asset by Id")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
    public void delete(@PathVariable("id") UUID assetId) {
        service.deleteWithHistory(assetId);
    }

    @PostMapping("/{id}/activate")
    @ResponseBody
    @Operation(summary = "Set state of asset to activate")
    @ApiResponse(responseCode = "200",description = "Successful operation",content = @Content(schema = @Schema(implementation = Asset.class)))
    public Asset activate(@PathVariable("id") UUID assetId){
        return stateService.activate(assetId);
    }

    @PostMapping("/{id}/deactivate")
    @ResponseBody
    @Operation(summary = "Set state of asset to inactive")
    @ApiResponse(responseCode = "200",description = "Successful operation",content = @Content(schema = @Schema(implementation = Asset.class)))
    public Asset deactivate(@PathVariable("id") UUID assetId){
        return stateService.deactivate(assetId);
    }


    

}
