package cz.bicenc.asset.persistence.repository;

import cz.bicenc.asset.persistence.entity.Address;
import cz.bicenc.asset.persistence.entity.QAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID>,
        QuerydslPredicateExecutor<Address>,
        QuerydslBinderCustomizer<QAddress> {

    @Override
    default void customize(QuerydslBindings bindings, QAddress root) {
    }

}