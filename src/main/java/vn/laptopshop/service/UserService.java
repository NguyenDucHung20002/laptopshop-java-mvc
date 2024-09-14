package vn.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.laptopshop.domain.Role;
import vn.laptopshop.domain.User;
import vn.laptopshop.repository.RoleRepository;
import vn.laptopshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String handleHello() {
        return "Hello from service";
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }

    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }

    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }
}
