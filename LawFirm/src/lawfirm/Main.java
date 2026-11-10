package lawfirm;

import lawfirm.employee.Assistant;
import lawfirm.employee.Consultant;
import lawfirm.employee.Lawyer;
import lawfirm.legal.LegalCase;
import lawfirm.manager.LawFirmManager;

import java.time.LocalDate;

/**
 * Необязательная демонстрация работы иерархии (для защиты достаточно самих классов).
 *
 * @author Асланиди Г. А.
 * @version 1.0
 * @since 1.0
 */
public class Main {
    /**
     * Создаёт экземпляр класса. Для демонстрации достаточно метода {@link #main(String[])}.
     */
    public Main() {
    }

    /**
     * Точка входа: создаёт контору, сотрудников и дело, затем печатает отчёт.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        LawFirmManager manager = new LawFirmManager();

        Lawyer lawyer = new Lawyer(1, "Иванов И.И.", 8, 90000, "уголовное право");
        Assistant assistant = new Assistant(2, "Петрова А.С.", 2, 40000, lawyer);
        Consultant consultant = new Consultant(3, "Сидоров П.П.", 15, 0,
                "уголовное право", 3000, true);

        manager.add(lawyer);
        manager.add(assistant);
        manager.add(consultant);

        LegalCase c = new LegalCase(101, "уголовное", "ООО Ромашка",
                "Защита по делу о мошенничестве", LocalDate.of(2026, 12, 20));
        manager.assignCase(c, lawyer);
        consultant.handleCase(c);
        consultant.addHours(10);
        assistant.addTask("Собрать материалы дела №101");
        lawyer.closeCase(c, true);

        System.out.println(manager.generateGeneralReport());
        System.out.println(c);
        for (String line : c.getLog()) {
            System.out.println(line);
        }
        System.out.println("Найдено по специализации: "
                + manager.findBySpecialization("Уголовное право").length);
    }
}
