package lawfirm.interfaces;

/**
 * Контракт для объектов, умеющих формировать отчёт.
 * <br>
 * Реализуется классами {@link lawfirm.employee.Lawyer} и {@link lawfirm.employee.Assistant}.
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 * @see lawfirm.manager.LawFirmManager#generateGeneralReport()
 */
public interface IReportable {
    /**
     * Формирует текстовый отчёт об объекте.
     *
     * @return строка с текстом отчёта
     */
    String generateReport();
}
