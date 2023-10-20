package com.basejava.webapp.model;

import com.basejava.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ResumeTestData {
    public static Resume fillResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.getContacts().put(ContactType.NUMBER, ContactType.NUMBER.getTitle());
        resume.getContacts().put(ContactType.SKYPE, ContactType.SKYPE.getTitle());
        resume.getContacts().put(ContactType.EMAIL, ContactType.EMAIL.getTitle());
        resume.getContacts().put(ContactType.LINKEDIN, ContactType.LINKEDIN.getTitle());
        resume.getContacts().put(ContactType.GITHUB, ContactType.GITHUB.getTitle());
        resume.getContacts().put(ContactType.STACKOVERFLOW, ContactType.STACKOVERFLOW.getTitle());
        resume.getContacts().put(ContactType.HOME_PAGE, ContactType.HOME_PAGE.getTitle());
        resume.getSections().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.getSections().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.getSections().put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, " +
                "многомодульный Spring Boot + Vaadin проект для комплексных DIY смет", "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," +
                " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок" +
                " и ведение проектов. Более 3500 выпускников.")));
        resume.getSections().put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce")));
        resume.getSections().put(SectionType.EXPERIENCE, new CompanySection(new ArrayList<>(Arrays.asList(new Company("www.alcatel.ru", "Alcatel",
                new ArrayList<>(Arrays.asList(new Period("Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                        LocalDate.parse("1997-09-01"), LocalDate.parse("2005-01-01")))))))));
        resume.getSections().put(SectionType.EDUCATION, new CompanySection(new ArrayList<>(Arrays.asList(new Company("www.itmo.ru", "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                new ArrayList<>(Arrays.asList(new Period("Инженер", "(программист С, С++)", DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JUNE)),
                        new Period("Аспирантура", "(программист С, С++)", DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JUNE)))))))));
        return resume;
    }

}
