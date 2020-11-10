package demo.mockito;

import java.util.Map;
import java.util.function.Consumer;

public interface UserRepository {
	User findById(String id);

	void findUsersWithSameRoot(final String root, Consumer<Map<String, String>> consumer);
}
