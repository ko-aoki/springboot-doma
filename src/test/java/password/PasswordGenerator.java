package password;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by ko-aoki on 2017/03/11.
 */
@RunWith(JUnit4.class)
public class PasswordGenerator {

    @Test
    public void generatePassword() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("test"));
    }
}
