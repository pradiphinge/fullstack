package com.mpscstarter.backend.persistence.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;

public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		return (localDateTime == null ? null :Timestamp.valueOf(localDateTime));
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp sqlTimeStamp) {
		return (sqlTimeStamp==null?null :sqlTimeStamp.toLocalDateTime());
	}

}
