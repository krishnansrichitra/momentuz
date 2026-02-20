package com.momentus.foundation.ui.metadata.service;

import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.service.OrgProfileService;
import com.momentus.foundation.ui.metadata.dto.ListMetadataDTO;
import com.momentus.foundation.ui.metadata.dto.MetadataDTOHelper;
import com.momentus.foundation.ui.metadata.dto.UpdateViewMetadataDTO;
import com.momentus.foundation.ui.metadata.model.ListMetadata;
import com.momentus.foundation.ui.metadata.model.UpdateViewMetadata;
import com.momentus.foundation.ui.metadata.repository.ListMetadataRepository;
import com.momentus.foundation.ui.metadata.repository.UpdateViewMetadataRepository;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MetadataService {

  @Autowired ListMetadataRepository listMetadataRepository;

  @Autowired OrgProfileService orgProfileService;

  @Autowired MetadataDTOHelper metadataDTOHelper;

  @Autowired UpdateViewMetadataRepository updateViewMetadataRepository;

  @Cacheable("ListMetadata")
  public ListMetadataDTO getListMetadata(Long orgId, String entity, Locale locale) {

    OrgProfile orgProfile = orgProfileService.getProfileForGroup("GNL", orgId);
    if (orgProfile != null) {
      List<ListMetadata> listMetadataList =
          listMetadataRepository.findByProfile_ProfileCodeInAndEntity(
              getProfileCodes(orgProfile.getProfile().getFullProfileCode()), entity);
      if (!CollectionUtils.isEmpty(listMetadataList)) {
        return metadataDTOHelper.makeListMetadataDTO(
            listMetadataList.get(listMetadataList.size() - 1), locale);
      }
    }
    return null;
  }

  @Cacheable("UpdateViewMetadata")
  public UpdateViewMetadataDTO getUpdateViewMetadata(
      Long orgId, String entity, Locale locale, String mode) {

    OrgProfile orgProfile = orgProfileService.getProfileForGroup("GNL", orgId);
    if (orgProfile != null) {
      List<UpdateViewMetadata> updateViewMetadatas =
          updateViewMetadataRepository.findByProfile_ProfileCodeInAndEntity(
              getProfileCodes(orgProfile.getProfile().getFullProfileCode()), entity);
      if (!CollectionUtils.isEmpty(updateViewMetadatas)) {
        return metadataDTOHelper.makeUpdateViewDTO(
            updateViewMetadatas.get(updateViewMetadatas.size() - 1), locale, mode);
      }
    }
    return null;
  }

  private List<String> getProfileCodes(String fullProfile) {
    if (fullProfile == null || fullProfile.isBlank()) return Collections.emptyList();

    String[] parts = fullProfile.split("-");
    List<String> result = new ArrayList<>(Arrays.asList(parts));

    Collections.reverse(result);
    return result;
  }
}
