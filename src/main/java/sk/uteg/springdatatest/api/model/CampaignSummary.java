package sk.uteg.springdatatest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignSummary {
    private long totalFeedbacks;
    private List<QuestionSummary> questionSummaries;
}
