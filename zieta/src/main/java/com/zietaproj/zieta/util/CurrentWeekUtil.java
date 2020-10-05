package com.zietaproj.zieta.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

  public class CurrentWeekUtil {

	private final static ZoneId TZ = ZoneId.of("Asia/Kolkata");

	private final Locale locale;
	private final DayOfWeek firstDayOfWeek;
	private final DayOfWeek lastDayOfWeek;

	public CurrentWeekUtil(final Locale locale) {
        this.locale = locale;
        this.firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        this.lastDayOfWeek = DayOfWeek.of(((this.firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
    }

	public Date getFirstDay() {
		
		LocalDate date=  LocalDate.now(TZ).with(TemporalAdjusters.previousOrSame(this.firstDayOfWeek));
		return TSMUtil.getFormattedDate(Date.from(date.atStartOfDay(TZ).toInstant()));
	}

	public Date getLastDay() {
		LocalDate date =  LocalDate.now(TZ).with(TemporalAdjusters.nextOrSame(this.lastDayOfWeek));
		Date endDate = TSMUtil.getFormattedDate(Date.from(date.atStartOfDay(TZ).toInstant()));
		 	Calendar c = Calendar.getInstance(); 
			c.setTime(endDate); 
			c.add(Calendar.DATE, 1);
			endDate = c.getTime();
		return endDate;
	}

	@Override
	public String toString() {
		return String.format("The %s week starts on %s and ends on %s", this.locale.getDisplayName(),
				this.firstDayOfWeek, this.lastDayOfWeek);
	}
	
	
	public static void main(String []args) {
		final CurrentWeekUtil usWeek = new CurrentWeekUtil(new Locale("en","IN"));
		System.out.println(usWeek);
		System.out.println(usWeek.getFirstDay()); 
		System.out.println(usWeek.getLastDay()); 
	}
}