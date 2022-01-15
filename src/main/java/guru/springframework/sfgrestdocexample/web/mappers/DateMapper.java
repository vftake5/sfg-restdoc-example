package guru.springframework.sfgrestdocexample.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper
{
	public OffsetDateTime asOffsetDateTime(Timestamp timestamp)
	{
		if (timestamp == null)
			return null;
		else
		    return timestamp.toInstant().atOffset(ZoneOffset.UTC);
	}

	public Timestamp asTimestamp(OffsetDateTime offsetDateTime)
	{
		if (offsetDateTime == null)
			return null;
		else
		return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
	}

}
