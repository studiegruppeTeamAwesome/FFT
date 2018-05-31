package view;

import logic.PoorCreditRatingException;
import logic.Car;
import logic.Customer;
import logic.FFSController;
import logic.FacadeController;
import logic.LoanOffer;
import logic.Salesman;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import com.ferrari.finances.dk.rki.Rating;

import javafx.application.Application;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FFSGui extends Application implements Observer {
// ansvar:Sofie review:Martin, Shahnaz
	
	Customer customer;
	Car chosenCar;
	FacadeController controller = new FFSController();
	LoanOffer loanOffer;
	Salesman salesman = controller.getSalesmenByName(System.getProperty("user.name"));
	TextField rateTF;
	TextField creditTF;
	BankThread bankThread = new BankThread();
	RKIThread rkiThread = new RKIThread();

	@Override
	public void start(Stage stage) throws Exception {
		bankThread.addObserver(this);
		rkiThread.addObserver(this);
		
		stage.setScene(initStartScreen(stage));
		stage.show();

	}

	/**
	 * @param stage The stage to pass when initiating a different scene
	 * upon an Action (mouse click on a button)
	 * @return The scene representing the start screen
	 */
	private Scene initStartScreen(Stage stage) {

		VBox box = new VBox();
		box.setPadding(new Insets(10, 10, 10, 10));
		box.setSpacing(10);

		Button newLoan = new Button("Ny låneaftale");
		Button approveLoan = new Button("Godkend låneaftale");
		if (!salesman.isBoss())
			approveLoan.setDisable(true);
		Button printLoan = new Button("Print låneaftale");
		box.getChildren().add(newLoan);
		box.getChildren().add(approveLoan);
		box.getChildren().add(printLoan);

		// inner EventHandler class to handle mouse clicks on navigational buttons
		newLoan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initCustomerScene(stage));
			}
		});

		approveLoan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initLoansOverview(stage, false));
			}
		});
		
		printLoan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initLoansOverview(stage, true));
			}
		});

		return new Scene(box);
	}

	
	/**
	 * @param stage The stage to pass when initiating a different scene
	 * @return The scene containing GridPanes representing mock-ups 1 and 2 (UC1-3)
	 */
	private Scene initCustomerScene(Stage stage) {
		GridPane grid = new GridPane();

		// mockup 1
		GridPane lookUpCustomerGrid = initLookUpCustomer(stage);

		grid.add(lookUpCustomerGrid, 0, 0);

		Button lookUp = new Button("Slå op");
		
		TextField phone = new TextField();
		phone.setPromptText("format: 12345678");
		
		lookUpCustomerGrid.add(lookUp, 0, 3);
		lookUpCustomerGrid.add(phone, 0, 2);

		
		// upon click on look up button: 
		lookUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				// see method for description
				checkNumberField(phone, "tlf. nr.");
				
				// get the customer represented by the entered phone number from the database 
				// (through facade controller) and assign to the instance variable customer
				customer = controller.getCustomerByPhone(Integer.parseInt(phone.getText()));
				

				// add the grid presenting customer details to user and expand the stage to 
				// accomodate the new grid
				grid.add(initCustomerInfo(stage), 0, 1);
				stage.sizeToScene();
			}
		});

		return new Scene(grid);

	}

	/**
	 * @param stage The stage to pass when initiating a different scene
	 * @return The GridPane representing mock-up 1 (UC1-3)
	 */
	private GridPane initLookUpCustomer(Stage stage) {

		GridPane grid = new GridPane();
		gridPaddingSpacing(grid, 10, "");

		Label prompt1 = new Label("Angiv kundeoplysninger");
		Button back = new Button("Tilbage");
		Label prompt2 = new Label("Kundens tlf.");

		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initStartScreen(stage));
				
			}
		});

		grid.add(prompt1, 0, 0);
		grid.add(back, 1, 0);
		grid.add(prompt2, 0, 1);

		return grid;
	}

	/**
	 * @param stage The stage to pass when initiating a different scene
	 * @return the GridPane representing mock-up 2 (UC1-3)
	 */
	private GridPane initCustomerInfo(Stage stage) {

		GridPane grid = new GridPane();
		gridPaddingSpacing(grid, 10, "");

		Label topLabel = new Label("Kunden eksisterer i databasen" + "\n" + "Bekræft kundeoplysninger");
		Label nameLabel = new Label("Navn:");
		Label addressLabel = new Label("Adresse");
		Label cprLabel = new Label("cpr:");

		Label name = new Label();
		Label address = new Label();
		Label cpr = new Label();
		
		// if the entered phone number does not represent a costumer in the database, the get
		// methods will return an exception, and the user will be alerted with a pop up message
		try {
		name.setText(customer.getName());
		address.setText(customer.getAddress());
		cpr.setText(customer.getCPR());
		} catch(NullPointerException e) {
			initPopUp("Kunden eksisterer ikke i databasen", "-fx-text-fill: red; -fx-font-weight: bold");
			return null;
		}

		Button createLoan = new Button("Opret Loan");

		createLoan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// if the customer already has a loan offer, the user shouldn't be able to create a new one
				if (customer.hasActiveOffer()) {
					initPopUp("kunden har allerede et aktivt lånetilbud", "-fx-text-fill: red; -fx-font-weight: bold");
					return;
				}
				
				// start the threads that gather the RKI-rating of the customer and the bank rate
				rkiThread.setCustomer(customer);
				Thread runnableBankThread = new Thread(bankThread);
				Thread runnableRkiThread = new Thread(rkiThread);
				runnableBankThread.start();
				runnableRkiThread.start();

				stage.setScene(initCreateLoan(stage));
			}
		});

		grid.add(topLabel, 0, 0, 2, 1);
		grid.add(nameLabel, 0, 1);
		grid.add(addressLabel, 0, 2);
		grid.add(cprLabel, 0, 3);
		grid.add(createLoan, 0, 4);
		grid.add(name, 1, 1);
		grid.add(address, 1, 2);
		grid.add(cpr, 1, 3);

		return grid;
	}

	
	
	/**
	 * @param stage The stage to pass when initiating a different scene
	 * @return The scene representing mock-up 3 (UC1-3)
	 */
	private Scene initCreateLoan(Stage stage) {

		GridPane grid = new GridPane();
		gridPaddingSpacing(grid, 10, "");

		Label prompt = new Label("Opret nyt lånetilbud");

		ComboBox<Car> cars = new ComboBox<Car>();
		cars.setPromptText("Vælg bilmodel");
		
		Label priceLabel = new Label("Pris:");
		Label price = new Label();
		Label downPaymentLabel = new Label("Udbetaling:");
		TextField downPayment = new TextField();
		Label noOfMonthsLabel = new Label("Antal måneder:");
		TextField noOfMonths = new TextField();
		Button calculateOffer = new Button("Beregn");
		Button back = new Button("Tilbage");
		
		GridPane.setHalignment(back, HPos.RIGHT);
		
		Label creditLabel = new Label("Kreditværdighed");
		creditTF = new TextField();
		creditTF.setPromptText("Indlæser...");
		creditTF.setEditable(false);
		
		Label rate = new Label("Nuværende rente");
		rateTF = new TextField();
		rateTF.setPromptText("Indlæser...");
		rateTF.setEditable(false);

		// Add the cars from the database to the drop down menu (ComboBox)
		cars.getItems().addAll(controller.getAllCars());
		cars.setPromptText("Vælg bil");
		
		// present the price of the chosen car from the drop down to the user
		cars.valueProperty().addListener(new ChangeListener<Car>() {
			@Override
			public void changed(ObservableValue<? extends Car> arg0, Car previous, Car chosen) {
				chosenCar = chosen;
				price.setText(chosenCar.getPrice() + "");
			}
		});

		calculateOffer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				// see method for description
				checkNumberField(downPayment, "Udbetaling");
				checkNumberField(noOfMonths, "Antal måneder");
				
				// only continue if the user has chosen a car
				if (cars.getValue() == null) {
					initPopUp("vælg venligst en bil", "");
					return;
				}
				
				try {
					
					// calculate the interest rate
					double interestRate = controller.calculateInterestRate(
							customer.getRating(),
							Double.parseDouble(rateTF.getText()), 
							Integer.parseInt(downPayment.getText()),
							Integer.parseInt(noOfMonths.getText()), 
							chosenCar.getPrice());

					// calculate the monthly rate
					double monthlyRate = controller.calculateMonthlyRate(interestRate);

					// calculate the size of the repayments (same amount every term)
					double repayments = controller.calculateRepayments(
							chosenCar,
							Integer.parseInt(downPayment.getText()), 
							monthlyRate,
							Integer.parseInt(noOfMonths.getText()));
					
					// initiate a loan offer based on the previous calculations
					loanOffer = new LoanOffer(
							interestRate, 
							Integer.parseInt(downPayment.getText()), 
							repayments,
							Integer.parseInt(noOfMonths.getText()), 
							customer, 
							chosenCar, 
							salesman);

					stage.setScene(initConfirmLoan(stage));

				} catch (PoorCreditRatingException e) {
					// in case of a rating of D, present pop up message to user
					initPopUp(e.getMessage(), "-fx-text-fill: red; -fx-font-weight: bold");
				}

			}
		});

		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initCustomerScene(stage));
			}
		});

		grid.add(prompt, 0, 0, 3, 1);
		grid.add(cars, 0, 1, 2, 1);
		grid.add(priceLabel, 0, 2);
		grid.add(price, 1, 2);
		grid.add(downPaymentLabel, 0, 3, 2, 1);
		grid.add(downPayment, 0, 4, 2, 1);
		grid.add(noOfMonthsLabel, 0, 5, 2, 1);
		grid.add(noOfMonths, 0, 6, 2, 1);

		grid.add(calculateOffer, 2, 7);

		grid.add(back, 3, 0);
		grid.add(creditLabel, 3, 3);
		grid.add(creditTF, 3, 4);
		grid.add(rate, 3, 5);
		grid.add(rateTF, 3, 6);

		return new Scene(grid);
	}

	/**
	 * @param stage The stage to pass when initiating a different scene
	 * @return The scene representing mock-up 4 (UC1-3)
	 */
	private Scene initConfirmLoan(Stage stage) {

		GridPane grid = new GridPane();
		gridPaddingSpacing(grid, 10, "");
		
		Label prompt = new Label("Bekræft oplysninger");
		grid.add(prompt, 0, 0);
		
		grid.add(initCustomerDetailsGrid(customer), 0, 1);
		grid.add(initCarDetailsGrid(chosenCar), 0, 2);
		grid.add(initSalesmanDetailsGrid(salesman), 0, 3);
		grid.add(initLoanDetailsGrid(loanOffer), 0, 4);

		Button back = new Button("Tilbage");
		back.setAlignment(Pos.TOP_RIGHT);
		Button confirm = new Button("Bekræft");
		grid.add(back, 1, 0);
		grid.add(confirm, 1, 5);

		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initCreateLoan(stage));
			}
		});

		confirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				// if the price of the chosen car does not exceed the loan limit of the current user
				// the offer should be instantly approved
				if (chosenCar.getPrice()<salesman.getLoanLimit()) 
					loanOffer.setApproved(true);
				
				// save the loan offer init pop up message based on whether the loan offer was saved successfully
				if (controller.saveLoanOffer(loanOffer)) {
					customer.setHasActiveOffer(true);
					controller.updateCustomerHasOffer(customer);
					initPopUp("Lån gemt", "");
				} else 
					initPopUp("Lån ikke gemt!", "-fx-text-fill: red; -fx-font-weight: bold");
			}
		});

		return new Scene(grid);
	}

	/**
	 * @param stage The stage to pass when initiating a different scene
	 * @param approved true or false based on whether an overview of approved
	 * or unapproved loans is desired
	 * @return A scene presenting an overview of loan offers either approved
	 * or unapproved
	 */
	private Scene initLoansOverview(Stage stage, boolean approved) {

		GridPane grid = new GridPane();
		gridPaddingSpacing(grid, 10, "");
		
		Label prompt = new Label("Vælg et tilbud");
		Button back = new Button("Tilbage");
		grid.add(prompt, 0, 0);
		grid.add(back, 1, 0);

		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initStartScreen(stage));
			}
		});

		VBox loans = new VBox();
		
		List<LoanOffer> offers = controller.getLoansByApproved(approved);

		// for every loan offer from database add them to a HBox to allow the user to get a
		// quick overview of the offer
		for (LoanOffer lo : offers) {
			HBox details = new HBox();
			details.setSpacing(20);
			Label modelName = new Label("Model: " + lo.getCar().getModel());
			Label price = new Label("Pris: " + lo.getCar().getPrice());
			Button pick = new Button("Se detaljer");

			pick.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (approved) 
						stage.setScene(initPrintLoan(stage, lo));
					else
						stage.setScene(initApproveLoan(stage, lo));
				
				}
			});

			details.getChildren().add(modelName);
			details.getChildren().add(price);
			details.getChildren().add(pick);
			loans.getChildren().add(details);
		}

		grid.add(loans, 0, 1);
		return new Scene(grid);
	}

	
	/**
	 * @param stage The stage to pass when initiating a different scene
	 * @param chosenLoanOffer The stage to pass when initiating a different scene
	 * @return A scene representing the details of the chosen loan and the ability
	 * to approve it if desired
	 */
	private Scene initApproveLoan(Stage stage, LoanOffer chosenLoanOffer) {

		GridPane grid = new GridPane();
		gridPaddingSpacing(grid, 10, "");
		Label prompt = new Label("Godkend lånetilbud");
		grid.add(prompt, 0, 0);
		grid.add(initCustomerDetailsGrid(chosenLoanOffer.getCostumer()), 0, 1);
		grid.add(initCarDetailsGrid(chosenLoanOffer.getCar()), 0, 2);
		grid.add(initSalesmanDetailsGrid(chosenLoanOffer.getSalesman()), 0, 3);
		grid.add(initLoanDetailsGrid(chosenLoanOffer), 0, 4);

		Button back = new Button("Tilbage");
		back.setAlignment(Pos.TOP_RIGHT);
		Button approve = new Button("Godkend");
		grid.add(back, 1, 0);
		grid.add(approve, 1, 5);

		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initLoansOverview(stage, false));
			}
		});

		approve.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				chosenLoanOffer.setApproved(true);

				// inform user if the approval went successfully through a pop up message
				if (controller.approveLoan(chosenLoanOffer)) {
					initPopUp("lån godkendt", "");
					stage.setScene(initLoansOverview(stage, false));
				} else {
					stage.setScene(initStartScreen(stage));
					initPopUp("lån ikke godkendt!", "-fx-text-fill: red; -fx-font-weight: bold");
				}
			}
		});

		return new Scene(grid);
	}

	/**
	 * @param stage The stage to pass when initiating a different scene
	 * @param chosenLoanOffer The stage to pass when initiating a different scene
	 * @return A scene representing the details of the chosen loan and the ability
	 * to print it to a CSV-file if desired
	 */
	private Scene initPrintLoan(Stage stage, LoanOffer chosenLoanOffer) {
		
		GridPane grid = new GridPane();
		gridPaddingSpacing(grid, 10, "");
		Label prompt = new Label("Print lånetilbud");
		grid.add(prompt, 0, 0);
		grid.add(initCustomerDetailsGrid(chosenLoanOffer.getCostumer()), 0, 1);
		grid.add(initCarDetailsGrid(chosenLoanOffer.getCar()), 0, 2);
		grid.add(initSalesmanDetailsGrid(chosenLoanOffer.getSalesman()), 0, 3);
		grid.add(initLoanDetailsGrid(chosenLoanOffer), 0, 4);

		Button back = new Button("Tilbage");
		back.setAlignment(Pos.TOP_RIGHT);
		Button print = new Button("Print CSV-fil");
		grid.add(back, 1, 0);
		grid.add(print, 1, 5);
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initLoansOverview(stage, true));
			}
		});
		
		print.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.printLoan(chosenLoanOffer);
				initPopUp("lånetilbud eksporteret til csv", "");
			}
		});
		
		return new Scene(grid);
	}
	
	/**
	 * @param popUpMessage A message to presen to the user through the pop up
	 * @param css A String to style the scene with. If no styling is needed, pass an empty String
	 */
	private void initPopUp(String popUpMessage, String css) {
		
		GridPane grid = new GridPane();
		Label message = new Label(popUpMessage);
		
		// only style the scene if the css parameter is not an empty String
		if (!css.equals(""))
			message.setStyle(css);
		
		grid.add(message, 0, 0);
		gridPaddingSpacing(grid, 10, "");
		Stage popUp = new Stage();
		popUp.setScene(new Scene(grid));
		
		
		popUp.show();
	}
	
	
	/**
	 * @param customer The customer to represent the details for 
	 * @return a GridPane presenting the customer's details to the user
	 */
	private GridPane initCustomerDetailsGrid(Customer customer) {
		GridPane customerGrid = new GridPane();
		customerGrid.add(new Label("Kunde"), 0, 0);
		customerGrid.add(new Label("Navn:"), 0, 1);

		Label customerName = new Label(customer.getName());
		customerName.setMinSize(200, 30);
		customerGrid.add(customerName, 1, 1);

		customerGrid.add(new Label("Tlf:"), 0, 2);
		customerGrid.add(new Label("" + customer.getPhone()), 1, 2);
		 gridPaddingSpacing(customerGrid, 10, "-fx-background-color: #c4c4c4;");

		return customerGrid;
	}

	
	/**
	 * @param car The car to represent the details for 
	 * @return a GridPane presenting the car's details to the user
	 */
	private GridPane initCarDetailsGrid(Car car) {
		GridPane carGrid = new GridPane();
		
		carGrid.add(new Label("Bil"), 0, 0);
		carGrid.add(new Label("Model:"), 0, 1);
		carGrid.add(new Label(car.getModel()), 1, 1);
		carGrid.add(new Label("Pris:"), 0, 2);
		carGrid.add(new Label("" + car.getPrice()), 1, 2);
		gridPaddingSpacing(carGrid, 10, " -fx-background-color: #c4c4c4;");

		return carGrid;
	}

	
	/**
	 * @param salesman The salesman to represent the details for 
	 * @return a GridPane presenting the salesman's details to the user
	 */
	private GridPane initSalesmanDetailsGrid(Salesman salesman) {
		GridPane salesmanGrid = new GridPane();
		salesmanGrid.add(new Label("Sælger"), 0, 0);
		salesmanGrid.add(new Label("Navn:"), 0, 1);
		salesmanGrid.add(new Label(salesman.getName()), 1, 1);
		gridPaddingSpacing(salesmanGrid, 10, " -fx-background-color: #c4c4c4;");

		return salesmanGrid;
	}

	
	/**
	 * @param loanOffer The loan offer to represent the details for 
	 * @return a GridPane presenting the loan offer's details to the user
	 */
	private GridPane initLoanDetailsGrid(LoanOffer loanOffer) {
		GridPane detailsGrid = new GridPane();
		gridPaddingSpacing(detailsGrid, 10, " -fx-background-color: #c4c4c4;");
		detailsGrid.add(new Label("Detaljer"), 0, 0);
		detailsGrid.add(new Label("Udbetaling:"), 0, 1);
		detailsGrid.add(new Label(loanOffer.getDownPayment() + " kr."), 1, 1);
		detailsGrid.add(new Label("Antal ydelser:"), 0, 2);
		detailsGrid.add(new Label("" + loanOffer.getNumberOfMonths()), 1, 2);
		detailsGrid.add(new Label("Afdrag:"), 0, 3);
		
		DecimalFormat formatter = new DecimalFormat("##.##");
		formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		
		detailsGrid.add(new Label(formatter.format(loanOffer.getRepayments()) + " kr."), 1, 3);
		detailsGrid.add(new Label("ÅOP:"), 0, 4);
		detailsGrid.add(new Label("" + loanOffer.getAnnualCost()), 1, 4);
		

		return detailsGrid;
	}

	
	/**
	 * 
	 * @param grid The grid that needs styling
	 * @param insets The insets value desired for padding the grid
	 * @param css The String used to style the grid. If no styling is needed, pass an empty String
	 */
	private void gridPaddingSpacing(GridPane grid, int insets, String css) {
		grid.setPadding(new Insets(insets, insets, insets, insets));
		grid.setHgap(insets);
		grid.setVgap(insets);
		
		// only style if the css String isn't empty
		if (!css.isEmpty())
			grid.setStyle(css);
		
	}

	/**
	 * The method is used to make sure a TextField only allows numbers, and informs the user in case 
	 * an illegal String has been entered.
	 * 
	 * @param tf A TextField that the user should only be able to write numbers in
	 * @param tfName A String representing the TextField, used to inform the user which TextField
	 * rises issues
	 */
	private void checkNumberField(TextField tf, String tfName) {
		
		char[] array = tf.getText().toCharArray();
		
		if (array.length==0) {
			initPopUp("Udfyld venligst tekstfeltet " + tfName + " først", "");
			return;
		}
		
		for (char ch: array) {
			if (!(ch >= '0' && ch <= '9' )) {
				tf.clear();
				initPopUp("Indtast kun tal i tekstfeltet " + tfName, "");
				break;
			}
		}
	}
	

	@Override
	public void update(Observable sub, Object obj) {

		if (sub instanceof BankThread) {
			double rate = (double) obj;
			// format the rate to only have two decimals
			DecimalFormat formatter = new DecimalFormat("##.##");
			formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
			rateTF.setText(formatter.format(rate));

		} else {
			creditTF.setText("" + customer.getRating());
			// warn the user in case the credit rating is too low
			if (customer.getRating() == Rating.D) {
				creditTF.setText(customer.getRating() + " - for lav!");
				creditTF.setStyle("-fx-text-fill: red;");
			}
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
