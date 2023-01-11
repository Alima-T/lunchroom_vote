package com.topjava.lunchroom_vote.util;

import com.topjava.lunchroom_vote.HasId;
import com.topjava.lunchroom_vote.exception.IllegalRequestDataException;
import com.topjava.lunchroom_vote.exception.NotFoundException;
import com.topjava.lunchroom_vote.exception.OutOfTimeException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;

import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Alima-T 12/25/2022
 */
public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void votingTimeVerification(Clock clock) {
        if (LocalTime.now(clock).isAfter(LocalTime.of(11, 0, 0))) {
            throw new OutOfTimeException("Now " + LocalTime.now(clock).format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                    + ", sorry voting time expired. You could vote till 11:00:00");
        }
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }
}