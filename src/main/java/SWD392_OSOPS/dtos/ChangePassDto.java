package SWD392_OSOPS.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassDto {
    @NotEmpty(message = "Password can not be blank")
    private String password;
    @NotEmpty(message = "Repeat password can not be blank")
    private String repeatPassword;
}
