package cz.bicenc.asset.persistence.mapper;

import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.persistence.entity.Place;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {Mapper.class})
public interface Mapper {

    Place toPlace(Asset asset);

}
