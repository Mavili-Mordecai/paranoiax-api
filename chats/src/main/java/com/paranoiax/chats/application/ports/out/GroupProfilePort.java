package com.paranoiax.chats.application.ports.out;

import com.paranoiax.chats.domain.models.groupProfile.GroupProfile;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfileId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GroupProfilePort {
    Optional<GroupProfile> findById(GroupProfileId groupProfileId);
    List<GroupProfile> findAllByIds(Collection<GroupProfileId> ids);
    GroupProfile insert(GroupProfile groupProfile);
    GroupProfile update(GroupProfile groupProfile);
    void delete(GroupProfileId id);
}
