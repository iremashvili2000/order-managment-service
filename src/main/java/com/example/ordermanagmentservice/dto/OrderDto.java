package com.example.ordermanagmentservice.dto;

import com.example.ordermanagmentservice.model.Product;
import com.example.ordermanagmentservice.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;



public record OrderDto(


        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id,

         Long userId,

         Integer quantity,

         Double price,

         @JsonProperty(access = JsonProperty.Access.READ_ONLY) Status status,



         @JsonProperty(access = JsonProperty.Access.READ_ONLY) String created,


         String product
) {
}
