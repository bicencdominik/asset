package cz.bicenc.asset.persistence.repository;

import cz.bicenc.asset.persistence.entity.QRule;
import cz.bicenc.asset.persistence.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RuleRepository extends JpaRepository<Rule, UUID>,
        QuerydslPredicateExecutor<Rule>,
        QuerydslBinderCustomizer<QRule> {

    @Override
    default void customize(QuerydslBindings bindings, QRule root) {

    }

}