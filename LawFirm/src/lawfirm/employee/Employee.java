package lawfirm.employee;

/**
 * Абстрактный сотрудник конторы. Хранит общие данные (идентификатор, имя,
 * стаж, оклад) и объявляет абстрактный расчёт зарплаты, который у каждого
 * типа сотрудника свой.
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 * @see Lawyer
 * @see Assistant
 * @see Consultant
 */
public abstract class Employee {
    /** Уникальный идентификатор сотрудника. */
    private final int id;
    /** Имя сотрудника. */
    private final String name;
    /** Стаж работы в годах. */
    private final int experience;
    /** Базовый оклад. */
    private final double baseSalary;

    /**
     * Создаёт сотрудника с проверкой входных данных.
     *
     * @param id         уникальный идентификатор
     * @param name       имя (не пустое)
     * @param experience стаж в годах (не отрицательный)
     * @param baseSalary базовый оклад (не отрицательный)
     * @throws IllegalArgumentException если имя пустое, а стаж или оклад отрицательные
     */
    protected Employee(int id, String name, int experience, double baseSalary) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя сотрудника не может быть пустым");
        }
        if (experience < 0) {
            throw new IllegalArgumentException("Стаж не может быть отрицательным");
        }
        if (baseSalary < 0) {
            throw new IllegalArgumentException("Оклад не может быть отрицательным");
        }
        this.id = id;
        this.name = name;
        this.experience = experience;
        this.baseSalary = baseSalary;
    }

    /**
     * Возвращает идентификатор сотрудника.
     *
     * @return идентификатор
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает имя сотрудника.
     *
     * @return имя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает стаж сотрудника.
     *
     * @return стаж в годах
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Возвращает базовый оклад (без бонусов и почасовых выплат).
     *
     * @return базовый оклад
     */
    public double getBaseSalary() {
        return baseSalary;
    }

    /**
     * Рассчитывает итоговую зарплату. Правило расчёта определяется
     * в каждом классе-наследнике (полиморфизм).
     *
     * @return итоговая зарплата
     */
    public abstract double calculateSalary();

    /**
     * Возвращает краткую информацию о сотруднике: id, имя, должность
     * (имя класса) и стаж.
     *
     * @return строка с информацией о сотруднике
     */
    public String getInfo() {
        return "[" + id + "] " + name + ", " + getClass().getSimpleName()
                + ", стаж: " + experience + " лет";
    }
}
