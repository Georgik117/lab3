package lawfirm.interfaces;

import lawfirm.legal.LegalCase;

/**
 * Контракт для работников, оказывающих юридические услуги.
 * <br>
 * Реализуется классами {@link lawfirm.employee.Lawyer} и {@link lawfirm.employee.Consultant}.
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 * @see LegalCase
 */
public interface ILegalService {
    /**
     * Принимает дело в работу или участвует в его ведении.
     * Конкретное поведение определяет реализующий класс.
     *
     * @param c юридическое дело
     * @throws IllegalArgumentException если дело равно {@code null}
     */
    void handleCase(LegalCase c);

    /**
     * Возвращает специализацию работника.
     *
     * @return специализация, например "уголовное право"
     */
    String getSpecialization();
}
