package cz.bicenc.asset.persistence.entity;

import cz.bicenc.asset.persistence.model.GpsType;
import jakarta.persistence.*;
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
@Table(name = "gps")
public class Gps extends ExtendedAuditEntity {

    @NotNull
    @Column(name = "longitude",nullable = false)
    private double longitude;

    @NotNull
    @Column(name = "latitude",nullable = false)
    private double latitude;

    @NotNull
    @Column(name = "type",nullable = false)
    @Enumerated(EnumType.STRING)
    private GpsType type;


    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Gps gps = (Gps) object;
        return getId() != null && Objects.equals(getId(), gps.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longitude = " + longitude + ", " +
                "latitude = " + latitude + ", " +
                "type = " + type + ")";
    }
}
