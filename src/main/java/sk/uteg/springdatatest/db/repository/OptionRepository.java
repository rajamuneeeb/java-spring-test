package sk.uteg.springdatatest.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.uteg.springdatatest.db.model.Option;

import java.util.UUID;

@Repository
public interface OptionRepository extends JpaRepository<Option, UUID> {

}
