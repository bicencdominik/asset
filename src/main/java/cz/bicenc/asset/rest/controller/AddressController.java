package cz.bicenc.asset.rest.controller;

import cz.bicenc.asset.exception.AssetBadRequestException;
import cz.bicenc.asset.persistence.entity.Address;
import cz.bicenc.asset.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Tag(name = "Address API")
@RestController
@RequestMapping("${spring.data.rest.base-path}/context/asset/{assetId}/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;

    @GetMapping("/{addressId}")
    @ResponseBody
    @Operation(summary = "Get an Address by Id")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Address.class)))
    public Address getOne(@PathVariable("assetId") UUID assetId, @PathVariable("addressId") UUID addressId) {
        return service.get(assetId,addressId);
    }

    @GetMapping()
    @ResponseBody
    @Operation(summary = "Get an rules by addressId")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Address.class)))
    public Address getAddressByAsset(@PathVariable("assetId") UUID assetId){
        return service.getAddressByAssetId(assetId);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create new address")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Address.class)))
    public Address create(@PathVariable("assetId") UUID assetId ,@RequestBody Address address) {
        address.setId(null);
        address.setCreated(null);
        address.setCreatedBy(null);
        return service.save(assetId,address);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Update an existing address")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Address.class)))
    public Address update(@PathVariable("assetId") UUID assetId, @PathVariable("id") UUID addressId, @RequestBody Address address) {
        if (Objects.isNull(address.getId())) {
            throw new AssetBadRequestException("Empty ID");
        } else if (!Objects.equals(address.getId(), assetId)) {
            throw new AssetBadRequestException("ID doesnt match");
        }
        return service.update(assetId,address);
    }


}
