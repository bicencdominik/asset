package cz.bicenc.asset.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryInit;
import com.querydsl.core.annotations.QueryType;
import cz.bicenc.asset.persistence.model.AssetState;
import cz.bicenc.asset.persistence.model.RuleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "asset")
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(                              //TODO tohle asi pak nejspise bude v DTO
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        use = JsonTypeInfo.Id.NAME,
        visible = false
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Asset.class, name = "asset"),
        @JsonSubTypes.Type(value = Place.class, name = "place")
})
public class Asset extends ExtendedAuditEntity {

    @NotBlank
    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Column(name = "code", length = 10)
    private String code;

    @NotNull
    @Column(name = "customerId", nullable = false)
    private UUID customerId;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Builder.Default
    private AssetState state = AssetState.ACTIVE;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id")
    private Gps location;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "asset_id")
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "asset", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /*@OneToMany(mappedBy = "asset", orphanRemoval = true)
    @JsonManagedReference
    private List<Rule> rules = new ArrayList<>();*/

    @OneToMany
    @JoinTable(name = "asset_rule_mapping",
      joinColumns = {@JoinColumn(name = "asset_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rule_id",referencedColumnName = "id")}
    )
    @MapKeyEnumerated(EnumType.STRING)
    private Map<RuleType, Rule> rules = new EnumMap<>(RuleType.class);

    @JsonIgnore
    @Transient
    @QueryInit("addressLike")
    @QueryType(PropertyType.STRING)
    private String addressLike;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Asset asset = (Asset) object;
        return getId() != null && Objects.equals(getId(), asset.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "name = " + getName() + ", " +
                "code = " + getCode() + ", " +
                "customerId = " + getCustomerId() + ", " +
                "state = " + getState() + ", " +
                "location = " + getLocation();
    }
}
