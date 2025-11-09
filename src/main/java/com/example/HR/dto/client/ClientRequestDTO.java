package com.example.HR.dto.client;

import com.example.HR.enums.JobTitle;
import com.example.HR.validation.Create;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.parser.Upgrade;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRequestDTO {

    @NotNull(groups = Upgrade.class, message = "Id is required for update")
    private Long id;

    @NotNull(groups = Create.class)
    private String fullname;

    @Schema(description = "Email account of the employee", required = true, example = "johndoe@example.com")
    @NotNull(groups = Create.class,message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(groups = Create.class,message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull(groups = Create.class,message = "Email is required")
    private String companyName;

    @Schema(description = "Job title of the employee", required = true, example = "DEVELOPER", allowableValues = {"DEVELOPER", "CEO", "UX_DESIGNER", "PROJECT_MANAGER", "PRODUCTS_DESIGNER", "ILLUSTRATOR", "UI_DESIGNER"})
    @NotNull(groups = Create.class,message = "Job title is required")
    private JobTitle jobTitle;

}
