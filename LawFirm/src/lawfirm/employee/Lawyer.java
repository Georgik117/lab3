package lawfirm.employee;

import lawfirm.interfaces.ILegalService;
import lawfirm.interfaces.IReportable;
import lawfirm.legal.CaseStatus;
import lawfirm.legal.LegalCase;

import java.util.Arrays;

/**
 * Адвокат: ведёт дела, получает оклад и бонус за каждое успешно закрытое дело.
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 * @see Employee
 * @see ILegalService
 * @see IReportable
 */
public final class Lawyer extends Employee implements ILegalService, IReportable {
    /** Бонус за одно успешно закрытое дело. */
    private static final double BONUS_PER_SUCCESS = 5000.0;
    /** Начальная ёмкость массива дел. */
    private static final int INITIAL_CAPACITY = 4;

    /** Специализация адвоката. */
    private final String specialization;
    /** Дела, которые ведёт адвокат. */
    private LegalCase[] cases = new LegalCase[INITIAL_CAPACITY];
    /** Количество дел в ведении. */
    private int casesCount = 0;

    /**
     * Создаёт адвоката.
     *
     * @param id             идентификатор
     * @param name           имя
     * @param experience     стаж в годах
     * @param baseSalary     оклад
     * @param specialization специализация
     * @throws IllegalArgumentException если данные сотрудника некорректны
     * @see Employee#Employee(int, String, int, double)
     */
    public Lawyer(int id, String name, int experience, double baseSalary,
                  String specialization) {
        super(id, name, experience, baseSalary);
        this.specialization = specialization;
    }

    /**
     * Возвращает специализацию адвоката.
     *
     * @return специализация
     */
    @Override
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Принимает дело в ведение: сохраняет его, подключает адвоката как участника
     * и переводит новое дело в статус {@link CaseStatus#IN_PROGRESS}.
     * Если дело уже ведётся этим адвокатом, ничего не происходит.
     *
     * @param c юридическое дело
     * @throws IllegalArgumentException если дело равно {@code null}
     */
    @Override
    public void handleCase(LegalCase c) {
        if (c == null) {
            throw new IllegalArgumentException("Дело не может быть null");
        }
        for (int i = 0; i < casesCount; i++) {
            if (cases[i].getCaseId() == c.getCaseId()) {
                return; // дело уже ведётся этим адвокатом
            }
        }
        if (casesCount == cases.length) {
            cases = Arrays.copyOf(cases, cases.length * 2);
        }
        cases[casesCount++] = c;
        c.addParticipant(this);
        if (c.getStatus() == CaseStatus.NEW) {
            c.changeStatus(CaseStatus.IN_PROGRESS, this);
        } else {
            c.addLogEntry(getName() + " принял дело в ведение");
        }
    }

    /**
     * Закрывает дело успешно или неуспешно. От результата зависит бонус.
     *
     * @param c       закрываемое дело
     * @param success {@code true}, если дело решено успешно
     * @throws IllegalStateException если дело уже закрыто
     * @see #calculateSalary()
     */
    public void closeCase(LegalCase c, boolean success) {
        c.changeStatus(success ? CaseStatus.CLOSED_SUCCESS : CaseStatus.CLOSED_FAILED, this);
    }

    /**
     * Возвращает количество дел в ведении адвоката.
     *
     * @return число дел
     */
    public int getCasesCount() {
        return casesCount;
    }

    /**
     * Подсчитывает успешно закрытые дела.
     *
     * @return количество дел со статусом {@link CaseStatus#CLOSED_SUCCESS}
     */
    private int countSuccessful() {
        int count = 0;
        for (int i = 0; i < casesCount; i++) {
            if (cases[i].getStatus() == CaseStatus.CLOSED_SUCCESS) {
                count++;
            }
        }
        return count;
    }

    /**
     * Рассчитывает зарплату адвоката: оклад плюс бонус за каждое
     * успешно закрытое дело.
     *
     * @return итоговая зарплата
     */
    @Override
    public double calculateSalary() {
        return getBaseSalary() + BONUS_PER_SUCCESS * countSuccessful();
    }

    /**
     * Формирует отчёт по делам адвоката.
     *
     * @return текст отчёта
     */
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Отчёт адвоката ").append(getName())
          .append(" (").append(specialization).append(")\n");
        sb.append("Дел в ведении: ").append(casesCount)
          .append(", успешно закрыто: ").append(countSuccessful()).append("\n");
        for (int i = 0; i < casesCount; i++) {
            sb.append("  - ").append(cases[i]).append("\n");
        }
        return sb.toString();
    }
}
