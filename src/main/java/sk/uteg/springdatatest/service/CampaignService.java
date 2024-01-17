package sk.uteg.springdatatest.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sk.uteg.springdatatest.api.model.CampaignSummary;
import sk.uteg.springdatatest.api.model.OptionSummary;
import sk.uteg.springdatatest.api.model.QuestionSummary;
import sk.uteg.springdatatest.db.model.*;
import sk.uteg.springdatatest.db.repository.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CampaignService {

    private final FeedbakRepository feedbackRepository;


    private final QuestionRepository questionRepository;

    private final CampaignRepository campaignRepository;

    public ResponseEntity<CampaignSummary> getCampaignSummary(UUID campaignId) {
        Campaign campaign = getCampaignById(campaignId);
        if (campaign == null) {
            throw new RuntimeException("Campaign Not Found");

        }

        List<Feedback> feedbacks = feedbackRepository.findByCampaign(campaign);
        int totalFeedbacks = feedbacks.size();

        List<QuestionSummary> questionSummaries = new ArrayList<>();
        List<Question> questions = questionRepository.findByCampaign(campaign);

        for (Question question : questions) {
            QuestionSummary questionSummary = new QuestionSummary();
            questionSummary.setName(question.getText());
            questionSummary.setType(question.getType());

            if (question.getType().equals(QuestionType.RATING)) {
                double averageRating = calculateAverageRating(feedbacks, question);
                questionSummary.setRatingAverage(BigDecimal.valueOf(averageRating));
            } else if (question.getType().equals(QuestionType.CHOICE)) {
                List<OptionSummary> optionSummaries = calculateOptionOccurrences(feedbacks, question);
                questionSummary.setOptionSummaries(optionSummaries);
            }

            questionSummaries.add(questionSummary);
        }

        return new ResponseEntity<>(new CampaignSummary(totalFeedbacks, questionSummaries), HttpStatus.OK);

    }

    private double calculateAverageRating(List<Feedback> feedbacks, Question question) {
        List<Integer> ratings = feedbacks.stream().map(feedback -> getRatingForQuestion(feedback, question)).filter(Objects::nonNull).collect(Collectors.toList());

        return ratings.isEmpty() ? 0.0 : ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    private List<OptionSummary> calculateOptionOccurrences(List<Feedback> feedbacks, Question question) {
        List<List<Option>> selectedOptions = feedbacks.stream().map(feedback -> getSelectedOptionsForQuestion(feedback, question)).filter(Objects::nonNull).collect(Collectors.toList());

        Map<String, Long> optionOccurrences = selectedOptions.stream().flatMap(List::stream).map(Option::getText)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<OptionSummary> optionSummaries = new ArrayList<>();
        question.getOptions().forEach(option -> {
            long occurrences = optionOccurrences.getOrDefault(option, 0L);
            optionSummaries.add(new OptionSummary(option.getText(), (int) occurrences));
        });

        return optionSummaries;
    }

    private Integer getRatingForQuestion(Feedback feedback, Question question) {
        return feedback.getAnswers().stream().filter(answer -> answer.getQuestion().equals(question)).map(Answer::getRatingValue).findFirst().orElse(null);
    }

    private List<Option> getSelectedOptionsForQuestion(Feedback feedback, Question question) {
        return feedback.getAnswers().stream().filter(answer -> answer.getQuestion().equals(question)).map(Answer::getSelectedOptions).findFirst().orElse(null);
    }

    private Campaign getCampaignById(UUID campaignId) {
        return campaignRepository.findById(campaignId).orElse(null);
    }
}

