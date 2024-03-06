package projeliya.ver2.projeliya;

public class gridClass {
    
    private String efficiencyStepsScore;
    private String memoryScore;
    private String timeScore;
    private String validationFuncsScore;
    private String chatGptScore;
    private String finalScore;
    private String Compilation;
    private int counter = 0;
    public gridClass(int counter ,String Compilation,String timeScore, String efficiencyStepsScore, String memoryScore, String validationFuncsScore, String chatGptScore, String finalScore) 
    {
        this.counter = counter;
        this.timeScore = timeScore;
        this.Compilation = Compilation;
        this.efficiencyStepsScore = efficiencyStepsScore;
        this.memoryScore = memoryScore;
        this.validationFuncsScore = validationFuncsScore;
        this.chatGptScore = chatGptScore;
        this.finalScore = finalScore;
    }

    public int getCounter() {
        return counter;
    }

    public String getTimeScore() {
        return timeScore;
    }

    public String getEfficiencyStepsScore() {
        return efficiencyStepsScore;
    }

    public String getMemoryScore() {
        return memoryScore;
    }

    public String getValidationFuncsScore() {
        return validationFuncsScore;
    }

    public String getChatGptScore() {
        return chatGptScore;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public String getCompilation() {
        return Compilation;
    }

}