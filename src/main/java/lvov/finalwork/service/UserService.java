package lvov.finalwork.service;

import lvov.finalwork.dto.UserDto;
import lvov.finalwork.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByUsername(String username);
    List<UserDto> findAllUsers();
    void addRoleToUser(String username, String roleName);
}