package projeliya.ver2.projeliya;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ExaminationRepo extends MongoRepository<Examination,String>
{
    
}
