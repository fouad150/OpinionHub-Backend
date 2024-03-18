package com.youcode.opinionhub.Service;

import com.youcode.opinionhub.Entity.Reaction;

public interface ReactionService {

    Reaction addReaction(Long publicationId, String userEmail);

    void deleteReaction(Long publicationId, String userEmail);
}
