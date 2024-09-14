package vn.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import vn.laptopshop.domain.Role;
import vn.laptopshop.repository.RoleRepository;

@Service
public class RoleService {
  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public List<Role> getAllRoles() {
    return this.roleRepository.findAll();
  }
}
