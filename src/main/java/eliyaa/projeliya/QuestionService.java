package eliyaa.projeliya;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
@Service
public class QuestionService 
{

    private final QuestionRepo questionRepository;

    public QuestionService(QuestionRepo questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void addAnswerToQuestion(Answer answer, Long numOfQuestion) {
        Question question = questionRepository.findById(numOfQuestion);
        answer.setAnswerNumber(question.getAnswers().size() + 1); // Assigning answer number
        question.getAnswers().add(answer);
        questionRepository.save(question);
    }

    public void updateAnswerInQuestion(Answer updatedAnswer, Long numOfQuestion, Integer answerNumber) {
        Question question = questionRepository.findByNum(numOfQuestion);
        List<Answer> answers = question.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            if (answer.getAnswerNumber() == answerNumber) 
            {
                answers.set(i, updatedAnswer);
                break;
            }
        }
        questionRepository.save(question);
    }

    public void removeAnswerFromQuestion(Long numOfQuestion, Integer answerNumber) 
    {
        Question question = questionRepository.findByNum(numOfQuestion);
        List<Answer> answers = question.getAnswers();
        answers.removeIf(answer -> answer.getAnswerNumber() == answerNumber);
         question.setAnswers(answers);
        questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    public void addQuestion(Question question) {
        questionRepository.save(question);
    }
}
