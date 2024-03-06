package projeliya.ver2.projeliya;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SolutionService 
{
    
    private SolutionRepo SolutionRepo;

    private ArrayList<SoultionsChangeListner> listeners; 
    
    public interface SoultionsChangeListner 
    {
       public void onChange();
    }   
    public SolutionService(SolutionRepo SolutionRepo) {
        listeners = new ArrayList<>();
        this.SolutionRepo = SolutionRepo;
    }

    public List<Solution> getAllSolutions()
    {
        return SolutionRepo.findAll();
    }
    
    public void addSoulitonChangeListener(SoultionsChangeListner listener)
    {
       synchronized (listeners)
       {
          listeners.add(listener);
       }
    }
    public void addSolution(Solution solution) 
    {
        List<Solution> listOfSolutions =  getAllSolutions();
        int num;
        if(listOfSolutions.size()==-1)
        {
            num = 0;
        }else{
         num = listOfSolutions.get(listOfSolutions.size()-1).getId()+1;
        }
        solution.setId(num);

        SolutionRepo.insert(solution);
          synchronized(listeners)
          {
            for (SoultionsChangeListner listener : listeners)
            {
                 listener.onChange();
            }
        }
    }
    public void deleteSolution(int id) {
        
    Optional<Solution> solution = SolutionRepo.findById(id);
        if(solution != null)
        {
        SolutionRepo.delete(solution.orElse(null));
        }
    }
    public  List<Solution>  showTheAnswersForUser(String name) 
    {
        List<Solution> listOfSolutions = getAllSolutions();
        List<Solution>listOfSolutionsUser = new ArrayList();
        for(int i=0; i<listOfSolutions.size(); i++) 
        {
            if(listOfSolutions.get(i).getNameOfWriter().equals(name)) 
            {
                listOfSolutionsUser.add(listOfSolutions.get(i));
            }
        } 
        return listOfSolutionsUser;
    }




}
