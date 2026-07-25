package com.paranoiax.users.domain;

import com.paranoiax.users.domain.exceptions.*;

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
            throw new InvalidLengthException(fieldName, minLength, maxLength);
        }
        return obj;
    }

    public static String hasLengthIfPresent(String obj, String fieldName, Integer minLength, Integer maxLength) {
        if (obj != null && (obj.length() < minLength || obj.length() > maxLength)) {
            throw new InvalidLengthException(fieldName, minLength, maxLength);
        }
        return obj;
    }

    public static String matches(String obj, String fieldName, String regex) {
        if (obj == null || !obj.matches(regex)) {
            throw new InvalidFormatException(fieldName);
        }
        return obj;
    }

    public static String matchesIfPresent(String obj, String fieldName, String regex) {
        if (obj != null && !obj.matches(regex)) {
            throw new InvalidFormatException(fieldName);
        }
        return obj;
    }

    public static Instant after(Instant instant1, String fieldName1, Instant instant2, String fieldName2) {
        if (instant1.isBefore(instant2)) {
            throw new InvalidTimestampException(fieldName1, DomainErrorCode.TIMESTAMP_MUST_BE_AFTER, fieldName2);
        }
        return instant1;
    }

    public static Instant before(Instant instant1, String fieldName1, Instant instant2, String fieldName2) {
        if (instant1.isAfter(instant2)) {
            throw new InvalidTimestampException(fieldName1, DomainErrorCode.TIMESTAMP_MUST_BE_BEFORE, fieldName2);
        }
        return instant1;
    }
}