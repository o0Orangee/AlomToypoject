package alom.toyproject.repository;

import alom.toyproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataJpaUserRepository extends JpaRepository<User, Long>, UserRepository {
    @Override
    Optional<User> findByEmail(String email);
}
