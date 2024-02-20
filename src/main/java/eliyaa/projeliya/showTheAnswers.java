package eliyaa.projeliya;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Route("/show")
public class showTheAnswers extends VerticalLayout {
    private final QuestionService questionService;
    private List<Answer> currentAnswers;

    public showTheAnswers(QuestionService questionService) 
    {
        this.questionService = questionService;
        this.currentAnswers = new ArrayList<>();

        // Create form components for adding a new question
        TextField questionField = new TextField("Question");
        TextField answerField = new TextField("Answer");
        Button addAnswerButton = new Button("Add Answer", event -> {
            String answerContent = answerField.getValue();
            if (!answerContent.isEmpty()) {
                currentAnswers.add(new Answer("eliya", 1, answerContent, 10.0, new Date(), currentAnswers.size() + 1));
                System.out.println("add Answer!");
                answerField.clear(); // Clear the answer field after adding
            }
        });
        Button addQuestionButton = new Button("Add Question", event -> {
            String questionContent = questionField.getValue();
            if (!questionContent.isEmpty() && !currentAnswers.isEmpty()) {
                // Create new question with the provided content and answers
                Question newQuestion = new Question();
                newQuestion.setQuestion(questionContent);
                newQuestion.setAnswers(currentAnswers);
                questionService.addQuestion(newQuestion);
                System.out.println("Add question!");

                currentAnswers.clear(); // Clear the list of current answers
                refreshQuestions(); // Refresh the displayed questions after adding a new one
                questionField.clear(); // Clear the question field after adding
            }
        });

        // Add form components to the layout
        add(questionField, answerField, addAnswerButton, addQuestionButton);

        // Display existing questions and their answers
        int counter = 0;
        counter++;
        System.out.println(" counter = " + counter);
        if(counter == 2)
        {

        }
    }

    private void refreshQuestions() {
        removeAll(); // Clear existing components before displaying updated questions
        List<Question> questions = questionService.getAllQuestions();
        for (Question question : questions) {
            H1 questionHeader = new H1(question.getQuestion());
            add(questionHeader);

            // Add answers for the question
            List<Answer> answers = question.getAnswers();
            for (Answer answer : answers) {
                add(new H1(answer.getAnswer())); // Assuming getContent() returns the answer content
            }
        }
    }
}
