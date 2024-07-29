package org.example.intro.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.intro.validation.FieldMatch;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@FieldMatch(field = "password", verifyField = "repeatPassword")
public class UserRegistrationRequestDto {
    @NotBlank(message = "Email field is mandatory")
    @Email(message = "Should be a valid email.")
    @Length(max = 255, message = "Email can not be over 255 characters")
    private String email;

    @NotBlank(message = "Password field is mandatory")
    @Length(
            min = 8,
            max = 35,
            message = "Password length can be from 8 to 35 characters"
    )
    private String password;

    @NotBlank(message = "Repeat password field is mandatory")
    @Length(
            min = 8,
            max = 35,
            message = "Password length can be from 8 to 35 characters"
    )
    private String repeatPassword;

    @NotBlank(message = "First name field is mandatory")
    @Length(
            max = 255,
            message = "First name value can not be over 255 characters"
    )
    private String firstName;

    @NotBlank(message = "Last name field is mandatory")
    @Length(max = 255, message = "Last name value can not be over 255 characters")
    private String lastName;

    @Length(
            max = 255,
            message = "Shipping address name value can not be over 255 characters"
    )
    private String shippingAddress;
}
