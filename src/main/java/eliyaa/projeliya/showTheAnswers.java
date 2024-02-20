package eliyaa.projeliya;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("/show")
public class showTheAnswers extends VerticalLayout 
{

    private List<Answer> answerList;
    private QuestionService questionService;

    public showTheAnswers(QuestionService questionService) 
    {
        answerList = new ArrayList<Answer>();
        this.questionService = questionService;

        // Retrieve all questions from the service
        List<Question> questions = questionService.getAllQuestions();
        Long num =(long) 1;
        questionService.addQuestion(new Question(num, null, answerList,null));
        
        // // Display documents
        // for (Question question : questions) {
        //     // Create a text component to represent the question
        //     String questionText = "Question #" + question.getNum() + ": " + question.getQuestion();
        //     add(new H1(questionText));

        //     // Display answers for the question
        //     List<Answer> answers = question.getAnswers();
        //     for (Answer answer : answers) {
        //         // Create a text component to represent the answer
        //         String answerText = "Answer #" + answer.getAnswerNumber() + ": " + answer.getAnswer() + "";
        //         add(answerText);
        //     }
        // }
    }
}

