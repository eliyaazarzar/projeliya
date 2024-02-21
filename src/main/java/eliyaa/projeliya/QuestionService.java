package eliyaa.projeliya;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.stereotype.Service;

@Service

public class QuestionService 
{
    private UserService userService;
    private List<Answer> currentAnswers;
    private final QuestionRepo questionRepository;
    public QuestionService(QuestionRepo questionRepository, UserService userService) 
    {
        this.userService = userService;
        this.questionRepository = questionRepository;
    }
    public Question findById(Long id)
    {
        Optional<Question> questionsList = questionRepository.findById(id);
        Question question =  questionsList.isPresent() ? questionsList.get() : null;
        return question;
    }
    public Question saveTheList(List<Answer> answerList,Long id)
    {
                Optional<Question> questionsList = questionRepository.findById(id);
                Question question =  questionsList.isPresent() ? questionsList.get() : null;
            if(question == null)
            {
                System.out.println("this is null");
                return null;
            }
            question.setAnswers(answerList);
            question =questionRepository.save(question);
            System.out.println(question.getAnswers()); 
            return question;
    }
    public void addQuestion(Question question)
    {
         questionRepository.insert(question);
    }
    
    public List<Question> addToList(Answer answer, long id) 
    {
        currentAnswers = new ArrayList<>();
        // Assuming questionService and currentAnswers are accessible
        System.out.println(id);
        Optional<Question> questionsList = questionRepository.findById(id);
        Question question =  questionsList.isPresent() ? questionsList.get() : null;      
          if (question == null) 
          {
            System.out.println("null");
          }
        System.out.println(answer.getNameOfWriter());
        User user = userService.getUserByID(answer.getNameOfWriter());
        if (user != null && question !=  null) 
        {
            currentAnswers = question.getAnswers();
            answer.setAnswerNumber(currentAnswers.size());
            currentAnswers.add(answer);
            user.setAnswerList(currentAnswers);
            userService.saveUser(user);
            question.setAnswers(currentAnswers);
            question =questionRepository.save(question);
        }
        return questionRepository.findAll();
    }
    public List<Question> getAllQuestions() 
    {
        return questionRepository.findAll();
    }



}
