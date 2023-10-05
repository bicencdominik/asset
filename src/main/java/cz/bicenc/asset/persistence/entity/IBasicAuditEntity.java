package cz.bicenc.asset.persistence.entity;

import java.time.LocalDateTime;

public interface IBasicAuditEntity<I> {

     I getId();
     LocalDateTime getCreated();
     String getCreatedBy();
     Boolean getDeleted();
     LocalDateTime getDeletedTime() ;
     String getDeletedBy();
     Integer getVersion();
     void setId(I id);
     void setCreated(LocalDateTime created);
     void setCreatedBy(String createdBy);
     void setDeleted(Boolean deleted);
     void setDeletedTime(LocalDateTime deletedTime) ;
     void setDeletedBy(String deletedBy);
     void setVersion(Integer version);
    
}
