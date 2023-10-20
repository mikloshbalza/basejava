package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.getContacts().put(ContactType.NUMBER, ContactType.NUMBER.getTitle());
        resume.getContacts().put(ContactType.SKYPE, ContactType.SKYPE.getTitle());
        resume.getContacts().put(ContactType.EMAIL, ContactType.EMAIL.getTitle());
        resume.getContacts().put(ContactType.LINKEDIN, ContactType.LINKEDIN.getTitle());
        resume.getContacts().put(ContactType.GITHUB, ContactType.GITHUB.getTitle());
        resume.getContacts().put(ContactType.STACKOVERFLOW, ContactType.STACKOVERFLOW.getTitle());
        resume.getContacts().put(ContactType.HOME_PAGE, ContactType.HOME_PAGE.getTitle());
        System.out.println(resume.getContacts());
        resume.getSections().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.getSections().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.getSections().put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, " +
                "многомодульный Spring Boot + Vaadin проект для комплексных DIY смет")));
//        resume.getSections().put(SectionType.EXPERIENCE, new CompanySection(new HashMap<Company, Period>(){{
//                put(new Company("www.alcatel.ru","Alcatel"), new Period("Инженер по аппаратному и программному тестированию",
//                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",LocalDate.parse("1997-09-01"),LocalDate.parse("2005-01-01")));
//        }}));

        resume.getSections().put(SectionType.EXPERIENCE, new CompanySection(new ArrayList<>(Arrays.asList(new Company("www.alcatel.ru", "Alcatel",
                new ArrayList<>(Arrays.asList(new Period("Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                        LocalDate.parse("1997-09-01"), LocalDate.parse("2005-01-01")))))))));
        System.out.println(resume.getSections());

    }

}
