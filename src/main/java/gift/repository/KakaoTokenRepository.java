package gift.repository;

import gift.entity.MemberKakaoToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoTokenRepository extends JpaRepository<MemberKakaoToken, Integer> {

    MemberKakaoToken findByEmail(String email);
}
