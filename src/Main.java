import java.util.Map;

/**
 * Created by ilion on 02.02.2015.
 */
public class Main {
	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
			System.out.format("%s=%s%n",
					envName,
					env.get(envName));
		}
	}
}
