package ucll.project.domain.user;

import java.time.LocalDate;

public class DummyUserData {
    public static void addData(UserRepository userRepository) {
        userRepository.createUser(
                new User("admin",
                        "admin", "user",
                        "admin@example.com",
                        Gender.FEMALE, Role.ADMIN, 3, LocalDate.now()
                ),
                "admin" // password
        );
        userRepository.createUser(
                new User("support",
                        "support", "user",
                        "support@example.com",
                        Gender.MALE, Role.SUPER,3,LocalDate.now()
                ),
                "support" // password
        );
        userRepository.createUser(
                new User("user",
                        "simple", "user",
                        "user@example.com",
                        Gender.FEMALE, Role.USER,3,LocalDate.now()
                ),
                "user" // password
        );
        userRepository.createUser(
                new User("user2",
                        "simple2", "user",
                        "user2@example.com",
                        Gender.MALE, Role.USER,3,LocalDate.now()
                ),
                "user2" // password
        );
    }
}
