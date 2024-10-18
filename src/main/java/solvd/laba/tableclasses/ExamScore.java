package solvd.laba.tableclasses;

public class ExamScore {

    public int examId;
    public int enrollmentId;
    public int score;
    public int retryScore;


    public ExamScore(int examId, int enrollmentId, int score){
        this.examId = examId;
        this.enrollmentId = enrollmentId;
        this.score = score;
    }

    public ExamScore(int examId, int enrollmentId, int score, int retryScore){
        this(examId, enrollmentId, score);
        this.retryScore = retryScore;
    }

    // Getters and setters TBI

}
