package eliyaa.projeliya;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface QuestionRepo extends MongoRepository<Question,Object> 
{

}
