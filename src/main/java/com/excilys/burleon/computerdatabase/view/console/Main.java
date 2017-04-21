package com.excilys.burleon.computerdatabase.view.console;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.burleon.computerdatabase.repository.model.Company;
import com.excilys.burleon.computerdatabase.repository.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.spring.config.ApplicationConfig;

@Component
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final Scanner SCAN = new Scanner(System.in);

    private static int choiceMainMenu;

    /**
     * To clear the console.
     */
    public static void clearConsole() {

    }

    /**
     * The main function.
     *
     * @param args
     *            the args
     * @throws IOException
     *             The IOException
     */
    public static void main(final String[] args) throws IOException {

        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("javaee");
        context.register(ApplicationConfig.class);
        context.refresh();

        final Main p = context.getBean(Main.class);
        p.start(args);

        context.close();
    }

    @Autowired
    private IPageService<Company> companyPage;

    @Autowired
    private IPageService<Computer> computerPage;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IComputerService computerService;

    /**
     * To display the menu and get the user choice.
     *
     * @param menuToDisplay
     *            The menu shown to the user
     * @return The menu choice done by the user
     */
    public int displayMenyAndGetUserChoice(final StringBuilder menuToDisplay) {
        Main.clearConsole();
        System.out.println(menuToDisplay);
        boolean isContinue = true;
        int choice = 0;
        do {
            final String choiceString = Main.SCAN.nextLine();
            final Pattern pattern = Pattern.compile("(\\d+)");
            final Matcher matcher = pattern.matcher(choiceString);
            if (matcher.find()) {
                if (!matcher.group(1).equalsIgnoreCase("")) {
                    choice = Integer.valueOf(matcher.group(1));
                    isContinue = false;
                }
            }
            Main.clearConsole();
        } while (isContinue);
        return choice;
    }

    /**
     * Get a date with the format : yyyy-MM-dd.
     *
     * @return The localdatetime provide by the user
     */
    public LocalDateTime inputDate() {
        boolean isContinue = true;
        DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate datee = null;
        LocalDateTime date = null;
        while (isContinue) {
            final String line = Main.SCAN.nextLine();
            if (!line.trim().isEmpty()) {
                try {
                    datee = LocalDate.parse(line);
                    isContinue = false;
                    date = LocalDateTime.of(datee, LocalTime.now());
                } catch (final DateTimeParseException e) {
                    System.out.println("Sorry, that's not valid. Please try again.");
                }
            } else {
                isContinue = false;
            }
        }
        return date;
    }

    /**
     * Get a long.
     *
     * @return a long
     */
    private long inputLong() {
        boolean isContinue = true;
        long input = 0;
        do {
            final String inputLong = Main.SCAN.nextLine();
            final Pattern pattern = Pattern.compile("(\\d+)");
            final Matcher matcher = pattern.matcher(inputLong);
            if (!inputLong.equals("")) {
                if (matcher.find()) {
                    if (!matcher.group(1).equalsIgnoreCase("")) {
                        input = Long.valueOf(matcher.group(1));
                        isContinue = false;
                    }
                }
            } else {
                isContinue = false;
            }
        } while (isContinue);
        return input;
    }

    /**
     * To display List of Computer or Company and there menu.
     */
    public void showListAndMenu() {
        boolean isContinue = true;
        while (isContinue) {
            StringBuilder sb = new StringBuilder();
            sb.append("\t1. Next Page\n");
            sb.append("\t2. Previous page\n");
            sb.append("\t3. Show page x\n");
            sb.append("\t0. Pevious menu\n");
            if (Main.choiceMainMenu == 1) {
                System.out.println(this.computerPage);
            } else if (Main.choiceMainMenu == 2) {
                System.out.println(this.companyPage);
            }

            final int choice = this.displayMenyAndGetUserChoice(sb);

            switch (choice) {
                case 1:
                    if (Main.choiceMainMenu == 1) {
                        this.computerPage.next(Computer.class);
                    } else if (Main.choiceMainMenu == 2) {
                        this.companyPage.next(Company.class);
                    }
                    break;
                case 2:
                    if (Main.choiceMainMenu == 1) {
                        this.computerPage.previous(Computer.class);
                    } else if (Main.choiceMainMenu == 2) {
                        this.companyPage.previous(Company.class);
                    }
                    break;
                case 3:
                    sb = new StringBuilder();
                    sb.append("\tNumber of page : ");
                    final int pageNumber = this.displayMenyAndGetUserChoice(sb);
                    if (Main.choiceMainMenu == 1) {
                        this.computerPage.page(Computer.class, pageNumber);
                    } else if (Main.choiceMainMenu == 2) {
                        this.companyPage.page(Company.class, pageNumber);
                    }
                    break;
                case 0:
                    isContinue = false;
                    break;
                default:
                    break;
            }
        }
    }

    private void start(final String[] args) {

        this.companyPage.setModelService(this.companyService);
        this.companyPage.setRecordsByPage(5);
        this.companyPage.page(Company.class, 1);
        this.computerPage.setModelService(this.computerService);
        this.computerPage.setRecordsByPage(5);
        this.computerPage.page(Computer.class, 1);

        boolean isContinue = true;
        boolean isContinueSubMenu = false;
        while (isContinue) {
            StringBuilder sb = new StringBuilder();
            sb.append("\t1. List computers\n");
            sb.append("\t2. List companies\n");
            sb.append("\t3. Show computer details\n");
            sb.append("\t4. Create a computer\n");
            sb.append("\t5. Update a computer\n");
            sb.append("\t6. Delete a computer\n");
            sb.append("\t7. Delete a company (And computer associate)\n");
            sb.append("\t0. Exit\n");
            Main.choiceMainMenu = this.displayMenyAndGetUserChoice(sb);

            switch (Main.choiceMainMenu) {
                case 1:
                    this.showListAndMenu();
                    break;
                case 2:
                    this.showListAndMenu();
                    break;
                case 3:
                    sb = new StringBuilder();
                    sb.append("Computer ID :\n");
                    Optional<Computer> computerOpt = this.computerService.get(Computer.class,
                            Main.choiceMainMenu = this.displayMenyAndGetUserChoice(sb));
                    if (computerOpt.isPresent()) {
                        sb = new StringBuilder();
                        sb.append("Detail of : " + computerOpt.get().getId() + "\n");
                        sb.append("Name : " + computerOpt.get().getName() + "\n");
                        sb.append("Introduce date : " + computerOpt.get().getIntroduced() + "\n");
                        sb.append("Discontinue date : " + computerOpt.get().getDiscontinued() + "\n");
                        final Company company = computerOpt.get().getCompany();
                        if (company != null) {
                            sb.append("Linked with the company : " + company.getName() + "\n\n");
                        }
                        System.out.println(sb);
                    }
                    break;
                case 4:
                    do {
                        try {
                            isContinueSubMenu = false;
                            final Computer computer = new Computer();
                            System.out.print("Computer creation\n");
                            System.out.print("Name :");
                            computer.setName(Main.SCAN.nextLine());
                            System.out.print("Company Id :");
                            final long companyId = this.inputLong();
                            if (companyId != 0) {
                                computer.setCompany(new Company.CompanyBuilder().id(companyId).build());
                            }
                            System.out.print("Introduce date (yyyy-MM-dd) :");
                            LocalDateTime date = this.inputDate();
                            if (date != null) {
                                computer.setIntroduced(date);
                            }
                            System.out.print("Discontinue date (yyyy-MM-dd) :");
                            date = this.inputDate();
                            if (date != null) {
                                computer.setDiscontinued(date);
                            }
                            this.computerService.save(computer);
                            this.computerPage.refresh(Computer.class);
                        } catch (final ServiceException e) {
                            System.out.println(e.getMessage());
                            isContinueSubMenu = true;
                        }
                    } while (isContinueSubMenu);
                    break;
                case 5:
                    System.out.print("Computer update ID :");
                    computerOpt = this.computerService.get(Computer.class, this.inputLong());
                    if (computerOpt.isPresent()) {
                        do {
                            try {
                                isContinueSubMenu = false;
                                System.out.print("Name :");
                                computerOpt.get().setName(Main.SCAN.nextLine());
                                System.out.print("Company Id :");
                                final long companyId = this.inputLong();
                                if (companyId != 0) {
                                    computerOpt.get()
                                            .setCompany(new Company.CompanyBuilder().id(companyId).build());
                                }
                                System.out.print("Introduce date (yyyy-MM-dd) :");
                                LocalDateTime date = this.inputDate();
                                if (date != null) {
                                    computerOpt.get().setIntroduced(date);
                                }
                                System.out.print("Discontinue date (yyyy-MM-dd) :");
                                date = this.inputDate();
                                if (date != null) {
                                    computerOpt.get().setDiscontinued(date);
                                }
                                this.computerService.save(computerOpt.get());
                                this.computerPage.refresh(Computer.class);
                            } catch (final ServiceException e) {
                                System.out.println(e.getMessage());
                                isContinueSubMenu = true;
                            }
                        } while (isContinueSubMenu);
                    } else {
                        sb = new StringBuilder();
                        sb.append("The computer doens't exist\n");
                        System.out.println(sb);
                    }
                    break;
                case 6:
                    sb = new StringBuilder();
                    sb.append("Remove computer ID :\n");
                    computerOpt = this.computerService.get(Computer.class,
                            Main.choiceMainMenu = this.displayMenyAndGetUserChoice(sb));
                    if (computerOpt.isPresent()) {
                        this.computerService.remove(computerOpt.get());
                        this.computerPage.refresh(Computer.class);
                        sb = new StringBuilder();
                        sb.append("The computer has been deleted");
                        System.out.println(sb);

                    } else {
                        sb = new StringBuilder();
                        sb.append("The computer doens't exist\n");
                        System.out.println(sb);
                    }
                    break;
                case 7:
                    sb = new StringBuilder();
                    sb.append("Remove company ID :\n");
                    final Optional<Company> companyOpt = this.companyService.get(Company.class,
                            Main.choiceMainMenu = this.displayMenyAndGetUserChoice(sb));
                    if (companyOpt.isPresent()) {
                        this.companyService.remove(companyOpt.get());
                        this.companyPage.refresh(Company.class);
                        sb = new StringBuilder();
                        sb.append("The company has been deleted");
                        System.out.println(sb);

                    } else {
                        sb = new StringBuilder();
                        sb.append("The company doens't exist\n");
                        System.out.println(sb);
                    }
                    break;
                case 0:
                    isContinue = false;
                    System.out.println("------- APP IS CLOSED -------");
                    break;
                default:
                    break;
            }

        }
    }
}
