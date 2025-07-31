package gift.repository;

import gift.entity.Wish;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    Page<Wish> findAllByMemberId(Long memberId, Pageable pageable);

    List<Wish> findAllByMemberId(Long memberId);
}
