package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.Exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;


    /**
     * @param id
     * @return
     */
    @Override
    public UserDto get(long id) throws NotFoundException {
        return userMapper.toUserDto(userRepository.get(id));
    }

    /**
     * @return
     */
    @Override
    public Collection<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    /**
     * @param userDto
     * @return
     */
    @Override
    public UserDto add(UserDto userDto) {
        User newUser = userRepository.save(userMapper.toUser(userDto));
        log.info("Пользователь создан с идентификатором #{}", newUser.getId());
        return userMapper.toUserDto(newUser);
    }

    /**
     * @param userDto
     * @return
     */
    @Override
    public UserDto patch(UserDto userDto, long id) throws NotFoundException {
        User toPatchUser = userRepository.get(id);
        if (userDto.getName() != null && !userDto.getName().isEmpty()) {
            toPatchUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            toPatchUser.setEmail(userDto.getEmail());
        }

        User patchedUser = userRepository.save(toPatchUser);
        log.info("Пользователь #{} обновлен успешно", patchedUser.getId());
        return userMapper.toUserDto(patchedUser);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
        log.info("Пользователь #{} удален успешно", id);
    }
}
