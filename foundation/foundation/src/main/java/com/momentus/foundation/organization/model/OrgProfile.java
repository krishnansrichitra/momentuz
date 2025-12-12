package com.momentus.foundation.organization.model;


import com.momentus.foundation.profile.model.Profile;
import com.momentus.foundation.profile.model.ProfileGroup;
import jakarta.persistence.*;

@Entity
@Table(name = "org_profile")
public class OrgProfile {

    @Id
    Long id;

    @ManyToOne(fetch = FetchType.EAGER , optional = false  )
    @JoinColumn(name = "org_id",referencedColumnName = "id")
    Organization organization;

    @ManyToOne(fetch = FetchType.LAZY , optional = false  )
    @JoinColumn(name = "profile_id",referencedColumnName = "id")
    Profile profile;

    @ManyToOne(fetch = FetchType.LAZY , optional = false  )
    @JoinColumn(name = "profile_group_code",referencedColumnName = "profileGroupCode")
    ProfileGroup profileGroup ;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted =false ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ProfileGroup getProfileGroup() {
        return profileGroup;
    }

    public void setProfileGroup(ProfileGroup profileGroup) {
        this.profileGroup = profileGroup;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
