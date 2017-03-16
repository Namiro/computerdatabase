package com.excilys.burleon.computerdatabase.view.console;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.service.ComputerService;
import com.excilys.burleon.computerdatabase.service.service.PageService;

public class Main {

	private static final Scanner scan = new Scanner(System.in);
	private static PageService<Company> companyPage;
	private static PageService<Computer> computerPage;
	private static int choiceMainMenu;
	private static final ComputerService computerService = new ComputerService();

	public static void clearConsole() {
		// TODO;
	}

	/**
	 * To display the menu and get the user choice
	 *
	 * @param menuToDisplay
	 *            The menu shown to the user
	 * @return
	 */
	public static int displayMenyAndGetUserChoice(final StringBuilder menuToDisplay) {
		Main.clearConsole();
		System.out.println(menuToDisplay);
		boolean isContinue = true;
		int choice = 0;
		do {
			final String choiceString = Main.scan.nextLine();
			final Pattern pattern = Pattern.compile("(\\d+)");
			final Matcher matcher = pattern.matcher(choiceString);
			if (matcher.find())
				if (!matcher.group(1).equalsIgnoreCase("")) {
					choice = Integer.valueOf(matcher.group(1));
					isContinue = false;
				}
			Main.clearConsole();
		} while (isContinue);
		return choice;
	}

	/**
	 * Get a date with the format : yyyy-MM-dd
	 *
	 * @return
	 */
	public static LocalDateTime inputDate() {
		boolean isContinue = true;
		DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate datee = null;
		LocalDateTime date = null;
		while (isContinue) {
			final String line = Main.scan.nextLine();
			if (!line.trim().isEmpty())
				try {
					datee = LocalDate.parse(line);
					isContinue = false;
					date = LocalDateTime.of(datee, LocalTime.now());
				} catch (final DateTimeParseException e) {
					System.out.println("Sorry, that's not valid. Please try again.");
				}
			else
				isContinue = false;
		}
		return date;
	}

	/**
	 * Get a long
	 *
	 * @return
	 */
	private static long inputLong() {
		boolean isContinue = true;
		long input = 0;
		do {
			final String inputLong = Main.scan.nextLine();
			final Pattern pattern = Pattern.compile("(\\d+)");
			final Matcher matcher = pattern.matcher(inputLong);
			if (!inputLong.equals("")) {
				if (matcher.find())
					if (!matcher.group(1).equalsIgnoreCase("")) {
						input = Long.valueOf(matcher.group(1));
						isContinue = false;
					}
			} else
				isContinue = false;
		} while (isContinue);
		return input;
	}

	public static void main(final String[] args) throws IOException {
		Main.companyPage = new PageService<>(Company.class);
		Main.computerPage = new PageService<>(Computer.class);

		boolean isContinue = true;
		boolean isContinueSubMenu = false;
		while (isContinue) {
			StringBuilder sb = new StringBuilder();
			sb.append("\t1. List computers\n");
			sb.append("\t2. List companies\n");
			sb.append("\t3. Show computer details\n");
			sb.append("\t4. Create a computer\n");
			sb.append("\t5. Update a company\n");
			sb.append("\t6. Delete a company\n");
			sb.append("\t7. Exit\n");
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
				Computer computer = Main.computerService.get(Computer.class,
						Main.choiceMainMenu = Main.displayMenyAndGetUserChoice(sb));
				if (computer != null) {
					sb = new StringBuilder();
					sb.append("Detail of : " + computer.getId() + "\n");
					sb.append("Name : " + computer.getName() + "\n");
					sb.append("Introduce date : " + computer.getIntroduced() + "\n");
					sb.append("Discontinue date : " + computer.getDiscontinued() + "\n");
					final Company company = computer.getCompany();
					if (company != null)
						sb.append("Linked with the company : " + company.getName() + "\n\n");
					System.out.println(sb);
				}
				break;
			case 4:
				do
					try {
						isContinueSubMenu = false;
						computer = new Computer();
						System.out.print("Computer creation\n");
						System.out.print("Name :");
						computer.setName(Main.scan.nextLine());
						System.out.print("Company Id :");
						final long companyId = Main.inputLong();
						if (companyId != 0)
							computer.setCompany(new Company.CompanyBuilder().id(companyId).build());
						System.out.print("Introduce date (yyyy-MM-dd) :");
						LocalDateTime date = Main.inputDate();
						if (date != null)
							computer.setIntroduced(date);
						System.out.print("Discontinue date (yyyy-MM-dd) :");
						date = Main.inputDate();
						if (date != null)
							computer.setDiscontinued(date);
						Main.computerService.save(computer);
						Main.computerPage.refresh();
					} catch (final ServiceException e) {
						System.out.println(e.getMessage());
						isContinueSubMenu = true;
					}
				while (isContinueSubMenu);
				break;
			case 5:
				computer = new Computer();
				System.out.print("Computer update ID :");
				computer = Main.computerService.get(Computer.class, Main.inputLong());
				if (computer != null)
					do
						try {
							isContinueSubMenu = false;
							System.out.print("Name :");
							computer.setName(Main.scan.nextLine());
							System.out.print("Company Id :");
							final long companyId = Main.inputLong();
							if (companyId != 0)
								computer.setCompany(new Company.CompanyBuilder().id(companyId).build());
							System.out.print("Introduce date (yyyy-MM-dd) :");
							LocalDateTime date = Main.inputDate();
							if (date != null)
								computer.setIntroduced(date);
							System.out.print("Discontinue date (yyyy-MM-dd) :");
							date = Main.inputDate();
							if (date != null)
								computer.setDiscontinued(date);
							Main.computerService.save(computer);
							Main.computerPage.refresh();
						} catch (final ServiceException e) {
							System.out.println(e.getMessage());
							isContinueSubMenu = true;
						}
					while (isContinueSubMenu);
				else {
					sb = new StringBuilder();
					sb.append("The computer doens't exist\n");
					System.out.println(sb);
				}
				break;
			case 6:
				sb = new StringBuilder();
				sb.append("Remove computer ID :\n");
				computer = Main.computerService.get(Computer.class,
						Main.choiceMainMenu = Main.displayMenyAndGetUserChoice(sb));
				if (computer != null) {
					Main.computerService.remove(computer);
					Main.companyPage.refresh();
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
				isContinue = false;
				System.out.println("------- APP IS CLOSED -------");
				break;
			default:
				break;
			}

		}

	}

	/**
	 * To display List of Computer or Company and there menu
	 */
	public static void showListAndMenu() {
		boolean isContinue = true;
		while (isContinue) {
			StringBuilder sb = new StringBuilder();
			sb.append("\t1. Next Page\n");
			sb.append("\t2. Previous page\n");
			sb.append("\t3. Show page x\n");
			sb.append("\t4. Pevious menu\n");
			if (Main.choiceMainMenu == 1)
				System.out.println(Main.computerPage);
			else if (Main.choiceMainMenu == 2)
				System.out.println(Main.companyPage);

			final int choice = Main.displayMenyAndGetUserChoice(sb);

			switch (choice) {
			case 1:
				if (Main.choiceMainMenu == 1)
					Main.computerPage.next();
				else if (Main.choiceMainMenu == 2)
					Main.companyPage.next();
				break;
			case 2:
				if (Main.choiceMainMenu == 1)
					Main.computerPage.previous();
				else if (Main.choiceMainMenu == 2)
					Main.companyPage.previous();
				break;
			case 3:
				sb = new StringBuilder();
				sb.append("\tNumber of page : ");
				final int pageNumber = Main.displayMenyAndGetUserChoice(sb);
				if (Main.choiceMainMenu == 1)
					Main.computerPage.page(pageNumber);
				else if (Main.choiceMainMenu == 2)
					Main.companyPage.page(pageNumber);
				break;
			case 4:
				isContinue = false;
				break;
			default:
				break;
			}
		}
	}
}
