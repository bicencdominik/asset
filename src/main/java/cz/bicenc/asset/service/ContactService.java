package cz.bicenc.asset.service;

import com.querydsl.core.types.Predicate;
import cz.bicenc.asset.persistence.entity.Contact;
import cz.bicenc.asset.persistence.repository.ContactRepository;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ContactService extends AbstractService<Contact, UUID> {

    private final ContactRepository repository;

    private final EntityManager entityManager;

    public ContactService(ContactRepository repository, EntityManager entityManager) {
        super(repository, log);
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public Page<Contact> findAll(@NonNull Predicate predicate, @NonNull Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    public Contact save(@NonNull Contact contact) {
        return saveWithHistory(contact);
    }

    public Contact update(@NonNull UUID contactId, @NonNull Contact contact) {
        return saveWithHistory(contact);
    }
}
