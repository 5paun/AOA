package com.example.analyzerofanalyses.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for login")
public class JwtRequest {

    @Schema(description = "email", example = "Shalashov-D-S@tut.by")
    @NotNull(message = "Username must be not null.")
    private String username;

    @Schema(description = "password", example = "$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m ")
    @NotNull(message = "Password must be not null.")
    private String password;
}

