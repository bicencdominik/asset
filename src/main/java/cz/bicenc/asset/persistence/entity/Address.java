package cz.bicenc.asset.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address extends ExtendedAuditEntity{

    @NotNull
    @Column(name = "name",nullable = false,length = 60)
    private String name;

    @Column(name = "code",length = 10)
    private String code;

    @NotBlank
    @Column(name = "street",nullable = false,length = 60)
    private String street;

    @NotBlank
    @Column(name = "zipCode",nullable = false,length = 10)
    private String zipCode;

    @NotBlank
    @Column(name = "city",nullable = false,length = 60)
    private String city;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "gps_id")
    private Gps gps;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST) //TODO zeptat se ondry jestli je to v pohode , protoze ja si takhle jen pomohl pri testovani
    @JoinColumn(name = "country_id",nullable = false)
    private Country country;


    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Address address = (Address) object;
        return getId() != null && Objects.equals(getId(), address.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "code = " + code + ", " +
                "street = " + street + ", " +
                "zipCode = " + zipCode + ", " +
                "city = " + city + ", " +
                "gps = " + gps + ", " +
                "country = " + country + ")";
    }
}
