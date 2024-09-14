package vn.laptopshop.controller.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.laptopshop.domain.Role;
import vn.laptopshop.domain.User;
import vn.laptopshop.service.UploadService;
import vn.laptopshop.service.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	private final UserService userService;
	private final UploadService uploadService;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.uploadService = uploadService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/admin/user")
	public String getUserPage(Model model) {
		List<User> users = this.userService.getAllUsers();
		model.addAttribute("users", users);
		return "admin/user/show";
	}

	@GetMapping("/admin/user/{id}")
	public String getUserDetailPage(Model model, @PathVariable Long id) {
		User user = this.userService.getUserById(id);
		model.addAttribute("id", id);
		model.addAttribute("user", user);
		return "/admin/user/detail";
	}

	@GetMapping("/admin/user/create") // GET
	public String getCreateUserPage(Model model) {
		model.addAttribute("newUser", new User());
		List<Role> roles = this.userService.getAllRoles();
		model.addAttribute("roles", roles);
		return "admin/user/create";
	}

	@PostMapping("/admin/user/create")
	public String createUserPage(Model model,
			@ModelAttribute("newUser") User user,
			@RequestParam("File") MultipartFile file) {
		String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
		String hashPassword = this.passwordEncoder.encode(user.getPassword());
		user.setAvatar(avatar);
		user.setPassword(hashPassword);
		this.userService.handleSaveUser(user);
		return "redirect:/admin/user";
	}

	@GetMapping("/admin/user/update/{id}") // GET
	public String getUpdateUserPage(Model model, @PathVariable Long id) {
		User user = this.userService.getUserById(id);
		List<Role> roles = this.userService.getAllRoles();
		model.addAttribute("roles", roles);
		model.addAttribute("user", user);
		return "admin/user/update";
	}

	@PostMapping("/admin/user/update")
	public String updateUserPage(Model model, @ModelAttribute("user") User user,
			@RequestParam("File") MultipartFile file) {
		User currentUser = this.userService.getUserById(user.getId());
		String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
		if (currentUser != null) {
			currentUser.setFullName(user.getFullName());
			currentUser.setEmail(user.getEmail());
			currentUser.setAddress(user.getAddress());
			currentUser.setAvatar(avatar);
			currentUser.setRole(user.getRole());
			System.out.println(currentUser);
			this.userService.handleSaveUser(currentUser);
		}
		return "redirect:/admin/user";

	}

	@RequestMapping(value = "/admin/user/delete/{id}") // GET
	public String getDeleteUserPage(Model model, @PathVariable Long id) {
		model.addAttribute("id", id);
		model.addAttribute("user", new User());
		return "admin/user/delete";
	}

	@PostMapping("/admin/user/delete") // GET
	public String deleteUserPage(Model model, @ModelAttribute("user") User user) {
		if (user != null) {
			this.userService.deleteAUser(user.getId());
		}
		return "redirect:/admin/user";
	}

}
