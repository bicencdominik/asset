package cz.bicenc.asset.persistence.repository;

import cz.bicenc.asset.persistence.entity.Country;
import cz.bicenc.asset.persistence.entity.QCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String>,
        QuerydslPredicateExecutor<Country>,
        QuerydslBinderCustomizer<QCountry> {

    @Override
    default void customize(QuerydslBindings bindings, QCountry root) {

    }
}