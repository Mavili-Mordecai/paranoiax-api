package com.paranoiax.users.domain;

import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.exceptions.DomainException;

import java.time.Instant;
import java.util.Map;

public class Require {
    public static <T> T notNull(T obj, DomainErrorCode code, String fieldName) {
        if (obj == null) {
            throw new DomainException(
                    code,
                    Map.of("field", fieldName),
                    String.format(code.getDefaultMessage(), fieldName)
            );
        }
        return obj;
    }

    public static String notBlank(String obj, DomainErrorCode code, String fieldName) {
        if (obj == null || obj.isBlank()) {
            throw new DomainException(
                    code,
                    Map.of("field", fieldName),
                    String.format(code.getDefaultMessage(), fieldName)
            );
        }
        return obj;
    }

    public static String hasLength(String obj, String fieldName, Integer minLength, Integer maxLength) {
        if (obj == null || obj.length() < minLength || obj.length() > maxLength) {
            throw new DomainException(
                    DomainErrorCode.INVALID_LENGTH,
                    Map.of(
                            "field", fieldName,
                            "minLength", minLength,
                            "maxLength", maxLength
                    ),
                    String.format(DomainErrorCode.INVALID_LENGTH.getDefaultMessage(), fieldName, minLength, maxLength)
            );
        }
        return obj;
    }

    public static String hasLengthIfPresent(String obj, String fieldName, Integer minLength, Integer maxLength) {
        if (obj != null && (obj.length() < minLength || obj.length() > maxLength)) {
            throw new DomainException(
                    DomainErrorCode.INVALID_LENGTH,
                    Map.of(
                            "field", fieldName,
                            "minLength", minLength,
                            "maxLength", maxLength
                    ),
                    String.format(DomainErrorCode.INVALID_LENGTH.getDefaultMessage(), fieldName, minLength, maxLength)
            );
        }
        return obj;
    }

    public static String matches(String obj, String fieldName, String regex) {
        if (obj == null || !obj.matches(regex)) {
            throw new DomainException(
                    DomainErrorCode.INVALID_FORMAT,
                    Map.of("field", fieldName),
                    String.format(DomainErrorCode.INVALID_FORMAT.getDefaultMessage(), fieldName)
            );
        }
        return obj;
    }

    public static String matchesIfPresent(String obj, String fieldName, String regex) {
        if (obj != null && !obj.matches(regex)) {
            throw new DomainException(
                    DomainErrorCode.INVALID_FORMAT,
                    Map.of("field", fieldName),
                    String.format(DomainErrorCode.INVALID_FORMAT.getDefaultMessage(), fieldName)
            );
        }
        return obj;
    }

    public static Instant after(Instant instant1, String fieldName1, Instant instant2, String fieldName2) {
        if (instant1.isBefore(instant2)) {
            throw new DomainException(
                    DomainErrorCode.TIMESTAMP_MUST_BE_AFTER,
                    Map.of("fieldName1", fieldName1, "fieldName2", fieldName2),
                    String.format(DomainErrorCode.TIMESTAMP_MUST_BE_AFTER.getDefaultMessage(), fieldName1, fieldName2)
            );
        }
        return instant1;
    }

    public static Instant before(Instant instant1, String fieldName1, Instant instant2, String fieldName2) {
        if (instant1.isAfter(instant2)) {
            throw new DomainException(
                    DomainErrorCode.TIMESTAMP_MUST_BE_BEFORE,
                    Map.of("fieldName1", fieldName1, "fieldName2", fieldName2),
                    String.format(DomainErrorCode.TIMESTAMP_MUST_BE_BEFORE.getDefaultMessage(), fieldName1, fieldName2)
            );
        }
        return instant1;
    }
}