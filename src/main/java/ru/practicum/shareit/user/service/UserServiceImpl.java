package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDto get(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id: " + id + " not found"));
        return userMapper.toUserDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<UserDto> getAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Transactional
    @Override
    public UserDto add(UserDto userDto) {
        log.info("User with id: " + userDto.getId() + " created");
        User user = userRepository.save(userMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public UserDto patch(UserDto userDto, Long id) {
        User oldUser = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id: " + userDto.getId() + " not found."));
        User updatedUser = userNameAndEmailUpdate(oldUser, userDto);
        log.info("User with id: " + userDto.getId() + " updated");
        return userMapper.toUserDto(updatedUser);
    }

    @Transactional
    @Override
    public Boolean delete(Long id) {
        userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
        userRepository.deleteById(id);
        log.info("User with id: " + id + " deleted");
        return true;
    }

    private User userNameAndEmailUpdate(User oldUser, UserDto userDto) {
        if (userDto.getEmail() != null) {
            if (!userDto.getEmail().isBlank()) {
                oldUser.setEmail(userDto.getEmail());
            }
        }
        if (userDto.getName() != null) {
            if (!userDto.getName().isBlank()) {
                oldUser.setName(userDto.getName());
            }
        }
        return oldUser;
    }
}
