package com.momentus.foundation.ui.metadata.repository;

import com.momentus.foundation.ui.metadata.model.ListMetadata;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListMetadataRepository extends JpaRepository<ListMetadata, String> {

  List<ListMetadata> findByProfile_ProfileCodeInAndEntityOrderByProfileLevelDesc(
      Collection<String> profileCodes, String entity);
}
