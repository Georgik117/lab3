package lawfirm.interfaces;

/**
 * Контракт для расчёта выплат (в первую очередь внешним специалистам).
 * <br>
 * Реализуется классом {@link lawfirm.employee.Consultant}.
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 */
public interface IPayable {
    /**
     * Рассчитывает сумму выплаты за выполненную работу.
     *
     * @return сумма выплаты
     */
    double computePayment();
}
