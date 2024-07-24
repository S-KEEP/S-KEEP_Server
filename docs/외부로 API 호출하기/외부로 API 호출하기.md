## 외부로 API 호출하기
요청을 보내는 방법은 3가지가 있다.
- RestTemplate
- OpenFeign
- HttpClient

### Feign Client
- Spring Cloud 프레임워크의 프로젝트 중 하나이다.
- Spring MVC에서 제공되는 어노테이션을 그대로 사용할 수 있다.
- @FeignClient를 통해 인터페이스를 클래스로 구체화 시킬 필요가 없다. (어노테이션이 자동으로 작성해준다.)

1️⃣ 의존성을 추가한다.
```gradle
implementation 'org.springframework.cloud:spring-cloud-starter-openfeign'
```

2️⃣ @EnableFeignClients 어노테이션을 추가한다. => FeignClient 어노테이션이 명시된 인터페이스를 찾아 자동으로 구현체를 구현해준다.
```java
@EnableFeignClients
@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
```

3️⃣ Feign Client 인터페이스를 생성한다.
```java
package com.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="feign", url="http://localhost:8080") // (name ="feign client 이름 설정" , url="호출할 api url", configuration = "feignclient 설정정보가 셋팅되어 있는 클래스")
public interface TestClient {
    @GetMapping("/testfeign")
    String testFeign();
}
```

4️⃣ Feign Client를 사용하는 서비스단 로직을 작성한다.
```java
package com.service;

import com.client.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired TestClient testClient;

    public String testFeign() {
        return testClient.testFeign();
    }
}
```

### 참고 블로그
https://velog.io/@choidongkuen/FeignClient-%EC%82%AC%EC%9A%A9%EB%B2%95%EC%97%90-%EB%8C%80%ED%95%B4-%EC%95%8C%EC%95%84%EB%B4%85%EC%8B%9C%EB%8B%A4.-Spring-Cloud-OpenFeign
https://velog.io/@skyepodium/2019-10-06-1410-%EC%9E%91%EC%84%B1%EB%90%A8