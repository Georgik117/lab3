package lawfirm.legal;

import lawfirm.employee.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Юридическое дело. Хранит журнал действий (аудит) и список участников.
 * Вместо {@code List} используются массивы (требование лабораторной работы).
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 * @see CaseStatus
 * @see lawfirm.employee.Lawyer#handleCase(LegalCase)
 */
public final class LegalCase {
    /** Начальная ёмкость массивов участников и журнала. */
    private static final int INITIAL_CAPACITY = 4;

    /** Номер дела. */
    private final int caseId;
    /** Тип дела (уголовное, гражданское и т.д.). */
    private final String caseType;
    /** Имя клиента. */
    private final String clientName;
    /** Описание дела. */
    private final String description;
    /** Крайний срок. */
    private final LocalDate deadline;
    /** Текущий статус. */
    private CaseStatus status;

    /** Участники дела. */
    private Employee[] participants = new Employee[INITIAL_CAPACITY];
    /** Количество участников. */
    private int participantsCount = 0;

    /** Журнал действий по делу. */
    private String[] log = new String[INITIAL_CAPACITY];
    /** Количество записей в журнале. */
    private int logCount = 0;

    /**
     * Создаёт новое дело со статусом {@link CaseStatus#NEW} и первой
     * записью в журнале.
     *
     * @param caseId      номер дела
     * @param caseType    тип дела
     * @param clientName  имя клиента (не пустое)
     * @param description описание дела
     * @param deadline    крайний срок
     * @throws IllegalArgumentException если имя клиента пустое
     */
    public LegalCase(int caseId, String caseType, String clientName,
                     String description, LocalDate deadline) {
        if (clientName == null || clientName.trim().isEmpty()) {
            throw new IllegalArgumentException("У дела должен быть клиент");
        }
        this.caseId = caseId;
        this.caseType = caseType;
        this.clientName = clientName;
        this.description = description;
        this.deadline = deadline;
        this.status = CaseStatus.NEW;
        addLogEntry("Дело зарегистрировано");
    }

    /**
     * Возвращает номер дела.
     *
     * @return номер дела
     */
    public int getCaseId() {
        return caseId;
    }

    /**
     * Возвращает тип дела.
     *
     * @return тип дела
     */
    public String getCaseType() {
        return caseType;
    }

    /**
     * Возвращает имя клиента.
     *
     * @return имя клиента
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Возвращает описание дела.
     *
     * @return описание
     */
    public String getDescription() {
        return description;
    }

    /**
     * Возвращает крайний срок дела.
     *
     * @return дата крайнего срока
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * Возвращает текущий статус дела.
     *
     * @return статус
     */
    public CaseStatus getStatus() {
        return status;
    }

    /**
     * Меняет статус дела и записывает это в журнал. Закрытое дело изменить нельзя.
     *
     * @param newStatus новый статус
     * @param who       сотрудник, изменивший статус
     * @throws IllegalStateException если дело уже закрыто
     */
    public void changeStatus(CaseStatus newStatus, Employee who) {
        if (status.isClosed()) {
            throw new IllegalStateException("Дело №" + caseId + " уже закрыто");
        }
        CaseStatus old = status;
        status = newStatus;
        addLogEntry(who.getName() + " изменил статус: " + old.getTitle()
                + " -> " + newStatus.getTitle());
    }

    /**
     * Добавляет участника дела. Повторно один и тот же сотрудник
     * (по идентификатору) не добавляется.
     *
     * @param e сотрудник
     * @throws IllegalArgumentException если сотрудник равен {@code null}
     */
    public void addParticipant(Employee e) {
        if (e == null) {
            throw new IllegalArgumentException("Участник не может быть null");
        }
        for (int i = 0; i < participantsCount; i++) {
            if (participants[i].getId() == e.getId()) {
                return;
            }
        }
        if (participantsCount == participants.length) {
            participants = Arrays.copyOf(participants, participants.length * 2);
        }
        participants[participantsCount++] = e;
        addLogEntry("К делу подключён: " + e.getName());
    }

    /**
     * Добавляет запись в журнал действий с отметкой времени (аудит).
     *
     * @param text текст записи
     */
    public void addLogEntry(String text) {
        if (logCount == log.length) {
            log = Arrays.copyOf(log, log.length * 2);
        }
        log[logCount++] = LocalDateTime.now().withNano(0) + " | " + text;
    }

    /**
     * Возвращает копию журнала, чтобы его нельзя было изменить снаружи.
     *
     * @return массив записей журнала
     */
    public String[] getLog() {
        return Arrays.copyOf(log, logCount);
    }

    /**
     * Возвращает копию массива участников дела.
     *
     * @return массив участников
     */
    public Employee[] getParticipants() {
        return Arrays.copyOf(participants, participantsCount);
    }

    /**
     * Возвращает текстовое представление дела.
     *
     * @return строка с номером, типом, клиентом, статусом, сроком и числом участников
     */
    @Override
    public String toString() {
        return "Дело №" + caseId + " [" + caseType + "], клиент: " + clientName
                + ", статус: " + status.getTitle() + ", срок: " + deadline
                + ", участников: " + participantsCount;
    }
}
