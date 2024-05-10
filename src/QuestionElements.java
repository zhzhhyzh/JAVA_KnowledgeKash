
public interface QuestionElements {

    public abstract int pointDistribute(int questionIndex, String answer);

    public abstract int answerQuestion(int questionId, String fileName);

    public abstract void createQuestion(String fileName);

    public abstract boolean updateQuestion(int questionId, String fileName);

    public abstract int getTotalCount(String fileName);
}
