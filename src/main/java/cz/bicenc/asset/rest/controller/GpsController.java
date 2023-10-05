package cz.bicenc.asset.rest.controller;

import cz.bicenc.asset.exception.AssetBadRequestException;
import cz.bicenc.asset.persistence.entity.Gps;
import cz.bicenc.asset.service.GpsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Tag(name = "Gps API")
@RestController
@RequestMapping("${spring.data.rest.base-path}/context/assetGps/")
@RequiredArgsConstructor
public class GpsController {

    private final GpsService service;

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Get an gps by type")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Gps.class)))
    public Gps getOne(@PathVariable("id") UUID GpsId) {
        return service.get(GpsId);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Update an existing gps")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(schema = @Schema(implementation = Gps.class)))
    public Gps update(@PathVariable("id") UUID gpsId, @RequestBody Gps gps) {
        if (Objects.isNull(gps.getId())) {
            throw new AssetBadRequestException("Empty ID");
        } else if (!Objects.equals(gps.getId(), gpsId)) {
            throw new AssetBadRequestException("ID doesnt match");
        }
        return service.update(gpsId,gps);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Delete an exsiting gps")
    public void delete(@PathVariable("id") UUID gpsId){
        service.delete(gpsId);
    }

}
