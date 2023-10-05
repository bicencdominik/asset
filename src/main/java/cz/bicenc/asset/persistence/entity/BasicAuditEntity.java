package cz.bicenc.asset.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public abstract class BasicAuditEntity implements IBasicAuditEntity<UUID> {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @CreatedDate
    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Size(max = 40)
    @CreatedBy
    @Column(name = "created_by", length = 40, updatable = false)
    private String createdBy;

    @NotNull
    @Builder.Default
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    @Size(max = 40)
    @Column(name = "deleted_by", length = 40)
    private String deletedBy;

    @Version
    @Column(name = "version")
    private Integer version;

}