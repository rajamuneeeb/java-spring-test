package sk.uteg.springdatatest.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.uteg.springdatatest.db.model.Campaign;
import sk.uteg.springdatatest.db.model.Feedback;

import java.util.UUID;
import java.util.List;

@Repository
public interface FeedbakRepository extends JpaRepository<Feedback, UUID> {
    List<Feedback> findByCampaign(Campaign campaign);
}
