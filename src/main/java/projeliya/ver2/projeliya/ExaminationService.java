package projeliya.ver2.projeliya;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class ExaminationService {

    private ExaminationRepo examinationRepo;

    public ExaminationService(ExaminationRepo examinationRepo) {
        this.examinationRepo = examinationRepo;
    }

    public Examination getById(String id) {
            Optional<Examination> examinationOptional = examinationRepo.findById(id);
            Examination examination =  examinationOptional.isPresent() ? examinationOptional.get() : null;
       return examination;
    }
}
