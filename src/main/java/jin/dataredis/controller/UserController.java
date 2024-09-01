package jin.dataredis.controller;

import jin.dataredis.entity.User;
import jin.dataredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * RestController: 이 주석은 @Controller와 @ResponseBody의 조합입니다.
 * 이는 이 클래스가 모든 메서드의 반환 값이 자동으로 JSON으로 직렬화되어 HTTP 응답 본문으로 전송되는 Spring MVC 컨트롤러임을 나타냅니다.
 */
@RestController
@RequestMapping("/users") // 모든 요청 매핑에 /users 접두사가 붙도록 지정, 예를 들어 /users/{id}는 getUserById 메서드에 매핑
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @GetMapping("/{id}"): /users/{id}에 대한 HTTP GET 요청을 이 메서드에 매핑합니다. {id} 부분은 사용자의 ID를 나타내는 경로 변수입니다.
     * @PathVariable Long id: URL의 {id}를 메소드 매개변수 id에 바인딩합니다.
     * ResponseEntity: 상태 코드 및 본문을 포함하여 HTTP 응답을 나타냅니다.
     * 이 경우, 발견되면 User 객체를 반환하고, 사용자가 존재하지 않으면 404 Not Found 응답을 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    /**
     * @PostMapping: /users에 대한 HTTP POST 요청을 이 메서드에 매핑합니다. 이는 일반적으로 새 리소스를 생성하는 데 사용됩니다.
     * @RequestBody 사용자 user: 요청 본문의 JSON 페이로드를 User 객체에 바인딩합니다.
     * ResponseEntity: 본문에 저장된 User 개체와 함께 201 Created 응답을 반환
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * @DeleteMapping("/{id}"): /users/{id}에 대한 HTTP DELETE 요청을 이 메서드에 매핑합니다.
     * @PathVariable Long id: URL의 {id}를 메소드 매개변수 id에 바인딩합니다.
     * ResponseEntity: 삭제가 성공했음을 나타내는 '204 No Content' 응답을 반환하며 응답 본문에 콘텐츠가 반환되지 않습니다.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
