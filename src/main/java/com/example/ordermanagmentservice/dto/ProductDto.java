package com.example.ordermanagmentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id,

        @NotBlank
        String name,

        @Min(value = 0)
        Double price,
        @Size(max = 500)
        String description
) {
}
