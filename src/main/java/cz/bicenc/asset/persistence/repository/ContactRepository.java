package cz.bicenc.asset.persistence.repository;

import cz.bicenc.asset.persistence.entity.Contact;
import cz.bicenc.asset.persistence.entity.QContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID>,
        QuerydslPredicateExecutor<Contact>,
        QuerydslBinderCustomizer<QContact> {

    @Override
    default void customize(QuerydslBindings bindings, QContact root) {

    }

}