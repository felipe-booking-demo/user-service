package io.felipe.bookingdemo.user_service.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DateMapperTest {


    @Test
    @DisplayName("Should convert LocalDate to Date & Date to LocalDate")
    void shouldConvertLocalDateToDate() {
        LocalDate localDate = LocalDate.of(1990, 6, 10);  // YYYY, MM, DD
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertThat(date).isEqualTo(DateMapper.toDate(localDate));
        assertThat(localDate).isEqualTo(DateMapper.toLocalDate(date));
    }

    @Test
    @DisplayName("Should convert null to null")
    void shouldConvertDateToLocalDate() {
        Date nullDate = null;
        LocalDate nullLocalDate = null;
        assertThat(nullDate).isEqualTo(DateMapper.toDate(null));
        assertThat(nullLocalDate).isEqualTo(DateMapper.toLocalDate(null));
    }
}
