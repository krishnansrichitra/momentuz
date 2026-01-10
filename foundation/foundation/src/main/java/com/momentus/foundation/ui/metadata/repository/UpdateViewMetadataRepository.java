package com.momentus.foundation.ui.metadata.repository;

import com.momentus.foundation.ui.metadata.model.ListMetadata;
import com.momentus.foundation.ui.metadata.model.UpdateViewMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UpdateViewMetadataRepository extends JpaRepository<UpdateViewMetadata,String> {

    List<UpdateViewMetadata> findByProfile_ProfileCodeInAndEntity(
            Collection<String> profileCodes,
            String entity
    );
}
