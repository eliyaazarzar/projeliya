package eliyaa.projeliya;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Route("/show")
public class showTheAnswers extends VerticalLayout {
    private final QuestionService questionService;
    private final Grid<Answer> answersGrid;

    public showTheAnswers(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.answersGrid = new Grid<>(Answer.class);

        // Set up grid configuration
        answersGrid.setColumns("nameOfWriter", "numQuestion", "answer", "grade");
        answersGrid.asSingleSelect().addValueChangeListener(event -> {
            showAnswerDetails(event.getValue());
        });
        
        add(new H1("All the Answers of the Question!"), answersGrid);
        refreshQuestions();
    }
    private void refreshQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        Map<String, List<Answer>> groupedAnswers = new LinkedHashMap<>();
        int answerCounter = 0; // Initialize answerCounter
    
        // Group answers by question topics
        for (Question question : questions) {
            String topic = question.getQuestion();
            List<Answer> answers = question.getAnswers();
            System.out.println(answers.size());
            // Set question number for each answer
            Long num = question.getId(); // Get the question's ID
            int numQuestion = num.intValue(); // Convert the Long ID to int
            for (Answer answer : answers) {
                answer.setAnswerNumber(answerCounter);  // Set answer number for each answer
                answer.setNumQuestion(numQuestion); // Set numQuestion to the question's number
                answerCounter++;
            }
            groupedAnswers.put(topic, answers);
        }
    
        // Prepare data for grid
        List<Answer> allAnswers = new ArrayList<>();
        for (List<Answer> answers : groupedAnswers.values()) {
            allAnswers.addAll(answers);
        }
        // Set the data provider for the grid
        answersGrid.setItems(allAnswers);
    }
    
    
    
    
    private void showAnswerDetails(Answer answer) {
        Dialog dialog = new Dialog();
        dialog.add(new H1("Answer Details"));
        dialog.add(new H1("Name of Writer: " + answer.getNameOfWriter()));
        dialog.add(new H1("Question Number: " + answer.getNumQuestion()));
        dialog.add(new H1("Answer Number: " + answer.getAnswerNumber())); // Display answerNumber instead of numQuestion
        dialog.add(new H1("Answer: " + answer.getAnswer()));
        dialog.add(new H1("Grade: " + answer.getGrade()));

        Button closeButton = new Button("Close", event -> dialog.close());
        dialog.add(closeButton);
        dialog.open();
    }
}
