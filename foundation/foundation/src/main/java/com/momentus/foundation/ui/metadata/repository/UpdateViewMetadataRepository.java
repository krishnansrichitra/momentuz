package com.momentus.foundation.ui.metadata.repository;

import com.momentus.foundation.ui.metadata.model.UpdateViewMetadata;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateViewMetadataRepository extends JpaRepository<UpdateViewMetadata, String> {

  List<UpdateViewMetadata> findByProfile_ProfileCodeInAndEntityOrderByProfileLevelDesc(
      Collection<String> profileCodes, String entity);
}
