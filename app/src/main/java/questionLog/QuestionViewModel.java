package questionLog;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {
    public static QuestionRepo repo;
    public final LiveData<List<Question>> allQuestions;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        repo = new QuestionRepo(application);
        allQuestions = repo.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return allQuestions;
    }

    public static void insert(Question question) {
        repo.insert(question);
    }

    public static void delete(Question question) {
        repo.delete(question);
    }

    public static void deleteAll() {
        repo.deleteAll();
    }
}
