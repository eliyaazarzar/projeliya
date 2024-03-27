package projeliya.ver2.projeliya;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import com.vaadin.flow.component.template.Id;
@Document(collection = "examinations")
public class Examination 
{
    @Id
    private String id;
    private int arr[][];    
    private List<String>explainTheArrays;
    public String getRunTime() {
        return runTime;
    }
    private int timeEffectiveAction;
    private double MemoryEffectiveAction;
    private String runTime;
    private String runAvergeTime;
    public Examination() {
    }
    
    public Examination(String id, int[][] arr, List<String> explainTheArrays, int timeEffectiveAction,
            double memoryEffectiveAction, String runTime, String runAvergeTime) {
        this.id = id;
        this.arr = arr;
        this.explainTheArrays = explainTheArrays;
        this.timeEffectiveAction = timeEffectiveAction;
        MemoryEffectiveAction = memoryEffectiveAction;
        this.runTime = runTime;
        this.runAvergeTime = runAvergeTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getRunAvergeTime() {
        return runAvergeTime;
    }

    public void setRunAvergeTime(String runAvergeTime) {
        this.runAvergeTime = runAvergeTime;
    }

    public Examination(String id, int[][] arr, List<String> explainTheArrays, int timeEffectiveAction,
            double memoryEffectiveAction, String runTime) {
        this.id = id;
        this.arr = arr;
        this.explainTheArrays = explainTheArrays;
        this.timeEffectiveAction = timeEffectiveAction;
        MemoryEffectiveAction = memoryEffectiveAction;
        this.runTime = runTime;
    }
    public Examination(String id, int[][] arr, List<String> explainTheArrays, int timeEffectiveAction,
            double memoryEffectiveAction) {
        this.id = id;
        this.arr = arr;
        this.explainTheArrays = explainTheArrays;
        this.timeEffectiveAction = timeEffectiveAction;
        MemoryEffectiveAction = memoryEffectiveAction;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int[][] getArr() {
        return arr;
    }
    public void setArr(int[][] arr) {
        this.arr = arr;
    }
    public List<String> getExplainTheArrays() {
        return explainTheArrays;
    }
    public void setExplainTheArrays(List<String> explainTheArrays) {
        this.explainTheArrays = explainTheArrays;
    }
    public int getTimeEffectiveAction() {
        return timeEffectiveAction;
    }
    public void setTimeEffectiveAction(int timeEffectiveAction) {
        this.timeEffectiveAction = timeEffectiveAction;
    }
    public double getMemoryEffectiveAction() {
        return MemoryEffectiveAction;
    }
    public void setMemoryEffectiveAction(double memoryEffectiveAction) {
        MemoryEffectiveAction = memoryEffectiveAction;
    }
    


    



}
