package cz.bicenc.asset.persistence.repository;

import com.querydsl.core.BooleanBuilder;
import cz.bicenc.asset.persistence.entity.Asset;
import cz.bicenc.asset.persistence.entity.Place;
import cz.bicenc.asset.persistence.entity.QAsset;
import cz.bicenc.asset.persistence.entity.QPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID>,
        QuerydslPredicateExecutor<Asset>,
        QuerydslBinderCustomizer<QAsset> {

    @Override
    default void customize(QuerydslBindings bindings, QAsset root) {

        bindings.bind(root.addressLike).first((path, value) -> {
            QPlace qPlace = root.as(QPlace.class);
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(root.instanceOf(Place.class));
            booleanBuilder.or(qPlace.address.street.containsIgnoreCase(value));
            booleanBuilder.or(qPlace.address.city.containsIgnoreCase(value));
            booleanBuilder.or(qPlace.address.name.containsIgnoreCase(value));
            booleanBuilder.or(qPlace.address.zipCode.containsIgnoreCase(value));

            return booleanBuilder.getValue();
        });

    }



}