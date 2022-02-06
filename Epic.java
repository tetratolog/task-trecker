import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> subtasksIds = new ArrayList<>();

    public Epic(String title, String description, String status) {
        super(title, description, status);

    }
}
