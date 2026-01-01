package com.momentus.foundation.ui.metadata.repository;

import com.momentus.foundation.menus.model.MenuSet;
import com.momentus.foundation.ui.metadata.model.ListMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ListMetadataRepository extends JpaRepository<ListMetadata,Long> {

    List<ListMetadata> findByProfile_ProfileCodeInAndEntity(
            Collection<String> profileCodes,
            String entity
    );
}
