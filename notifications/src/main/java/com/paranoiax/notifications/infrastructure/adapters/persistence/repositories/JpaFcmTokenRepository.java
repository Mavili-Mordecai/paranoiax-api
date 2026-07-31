package com.paranoiax.notifications.infrastructure.adapters.persistence.repositories;

import com.paranoiax.notifications.infrastructure.adapters.persistence.entities.FcmTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaFcmTokenRepository extends JpaRepository<FcmTokenEntity, UUID> {

    @Modifying
    @Query(value = """
        INSERT INTO fcm_tokens (id, user_id, device_id, fcm_token, updated_at)
        VALUES (gen_random_uuid(), :userId, :deviceId, :fcmToken, now())
        ON CONFLICT (user_id, device_id) 
        DO UPDATE SET 
            fcm_token = EXCLUDED.fcm_token,
            updated_at = now()
        """, nativeQuery = true)
    void upsertToken(
            @Param("userId") UUID userId,
            @Param("deviceId") String deviceId,
            @Param("fcmToken") String fcmToken
    );

    @Query("SELECT f.fcmToken FROM FcmTokenEntity f WHERE f.userId = :userId")
    List<String> findAllTokensByUserId(@Param("userId") UUID userId);
}