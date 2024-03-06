package projeliya.ver2.projeliya;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepo extends MongoRepository<Question,String> 
{
}
