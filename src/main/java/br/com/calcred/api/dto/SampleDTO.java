package br.com.calcred.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Past;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

@Value
@With
@JsonDeserialize(builder = SampleDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class SampleDTO {

    Long id;

    @Size(max = 64)
    @NotEmpty
    String fullName;
    @Past
    LocalDate birthday;
    @Email
    String email;

    LocalDateTime created;
    LocalDateTime lastUpdated;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {}
}
