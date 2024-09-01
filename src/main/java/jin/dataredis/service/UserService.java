package jin.dataredis.service;

import jin.dataredis.entity.User;
import jin.dataredis.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * value = "users": 결과가 저장될 캐시의 이름입니다.
     * key = "#id": 캐시 키가 id 매개변수여야 함을 지정합니다.
     * 특정 id를 사용하여 메소드가 호출되면 Spring은 결과가 이미 캐시에 있는지 확인합니다.
     * 그렇다면 userRepository를 호출하지 않고 캐시된 결과가 반환됩니다.
     * 그렇지 않은 경우 메서드가 실행되고 결과는 향후 호출을 위해 캐시
     */
    @Cacheable(value = "users", key = "#id") // Cacheable: 메서드 호출의 결과를 캐시하도록 지시
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * value = "users": 캐시의 이름입니다.
     * key = "#user.id": 캐시 키가 User 개체의 id여야 함을 지정합니다.
     * User가 저장되면 캐시는 새로운 또는 업데이트된 User 객체로 업데이트되므로
     * 이 id를 사용하여 getUserById를 후속 호출하면 업데이트된 객체가 반환
     */
    @CachePut(value = "users", key = "#user.id") // CachePut: 메서드 호출 결과로 캐시를 업데이트
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * value = "users": 캐시의 이름입니다.
     * key = "#id": 제거할 캐시 키가 id여야 함을 지정합니다.
     * User가 삭제되면 향후 getUserById 호출에서 오래된 데이터가 반환되지 않도록 캐시의 해당 항목이 제거
     */
    @CacheEvict(value = "users", key = "#id") // CacheEvict: 주어진 id에 해당하는 캐시된 항목을 제거
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}