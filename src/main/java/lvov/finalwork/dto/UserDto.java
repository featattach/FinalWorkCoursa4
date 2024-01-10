package lvov.finalwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty(message = "Имя не должно быть пустым")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

    private List<String> roles;  // Добавлено свойство для хранения списка ролей
}