package demo.mockito;

public class PasswordEncoderImpl implements PasswordEncoder {
	@Override
	public String encode(final String password) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(password);

		return stringBuilder.reverse().toString();
	}
}
