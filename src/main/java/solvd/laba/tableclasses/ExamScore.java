package solvd.laba.tableclasses;

public class ExamScore {

    public int examId;
    public int enrollmentId;
    public int score;
    public Integer retryScore;

    public ExamScore(int examId, int enrollmentId, int score, Integer retryScore){
        this.examId = examId;
        this.enrollmentId = enrollmentId;
        this.score = score;
        this.retryScore = retryScore;
    }

    // Getters and setters TBI

}
