package com.example.TestTaskBank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationInfo extends Operation{

    @NonNull()
    @Size(max = 4)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String pin;

}
