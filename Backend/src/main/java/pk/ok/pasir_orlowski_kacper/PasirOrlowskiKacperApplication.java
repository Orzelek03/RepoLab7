package pk.ok.pasir_orlowski_kacper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PasirOrlowskiKacperApplication {

    public static void main(String[] args) {

        System.setProperty("spring.classformat.ignore", "true");

        SpringApplication.run(PasirOrlowskiKacperApplication.class, args);
    }
}