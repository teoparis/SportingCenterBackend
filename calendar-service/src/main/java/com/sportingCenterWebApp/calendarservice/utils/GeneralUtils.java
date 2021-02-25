package com.sportingCenterWebApp.calendarservice.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class GeneralUtils {

	public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final List<String> roles) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	public static String aggiustaStringData(String stringaData) {
		String[] arrayString = stringaData.split("-");
		String stringaDataAggiustata = arrayString[2] + "-" + arrayString[1] + "-" + arrayString[0];
		return stringaDataAggiustata;
	}

	public static Date removeTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
