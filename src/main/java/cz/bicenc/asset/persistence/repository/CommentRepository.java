package cz.bicenc.asset.persistence.repository;

import cz.bicenc.asset.persistence.entity.Comment;
import cz.bicenc.asset.persistence.entity.QComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID>,
        QuerydslPredicateExecutor<Comment>,
        QuerydslBinderCustomizer<QComment> {

    @Override
    default void customize(QuerydslBindings bindings, QComment root) {

    }
}