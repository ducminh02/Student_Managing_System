package Object;

public class Exam {
    private int maxscore;
    private double actualscore;
    private double germanscore;
    private double passingscore;
    private double percentage;
    private String thema;

    public Exam (int maxscore, double actualscore, double passingscore, String thema) {
        this.maxscore = maxscore;
        this.actualscore = actualscore;
        this.passingscore = passingscore;
        this.thema = thema;
    }

    public double Bavariancalculator (int maxscore, double actualscore, double passingscore) {
        double up = maxscore - actualscore;
        double down = maxscore - passingscore;
        this.germanscore = ((up/down)*3) + 1;
        return this.germanscore;
    }

    public double percentage (int maxscore, double actualscore) {
        this.percentage = (actualscore/maxscore) *100;
        return this.percentage;
    }


}
