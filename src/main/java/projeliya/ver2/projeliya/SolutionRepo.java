package projeliya.ver2.projeliya;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepo  extends MongoRepository<Solution, Integer> 
{
}
