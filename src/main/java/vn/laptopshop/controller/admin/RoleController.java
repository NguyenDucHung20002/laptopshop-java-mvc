package vn.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;

import vn.laptopshop.service.RoleService;

@Controller
public class RoleController {

  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

}
