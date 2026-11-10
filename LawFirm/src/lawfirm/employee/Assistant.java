package lawfirm.employee;

import lawfirm.interfaces.IReportable;

import java.util.Arrays;

/**
 * Помощник адвоката: выполняет вспомогательные задачи и получает
 * фиксированный оклад.
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 * @see Employee
 * @see IReportable
 * @see Lawyer
 */
public final class Assistant extends Employee implements IReportable {
    /** Начальная ёмкость массива задач. */
    private static final int INITIAL_CAPACITY = 4;

    /** Адвокат-руководитель. */
    private final Lawyer supervisor;
    /** Выполненные задачи. */
    private String[] tasks = new String[INITIAL_CAPACITY];
    /** Количество задач. */
    private int tasksCount = 0;

    /**
     * Создаёт помощника.
     *
     * @param id         идентификатор
     * @param name       имя
     * @param experience стаж в годах
     * @param baseSalary фиксированный оклад
     * @param supervisor адвокат-руководитель
     * @throws IllegalArgumentException если руководитель равен {@code null}
     *                                  или данные сотрудника некорректны
     */
    public Assistant(int id, String name, int experience, double baseSalary,
                     Lawyer supervisor) {
        super(id, name, experience, baseSalary);
        if (supervisor == null) {
            throw new IllegalArgumentException("У помощника должен быть адвокат-руководитель");
        }
        this.supervisor = supervisor;
    }

    /**
     * Возвращает адвоката-руководителя.
     *
     * @return руководитель помощника
     */
    public Lawyer getSupervisor() {
        return supervisor;
    }

    /**
     * Добавляет выполненную задачу.
     *
     * @param task описание задачи
     */
    public void addTask(String task) {
        if (tasksCount == tasks.length) {
            tasks = Arrays.copyOf(tasks, tasks.length * 2);
        }
        tasks[tasksCount++] = task;
    }

    /**
     * Возвращает зарплату помощника, равную фиксированному окладу.
     *
     * @return оклад
     */
    @Override
    public double calculateSalary() {
        return getBaseSalary();
    }

    /**
     * Формирует отчёт по выполненным задачам.
     *
     * @return текст отчёта
     */
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Отчёт помощника ").append(getName())
          .append(" (руководитель: ").append(supervisor.getName()).append(")\n");
        sb.append("Задач выполнено: ").append(tasksCount).append("\n");
        for (int i = 0; i < tasksCount; i++) {
            sb.append("  - ").append(tasks[i]).append("\n");
        }
        return sb.toString();
    }
}
