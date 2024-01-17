package sk.uteg.springdatatest.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.uteg.springdatatest.api.model.CampaignSummary;
import sk.uteg.springdatatest.service.CampaignService;

import java.util.UUID;

@RestController
@RequestMapping("/campaign")
@AllArgsConstructor
@Slf4j
public class CampaignController {
    private final CampaignService campaignSummary;

    @GetMapping("/summary/{uuid}")
    public ResponseEntity<CampaignSummary> getSummary(@PathVariable UUID uuid) {
        log.info("UUID {} " , uuid);
        return campaignSummary.getCampaignSummary((uuid));
    }
}
