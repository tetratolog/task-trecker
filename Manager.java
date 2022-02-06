import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class Manager {
    int idCounter = 0;
    HashMap<Integer, Task> idToTasks = new HashMap<>();
    HashMap<Integer, Epic> idToEpics = new HashMap<>();
    HashMap<Integer, Subtask> idToSubtasks = new HashMap<>();

    public void createTask(Task task) {
        idToTasks.put(idCounter, task);
        idCounter += 1;
    }

    public void createEpic(Epic epic) {
        idToEpics.put(idCounter, epic);
        if (!Objects.equals(epic.status, "new")) {
            epic.status = "new";
            System.out.println("пустой эпик и не со статусом 'new'?  ладно, фронт-энд, так и быть, я всё поправила");
        }
        idCounter += 1;
    }

    public void createSubtask(Subtask subtask, int epicID) {
        if (idToEpics.containsKey(epicID)) {
            idToSubtasks.put(idCounter, subtask);
            idToEpics.get(epicID).subtasksIds.add(idCounter);
            subtask.epicId = epicID;
            updateEpicStatus(subtask.epicId);
            idCounter += 1;
        } else {
            System.out.println("Такого эпика не существует, фронт-энд, повнимательнее там");
        }
    }

    @Override
    public String toString() {
        return "Manager{" +
                "idCounter=" + idCounter +
                ", idToTasks=" + idToTasks.toString() +
                ", idToEpics=" + idToEpics.toString() +
                ", idToSubtasks=" + idToSubtasks.toString() +
                '}';
    }

    public Task[] returnTaskList() {
        Collection<Task> tasks = idToTasks.values();
        return tasks.toArray(new Task[tasks.size()]);
    }

    public Epic[] returnEpicList() {
        Collection<Epic> epics = idToEpics.values();
        return epics.toArray(new Epic[epics.size()]);
    }

    public Subtask[] returnSubtaskList() {
        Collection<Subtask> subtasks = idToSubtasks.values();
        return subtasks.toArray(new Subtask[subtasks.size()]);
    }

    public void deleteTasks() {
        idToTasks.clear();
    }

    public void deleteEpics() {
        idToEpics.clear();
        idToSubtasks.clear();
    }

    public void deleteSubtasks() {
        idToSubtasks.clear();
        for (Epic epic : idToEpics.values()) {
            epic.subtasksIds.clear();
        }
        for (int epicId : idToEpics.keySet()) {
            updateEpicStatus(epicId);
        }
    }

    public Task findTaskById(int id) {
        return idToTasks.get(id);
    }

    public Epic findEpicById(int id) {
        return idToEpics.get(id);
    }

    public Subtask findSubtaskById(int id) {
        return idToSubtasks.get(id);
    }

    public void deleteTaskById(int id) {
        idToTasks.remove(id);
    }

    public void deleteEpicById(int id) {
        Epic epic = idToEpics.get(id);
        for (int subtaskId : epic.subtasksIds) {
            idToSubtasks.remove(subtaskId);
        }
        idToEpics.remove(id);
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = idToSubtasks.get(id);
        Epic epic = idToEpics.get(subtask.epicId);
        epic.subtasksIds.remove((Integer) id);
        idToSubtasks.remove(id);
        updateEpicStatus(subtask.epicId);
    }

    public void updateTask(Task task, int id) {
        if (idToTasks.containsKey(id)) {
            idToTasks.put(id, task);
        } else {
            System.out.println("Такой задачи не существует, фронт-энд, повнимательнее там");
        }
    }

    public void updateEpic(Epic epic, int id) {
        if (idToEpics.containsKey(id)) {
            Epic oldEpic = idToEpics.get(id);
            epic.subtasksIds = oldEpic.subtasksIds;
            idToEpics.put(id, epic);
            updateEpicStatus(id);
        } else {
            System.out.println("Такого эпика не существует, фронт-энд, повнимательнее там");
        }
    }

    public void updateSubtask(Subtask subtask, int id) {
        if (idToSubtasks.containsKey(id)) {
            Subtask oldSubtask = idToSubtasks.get(id);
            subtask.epicId = oldSubtask.epicId;
            idToSubtasks.put(id, subtask);
            updateEpicStatus(subtask.epicId);
        } else {
            System.out.println("Такой подзадачи не существует, фронт-энд, повнимательнее там");
        }
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = idToEpics.get(epicId);

        if (epic.subtasksIds.isEmpty()) {
            epic.status = "new";
            return;
        }
        boolean done = true;
        boolean allNew = true;
        for (int subtaskId : epic.subtasksIds) {
            Subtask subtask = idToSubtasks.get(subtaskId);
            switch (subtask.status) {
                case "in progress":
                    epic.status = "in progress";
                    return;
                case "new":
                    done = false;
                    break;
                case "done":
                    allNew = false;
                    break;
            }
        }
        if (done) {
            epic.status = "done";
        } else if (!allNew) {
            epic.status = "in progress";
        } else {
            epic.status = "new";
        }
    }

    public ArrayList<Subtask> returnSubtaskListForEpic(Epic epic) {
        ArrayList<Subtask> subtasksListForEpic = new ArrayList<>();
        for (int subtaskId : epic.subtasksIds) {
            Subtask subtask = idToSubtasks.get(subtaskId);
            subtasksListForEpic.add(subtask);
        }
        return subtasksListForEpic;
    }
}