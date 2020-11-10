package demo.mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public boolean isValidUser(String id, String password) {
		User user = userRepository.findById(id);
		return isEnabledUser(user) && isValidPassword(user, password);
	}

	public List<String> listSameRootUsers(String root) {
		final List<String> users = new ArrayList<>();
		Consumer<Map<String, String>> consumer = r -> {
			final Entry<String, String> entry = r.entrySet().iterator().next();
			users.add(entry.getValue());
		};
		userRepository.findUsersWithSameRoot(root, consumer);

		return users;
	}

	private boolean isEnabledUser(User user) {
		return user != null && user.isEnabled();
	}

	private boolean isValidPassword(User user, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		return encodedPassword.equals(user.getPasswordHash());
	}
}
