package com.oficinaapp.oficina_app.application.dto.dashboard;

import java.time.LocalDate;

/**
 * @param date  the day itself; the app turns it into the letter it shows
 * @param count orders completed on that day
 */
public record DailyServiceCountResponse(LocalDate date, long count) {
}
