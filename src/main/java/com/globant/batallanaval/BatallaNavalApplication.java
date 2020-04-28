package com.globant.batallanaval;

import com.globant.batallanaval.harness.JuegoHarness;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatallaNavalApplication {

    public static void main(String[] args) {

        SpringApplication.run(BatallaNavalApplication.class, args);
        JuegoHarness client = new JuegoHarness();
        client.getResult();
    }

}
