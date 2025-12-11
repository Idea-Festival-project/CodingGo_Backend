package Coding_GO.codingGO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // JPA Auditing 활성화 (createdAt, updatedAt 자동 관리)
public class CodingGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodingGoApplication.class, args);
    }
}