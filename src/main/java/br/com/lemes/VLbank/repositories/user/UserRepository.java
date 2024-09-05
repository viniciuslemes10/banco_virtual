package br.com.lemes.VLbank.repositories.user;

import br.com.lemes.VLbank.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
