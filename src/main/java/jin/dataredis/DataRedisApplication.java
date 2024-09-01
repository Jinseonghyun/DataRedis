package jin.dataredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // 캐쉬 활성화
public class DataRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataRedisApplication.class, args);
    }

}
