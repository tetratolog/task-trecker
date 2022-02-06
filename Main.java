public class Main {
    public static void main(String[] args) {
        //создание объектов
        Task task1 = new Task ("переезд", "сменить место жительства", "new");
        Task task2 = new Task ("ателье", "подшить штаны", "new");
        Epic epic1 = new Epic("Свадьба", "организовать событие", "new");
        Subtask subtask1 = new Subtask("нанять фотографа","выбрать исполнителя и подписать контракт",
                "new");
        Subtask subtask2 = new Subtask("выбрать платье","съездить в салон Дива дивная", "new");
        int epicId1=2;
        Epic epic2 = new Epic("Ф-но", "научиться играть", "new");
        Subtask subtask3 = new Subtask("найти учителя","выбрать исполнителя и подписать контракт",
                "new");
        int epicId2=5;
        Manager manager = new Manager();
        manager.createTask(task1);
        manager.createTask(task2);
        manager.createEpic(epic1);
        manager.createSubtask(subtask1, epicId1);
        manager.createSubtask(subtask2, epicId1);
        manager.createEpic(epic2);
        manager.createSubtask(subtask3, epicId2);

        //печать статуса
        System.out.println(manager.toString());

        //обновление статуса
        manager.updateTask(new Task ("деклаттеринг","разобрать кухню","new"), 1);
        manager.updateSubtask(new Subtask ("нанять фотографа","выбрать исполнителя и подписать контракт",
                "done"),3);
        manager.updateSubtask(new Subtask ("найти учителя","выбрать исполнителя и подписать контракт",
                "done"),6);

        //печать статуса
        System.out.println(manager.toString());

        //удаление 1 задачи и 1 эпика
        manager.deleteTaskById(0);
        manager.deleteEpicById(2);

        //печать статуса
        System.out.println(manager.toString());
    }
}
