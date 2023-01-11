package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    /**
     * @param id
     * @return
     */
    @Override
    public UserDto get(Long id) {
        return UserMapper.toUserDto(userStorage.get(id));
    }

    /**
     * @return
     */
    @Override
    public Collection<UserDto> getAll() {
        return userStorage.getAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    /**
     * @param userDto
     * @return
     */
    @Override
    public UserDto add(UserDto userDto) {
        User user = userStorage.add(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    /**
     * @param userDto
     * @return
     */
    @Override
    public UserDto patch(UserDto userDto, Long id) {
        userDto.setId(id);
        return UserMapper.toUserDto(userStorage.patch(UserMapper.toUser(userDto)));
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {
        return userStorage.delete(id);
    }
}
