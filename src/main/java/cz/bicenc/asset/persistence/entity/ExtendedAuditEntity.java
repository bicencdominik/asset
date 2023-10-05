package cz.bicenc.asset.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public abstract class ExtendedAuditEntity extends BasicAuditEntity {

    @LastModifiedDate
    @Column(name = "changed")
    private LocalDateTime changed;

    @LastModifiedBy
    @Size(max = 40)
    @Column(name = "changed_by", length = 40)
    private String changedBy;

}