package entries;

/**
 * This class used to fill leader board tables.
 */
public class LeaderBoardEnrty {
    private int rank;
    private String nickname;
    private int score;

    public LeaderBoardEnrty(){}

    public LeaderBoardEnrty(int rank, String nickname, int score){
        this.rank=rank;
        this.nickname = nickname;
        this.score=score;
    }

    // Setters And Getters

    public void setRank(int rank){
        this.rank = rank;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getRank(){
        return this.rank;
    }

    public String getNickname(){
        return this.nickname;
    }

    public int getScore(){
        return this.score;
    }
}
