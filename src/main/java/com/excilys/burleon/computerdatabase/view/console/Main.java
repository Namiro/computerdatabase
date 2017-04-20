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

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.service.service.CompanyService;
import com.excilys.burleon.computerdatabase.service.service.ComputerService;
import com.excilys.burleon.computerdatabase.service.service.PageService;

public class Main {

    private static final IPageService<Company> companyPage = new PageService<>();
    private static final IPageService<Computer> computerPage = new PageService<>();;
    private static final IComputerService COMPUTER_SERVICE = new ComputerService();
    private static final ICompanyService COMPANY_SERVICE = new CompanyService();
    private static final Scanner SCAN = new Scanner(System.in);
    private static int choiceMainMenu;

    /**
     * To clear the console.
     */
    public static void clearConsole() {

    }

    /**
     * To display the menu and get the user choice.
     *
     * @param menuToDisplay
     *            The menu shown to the user
     * @return The menu choice done by the user
     */
    public static int displayMenyAndGetUserChoice(final StringBuilder menuToDisplay) {
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
    public static LocalDateTime inputDate() {
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
    private static long inputLong() {
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
     * The main function.
     *
     * @param args
     *            the args
     * @throws IOException
     *             The IOException
     */
    public static void main(final String[] args) throws IOException {
        Main.companyPage.setModelService(Main.COMPANY_SERVICE);
        Main.computerPage.setModelService(Main.COMPUTER_SERVICE);

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
            Main.choiceMainMenu = Main.displayMenyAndGetUserChoice(sb);

            switch (Main.choiceMainMenu) {
                case 1:
                    Main.showListAndMenu();
                    break;
                case 2:
                    Main.showListAndMenu();
                    break;
                case 3:
                    sb = new StringBuilder();
                    sb.append("Computer ID :\n");
                    Optional<Computer> computerOpt = Main.COMPUTER_SERVICE.get(Computer.class,
                            Main.choiceMainMenu = Main.displayMenyAndGetUserChoice(sb));
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
                            final long companyId = Main.inputLong();
                            if (companyId != 0) {
                                computer.setCompany(new Company.CompanyBuilder().id(companyId).build());
                            }
                            System.out.print("Introduce date (yyyy-MM-dd) :");
                            LocalDateTime date = Main.inputDate();
                            if (date != null) {
                                computer.setIntroduced(date);
                            }
                            System.out.print("Discontinue date (yyyy-MM-dd) :");
                            date = Main.inputDate();
                            if (date != null) {
                                computer.setDiscontinued(date);
                            }
                            Main.COMPUTER_SERVICE.save(computer);
                            Main.computerPage.refresh(Computer.class);
                        } catch (final ServiceException e) {
                            System.out.println(e.getMessage());
                            isContinueSubMenu = true;
                        }
                    } while (isContinueSubMenu);
                    break;
                case 5:
                    System.out.print("Computer update ID :");
                    computerOpt = Main.COMPUTER_SERVICE.get(Computer.class, Main.inputLong());
                    if (computerOpt.isPresent()) {
                        do {
                            try {
                                isContinueSubMenu = false;
                                System.out.print("Name :");
                                computerOpt.get().setName(Main.SCAN.nextLine());
                                System.out.print("Company Id :");
                                final long companyId = Main.inputLong();
                                if (companyId != 0) {
                                    computerOpt.get()
                                            .setCompany(new Company.CompanyBuilder().id(companyId).build());
                                }
                                System.out.print("Introduce date (yyyy-MM-dd) :");
                                LocalDateTime date = Main.inputDate();
                                if (date != null) {
                                    computerOpt.get().setIntroduced(date);
                                }
                                System.out.print("Discontinue date (yyyy-MM-dd) :");
                                date = Main.inputDate();
                                if (date != null) {
                                    computerOpt.get().setDiscontinued(date);
                                }
                                Main.COMPUTER_SERVICE.save(computerOpt.get());
                                Main.computerPage.refresh(Computer.class);
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
                    computerOpt = Main.COMPUTER_SERVICE.get(Computer.class,
                            Main.choiceMainMenu = Main.displayMenyAndGetUserChoice(sb));
                    if (computerOpt.isPresent()) {
                        Main.COMPUTER_SERVICE.remove(computerOpt.get());
                        Main.computerPage.refresh(Computer.class);
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
                    final Optional<Company> companyOpt = Main.COMPANY_SERVICE.get(Company.class,
                            Main.choiceMainMenu = Main.displayMenyAndGetUserChoice(sb));
                    if (companyOpt.isPresent()) {
                        Main.COMPANY_SERVICE.remove(companyOpt.get());
                        Main.companyPage.refresh(Company.class);
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

    /**
     * To display List of Computer or Company and there menu.
     */
    public static void showListAndMenu() {
        boolean isContinue = true;
        while (isContinue) {
            StringBuilder sb = new StringBuilder();
            sb.append("\t1. Next Page\n");
            sb.append("\t2. Previous page\n");
            sb.append("\t3. Show page x\n");
            sb.append("\t0. Pevious menu\n");
            if (Main.choiceMainMenu == 1) {
                System.out.println(Main.computerPage);
            } else if (Main.choiceMainMenu == 2) {
                System.out.println(Main.companyPage);
            }

            final int choice = Main.displayMenyAndGetUserChoice(sb);

            switch (choice) {
                case 1:
                    if (Main.choiceMainMenu == 1) {
                        Main.computerPage.next(Computer.class);
                    } else if (Main.choiceMainMenu == 2) {
                        Main.companyPage.next(Company.class);
                    }
                    break;
                case 2:
                    if (Main.choiceMainMenu == 1) {
                        Main.computerPage.previous(Computer.class);
                    } else if (Main.choiceMainMenu == 2) {
                        Main.companyPage.previous(Company.class);
                    }
                    break;
                case 3:
                    sb = new StringBuilder();
                    sb.append("\tNumber of page : ");
                    final int pageNumber = Main.displayMenyAndGetUserChoice(sb);
                    if (Main.choiceMainMenu == 1) {
                        Main.computerPage.page(Computer.class, pageNumber);
                    } else if (Main.choiceMainMenu == 2) {
                        Main.companyPage.page(Company.class, pageNumber);
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
}
