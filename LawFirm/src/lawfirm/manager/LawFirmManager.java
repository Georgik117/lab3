package lawfirm.manager;

import lawfirm.employee.Employee;
import lawfirm.employee.Lawyer;
import lawfirm.interfaces.ILegalService;
import lawfirm.interfaces.IReportable;
import lawfirm.legal.LegalCase;

import java.util.Arrays;

/**
 * Управляющий модуль конторы: хранит сотрудников, распределяет дела,
 * считает выплаты и собирает общий отчёт.
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 * @see Employee
 * @see LegalCase
 */
public final class LawFirmManager {
    /** Начальная ёмкость массива сотрудников. */
    private static final int INITIAL_CAPACITY = 8;

    /** Сотрудники конторы. */
    private Employee[] employees = new Employee[INITIAL_CAPACITY];
    /** Количество сотрудников. */
    private int count = 0;

    /**
     * Создаёт управляющий модуль конторы без сотрудников.
     */
    public LawFirmManager() {
    }

    /**
     * Добавляет сотрудника в контору.
     *
     * @param e сотрудник
     * @throws IllegalArgumentException если сотрудник равен {@code null}
     *                                  или сотрудник с таким id уже есть
     */
    public void add(Employee e) {
        if (e == null) {
            throw new IllegalArgumentException("Сотрудник не может быть null");
        }
        if (indexOf(e.getId()) != -1) {
            throw new IllegalArgumentException("Сотрудник с id " + e.getId() + " уже есть");
        }
        if (count == employees.length) {
            employees = Arrays.copyOf(employees, employees.length * 2);
        }
        employees[count++] = e;
    }

    /**
     * Удаляет сотрудника из конторы. Если его нет, ничего не происходит.
     *
     * @param e сотрудник
     */
    public void remove(Employee e) {
        if (e == null) {
            return;
        }
        int index = indexOf(e.getId());
        if (index == -1) {
            return;
        }
        for (int i = index; i < count - 1; i++) {
            employees[i] = employees[i + 1];
        }
        employees[--count] = null;
    }

    /**
     * Ищет индекс сотрудника по идентификатору.
     *
     * @param id идентификатор
     * @return индекс в массиве или -1, если сотрудник не найден
     */
    private int indexOf(int id) {
        for (int i = 0; i < count; i++) {
            if (employees[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Ищет всех сотрудников, оказывающих юридические услуги
     * по указанной специализации (без учёта регистра).
     *
     * @param spec специализация
     * @return массив найденных сотрудников (может быть пустым)
     * @see ILegalService#getSpecialization()
     */
    public Employee[] findBySpecialization(String spec) {
        Employee[] result = new Employee[count];
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (employees[i] instanceof ILegalService) {
                ILegalService service = (ILegalService) employees[i];
                if (service.getSpecialization().equalsIgnoreCase(spec)) {
                    result[found++] = employees[i];
                }
            }
        }
        return Arrays.copyOf(result, found);
    }

    /**
     * Назначает ответственного адвоката на дело.
     *
     * @param c      дело
     * @param lawyer адвокат, который должен числиться в конторе
     * @throws IllegalArgumentException если адвоката нет в конторе
     * @see Lawyer#handleCase(LegalCase)
     */
    public void assignCase(LegalCase c, Lawyer lawyer) {
        if (indexOf(lawyer.getId()) == -1) {
            throw new IllegalArgumentException("Адвокат не числится в конторе");
        }
        lawyer.handleCase(c);
    }

    /**
     * Считает общую сумму выплат всем сотрудникам. Для каждого вызывается
     * свой {@link Employee#calculateSalary()} (полиморфизм).
     *
     * @return общая сумма выплат
     */
    public double totalPayroll() {
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += employees[i].calculateSalary();
        }
        return sum;
    }

    /**
     * Формирует сводный отчёт. Отчёты запрашиваются только у тех сотрудников,
     * которые реализуют {@link IReportable}.
     *
     * @return текст общего отчёта
     */
    public String generateGeneralReport() {
        StringBuilder sb = new StringBuilder("=== Общий отчёт конторы ===\n");
        for (int i = 0; i < count; i++) {
            sb.append(employees[i].getInfo()).append("\n");
            if (employees[i] instanceof IReportable) {
                sb.append(((IReportable) employees[i]).generateReport());
            }
        }
        sb.append("Итого выплат: ").append(totalPayroll()).append("\n");
        return sb.toString();
    }
}
