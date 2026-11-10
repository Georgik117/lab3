package lawfirm.legal;

/**
 * Статус юридического дела.
 * <br>
 * Перечисление неявно является {@code final}, поэтому требование
 * "объявить класс конечным" для него выполнено автоматически.
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 * @see LegalCase#changeStatus(CaseStatus, lawfirm.employee.Employee)
 */
public enum CaseStatus {
    /** Дело только зарегистрировано. */
    NEW("Новое"),
    /** Дело принято в работу. */
    IN_PROGRESS("В работе"),
    /** Дело закрыто с успешным результатом (даёт бонус адвокату). */
    CLOSED_SUCCESS("Закрыто успешно"),
    /** Дело закрыто с неуспешным результатом. */
    CLOSED_FAILED("Закрыто неуспешно");

    /** Название статуса для вывода пользователю. */
    private final String title;

    /**
     * Создаёт статус с заданным названием.
     *
     * @param title название статуса
     */
    CaseStatus(String title) {
        this.title = title;
    }

    /**
     * Возвращает название статуса.
     *
     * @return название статуса на русском языке
     */
    public String getTitle() {
        return title;
    }

    /**
     * Проверяет, является ли статус конечным (дело закрыто).
     *
     * @return {@code true}, если дело закрыто успешно или неуспешно
     */
    public boolean isClosed() {
        return this == CLOSED_SUCCESS || this == CLOSED_FAILED;
    }
}
