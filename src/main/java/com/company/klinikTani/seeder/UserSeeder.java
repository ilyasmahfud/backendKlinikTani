package com.company.klinikTani.seeder;

import com.company.klinikTani.module.authentication.entity.User;
import com.company.klinikTani.module.authentication.entity.UserDetails;
import com.company.klinikTani.module.authentication.repository.UserRepository;
import com.company.klinikTani.module.authentication.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(value = 3)
@Slf4j
public class UserSeeder implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void run(String... args) {
        try {
            seed();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private void seed() {
        if (userRepository.count() == 0) {
            User userTony = new User();
            userTony.setEmail("tonystark@gmail.com");
            userTony.setPassword("password");

            UserDetails userDetailsTony = new UserDetails();
            userDetailsTony.setFullName("Tony Stark");
            userDetailsTony.setDateOfBirth(LocalDate.of(1970, 5, 29));
            userDetailsTony.setAddress("Jl. Metro Raya, Pondok Indah, Jakarta Selatan, DKI Jakarta");

            userDetailsTony.setMobileNumber("082111111111");

            authenticationService.register(userTony, userDetailsTony);

            User userBruce = new User();
            userBruce.setEmail("brucewayne@gmail.com");
            userBruce.setPassword("password");

            UserDetails userDetailsBruce = new UserDetails();
            userDetailsBruce.setFullName("Bruce Wayne");
            userDetailsBruce.setDateOfBirth(LocalDate.of(1978, 4, 17));
            userDetailsBruce.setAddress("Jl. Syamsu Rizal, Menteng, Jakarta Pusat, DKI Jakarta");

            userDetailsBruce.setMobileNumber("082122222222");

            authenticationService.register(userBruce, userDetailsBruce);
        }
    }
}
