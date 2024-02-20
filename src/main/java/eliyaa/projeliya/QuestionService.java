package eliyaa.projeliya;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService 
{
    private final QuestionRepo questionRepository;
    public QuestionService(QuestionRepo questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void addQuestion(Question question)
    {
         questionRepository.save(question);
    }
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }


}
