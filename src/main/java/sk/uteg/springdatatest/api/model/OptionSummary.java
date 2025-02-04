package sk.uteg.springdatatest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionSummary {
    private String text;
    private int occurrences;
}
