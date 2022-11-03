package com.example.sboot;

import com.example.sboot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class SpringBootDemoIdeaApplication implements ApplicationRunner {

    private final UserRepository userRepository;

    // For "java -jar" to work.
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoIdeaApplication.class, args);
    }

    // Tomcat invokes this method.
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        super.onStartup(servletContext);
//    }

    // Tomcat invokes this method.
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return super.configure(builder);
//    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        userRepository.save(new User("user@gmail.com", "User_First", "User_Last", "pwd", Set.of(Role.ROLE_USER)));
//        userRepository.save(new User("admin@mail.ru", "Admin_First", "Admin_Last", "pwd", Set.of(Role.ROLE_USER, Role.ROLE_ADMIN)));
//        System.out.println(userRepository.findAll());
        System.out.println(userRepository.findByLastNameContainingIgnoreCase("last"));
    }
}
