package cz.bicenc.asset.persistence.repository;

import cz.bicenc.asset.persistence.entity.Gps;
import cz.bicenc.asset.persistence.entity.QGps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface GpsRepository extends JpaRepository<Gps, UUID>,
        QuerydslPredicateExecutor<Gps>,
        QuerydslBinderCustomizer<QGps> {

    @Override
    default void customize(QuerydslBindings bindings, QGps root) {

    }
}