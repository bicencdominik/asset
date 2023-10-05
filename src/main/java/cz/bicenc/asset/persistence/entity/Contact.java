package cz.bicenc.asset.persistence.entity;

import cz.bicenc.asset.persistence.model.ContactType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contact")
public class Contact extends ExtendedAuditEntity{

    @NotBlank
    @Column(name = "firstName",nullable = false,length = 50)
    private String firstName;

    @NotBlank
    @Column(name = "lastName", nullable = false,length = 50)
    private String lastName;

    @NonNull
    @Column(name = "type",nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactType type;

    @NonNull
    @Size(max = 9, min = 9)
    @Column(name = "phoneNumber",nullable = false,length = 9)
    private String phoneNumber;

    @Email
    @Column(name = "email",nullable = false)
    private String email;

    private int priorityOrder;

    private String description;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Contact contact = (Contact) object;
        return getId() != null && Objects.equals(getId(), contact.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
