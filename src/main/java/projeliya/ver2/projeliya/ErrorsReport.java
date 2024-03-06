package projeliya.ver2.projeliya;

import java.util.ArrayList;

import jakarta.validation.OverridesAttribute.List;

public class ErrorsReport 
{
    
    private ArrayList<String> errorsArraysExplain;    
    private Boolean timeRunTest;
    private Boolean MemoryTest;
    private String runTimeOfFunc;
    private Point[]arrayOfPoints;
    private long[]arrayOfSlope;
    private long[]arrayOfDistance;

    public ErrorsReport() {
    }
    
    public ErrorsReport(ArrayList<String> errorsArraysExplain, Boolean timeRunTest, Boolean memoryTest,
            String runTimeOfFunc, Point[] arrayOfPoints, long[] arrayOfSlope, long[] arrayOfDistance) {
        this.errorsArraysExplain = errorsArraysExplain;
        this.timeRunTest = timeRunTest;
        MemoryTest = memoryTest;
        this.runTimeOfFunc = runTimeOfFunc;
        this.arrayOfPoints = arrayOfPoints;
        this.arrayOfSlope = arrayOfSlope;
        this.arrayOfDistance = arrayOfDistance;
    }

    public ErrorsReport(ArrayList<String> errorsArraysExplain, Boolean timeRunTest, Boolean memoryTest,
            String runTimeOfFunc) {
        this.errorsArraysExplain = errorsArraysExplain;
        this.timeRunTest = timeRunTest;
        MemoryTest = memoryTest;
        this.runTimeOfFunc = runTimeOfFunc;
    }
    public ArrayList<String> getErrorsArraysExplain() {
        return errorsArraysExplain;
    }
    public void setErrorsArraysExplain(ArrayList<String> errorsArraysExplain) {
        this.errorsArraysExplain = errorsArraysExplain;
    }
    public Boolean getTimeRunTest() {
        return timeRunTest;
    }
    public void setTimeRunTest(Boolean timeRunTest) {
        this.timeRunTest = timeRunTest;
    }
    public Boolean getMemoryTest() {
        return MemoryTest;
    }
    public void setMemoryTest(Boolean memoryTest) {
        MemoryTest = memoryTest;
    }
    public String getRunTimeOfFunc() {
        return runTimeOfFunc;
    }
    public void setRunTimeOfFunc(String runTimeOfFunc) {
        this.runTimeOfFunc = runTimeOfFunc;
    }

    public Point[] getArrayOfPoints() {
        return arrayOfPoints;
    }

    public void setArrayOfPoints(Point[] arrayOfPoints) {
        this.arrayOfPoints = arrayOfPoints;
    }

    public long[] getArrayOfSlope() {
        return arrayOfSlope;
    }

    public void setArrayOfSlope(long[] arrayOfSlope) {
        this.arrayOfSlope = arrayOfSlope;
    }

    public long[] getArrayOfDistance() {
        return arrayOfDistance;
    }

    public void setArrayOfDistance(long[] arrayOfDistance) {
        this.arrayOfDistance = arrayOfDistance;
    }



}
