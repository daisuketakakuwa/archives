package jp.langstack.domain.genre;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<GenreEntity,String> {
    

    @Query(value = "SELECT genre FROM GenreEntity genre WHERE genre.name = :name")
    public Optional<GenreEntity> findByName(@Param("name") String name);

}
