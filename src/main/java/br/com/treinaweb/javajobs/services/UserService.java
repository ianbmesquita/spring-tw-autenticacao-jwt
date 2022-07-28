package br.com.treinaweb.javajobs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.treinaweb.javajobs.dto.UserDTO;
import br.com.treinaweb.javajobs.mappers.UserMapper;
import br.com.treinaweb.javajobs.models.User;
import br.com.treinaweb.javajobs.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public User create(UserDTO userDTO) {
        return repository.save(mapper.toModel(userDTO));
    }


}
