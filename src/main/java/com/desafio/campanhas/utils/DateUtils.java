package com.desafio.campanhas.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.desafio.campanhas.service.exception.DataParseException;

public class DateUtils {

	private DateUtils() {
	}

	public static LocalDate dateToLocalDate(Date date) {
		return date.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
	}
	
	public static LocalDate parseDate(String data) {
		String pattern = "dd/MM/yyyy";
		if (StringUtils.isNotBlank(data)) {
			try {
				return LocalDate.parse(data, DateTimeFormatter.ofPattern(pattern));
			} catch (DateTimeParseException e) {
				throw new DataParseException("Data incorreta: [" + data + "], formato requerido: " + pattern);
			}
		}
		return null;
	}
	
}
