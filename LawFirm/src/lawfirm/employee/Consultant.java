package lawfirm.employee;

import lawfirm.interfaces.ILegalService;
import lawfirm.interfaces.IPayable;
import lawfirm.legal.LegalCase;

/**
 * Консультант (внешний или внутренний): работает почасово.
 * <br>
 * У внешнего консультанта оклад в конторе нулевой, он получает только
 * почасовую плату ({@link #computePayment()}).
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 * @see Employee
 * @see ILegalService
 * @see IPayable
 */
public final class Consultant extends Employee implements ILegalService, IPayable {
    /** Специализация консультанта. */
    private final String specialization;
    /** Ставка за час работы. */
    private final double hourlyRate;
    /** Признак внешнего консультанта. */
    private final boolean external;
    /** Отработанные часы. */
    private double hoursWorked;

    /**
     * Создаёт консультанта. Для внешнего консультанта оклад принудительно равен 0.
     *
     * @param id             идентификатор
     * @param name           имя
     * @param experience     стаж в годах
     * @param baseSalary     оклад (игнорируется для внешнего)
     * @param specialization специализация
     * @param hourlyRate     почасовая ставка (не отрицательная)
     * @param external       {@code true}, если консультант внешний
     * @throws IllegalArgumentException если ставка отрицательная
     *                                  или данные сотрудника некорректны
     */
    public Consultant(int id, String name, int experience, double baseSalary,
                      String specialization, double hourlyRate, boolean external) {
        super(id, name, experience, external ? 0.0 : baseSalary);
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("Ставка не может быть отрицательной");
        }
        this.specialization = specialization;
        this.hourlyRate = hourlyRate;
        this.external = external;
    }

    /**
     * Проверяет, является ли консультант внешним.
     *
     * @return {@code true}, если консультант внешний
     */
    public boolean isExternal() {
        return external;
    }

    /**
     * Возвращает количество отработанных часов.
     *
     * @return отработанные часы
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Добавляет отработанные часы.
     *
     * @param hours количество часов (больше нуля)
     * @throws IllegalArgumentException если часы не положительные
     */
    public void addHours(double hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("Часы должны быть положительными");
        }
        hoursWorked += hours;
    }

    /**
     * Возвращает специализацию консультанта.
     *
     * @return специализация
     */
    @Override
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Подключает консультанта к делу как эксперта и записывает консультацию
     * в журнал. Консультант дело не ведёт и у себя не хранит.
     *
     * @param c юридическое дело
     * @throws IllegalArgumentException если дело равно {@code null}
     */
    @Override
    public void handleCase(LegalCase c) {
        if (c == null) {
            throw new IllegalArgumentException("Дело не может быть null");
        }
        c.addParticipant(this);
        c.addLogEntry("Консультация эксперта: " + getName() + " (" + specialization + ")");
    }

    /**
     * Рассчитывает оплату консультаций: ставка, умноженная на часы.
     *
     * @return сумма оплаты
     */
    @Override
    public double computePayment() {
        return hourlyRate * hoursWorked;
    }

    /**
     * Рассчитывает итоговую выплату: оклад (0 для внешнего) плюс оплата часов.
     *
     * @return итоговая выплата
     * @see #computePayment()
     */
    @Override
    public double calculateSalary() {
        return getBaseSalary() + computePayment();
    }
}
