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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FFSGui extends Application implements Observer {
	// ansvar:Sofie review:Martin,Shahnaz

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
		System.out.println(System.getProperty("user.name"));

		stage.setScene(initStartScreen(stage));
		stage.show();

	}

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

	private Scene initCustomerScene(Stage stage) {
		GridPane grid = new GridPane();

		// mockup 1
		GridPane lookUpCustomerGrid = initLookUpCustomer(stage);

		grid.add(lookUpCustomerGrid, 0, 0);

		Button lookUp = new Button("Slå op");

		// textfield hvor bruger kan indtaste kundes tlf. nr. - gives en
		// prompt for at hjælpe bruger til det rigtige format
		TextField phone = new TextField("format: 12345678");
		phone.setStyle("-fx-text-inner-color: gray;");
		phone.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				phone.clear();
				phone.setStyle("-fx-text-inner-color: black;");
			}
		});

		lookUpCustomerGrid.add(lookUp, 0, 3);
		lookUpCustomerGrid.add(phone, 0, 2);

		lookUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				checkNumberField(phone, "tlf. nr.");

				customer = controller.getCustomerByPhone(Integer.parseInt(phone.getText()));
				grid.add(initCustomerInfo(stage), 0, 1);

				stage.sizeToScene();
			}
		});

		return new Scene(grid);

	}

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

	private GridPane initCustomerInfo(Stage stage) {

		GridPane grid = new GridPane();

		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setHgap(10);
		grid.setVgap(10);

		Label topLabel = new Label("Kunden eksisterer i databasen" + "\n" + "Bekræft kundeoplysninger");
		Label nameLabel = new Label("Navn:");
		Label addressLabel = new Label("Adresse");
		Label cprLabel = new Label("cpr:");

		Label name = new Label(customer.getName());
		Label address = new Label(customer.getAddress());
		Label cpr = new Label(customer.getCPR());

		Button createLoan = new Button("Opret Loan");

		createLoan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (customer.hasActiveOffer()) {
					initPopUp("kunden har allerede et aktivt lånetilbud", "-fx-text-fill: red; -fx-font-weight: bold");
					return;
				}

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

	private Scene initCreateLoan(Stage stage) {

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setHgap(10);
		grid.setVgap(10);

		Label prompt = new Label("Opret nyt lånetilbud");

		ComboBox<Car> cars = new ComboBox<Car>();

		Label priceLabel = new Label("Pris:");
		Label price = new Label();
		Label downPaymentLabel = new Label("Udbetaling:");
		TextField downPayment = new TextField();
		Label noOfMonthsLabel = new Label("Antal måneder:");
		TextField noOfMonths = new TextField();

		Button calc = new Button("Beregn");

		Button back = new Button("Tilbage");
		GridPane.setHalignment(back, HPos.RIGHT);
		Label creditLabel = new Label("Kreditværdighed");
		creditTF = new TextField();
		creditTF.setEditable(false);

		Label rate = new Label("Nuværende rente");
		rateTF = new TextField();
		rateTF.setEditable(false);

		cars.getItems().addAll(controller.getAllCars());
		cars.setPromptText("Vælg bil");
		cars.valueProperty().addListener(new ChangeListener<Car>() {
			@Override
			public void changed(ObservableValue<? extends Car> arg0, Car previous, Car chosen) {
				chosenCar = chosen;
				price.setText(chosenCar.getPrice() + "");
			}
		});

		calc.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				checkNumberField(downPayment, "Udbetaling");
				checkNumberField(noOfMonths, "Antal måneder");

				if (cars.getValue() == null) {
					initPopUp("vælg venligst en bil", "");
					return;
				}

				try {
					double interestRate = controller.calculateInterestRate(customer.getRating(),
							Double.parseDouble(rateTF.getText()), Integer.parseInt(downPayment.getText()),
							Integer.parseInt(noOfMonths.getText()), chosenCar.getPrice());

					double monthlyRate = controller.calculateMonthlyRate(interestRate);

					double repayments = controller.calculateRepayments(chosenCar,
							Integer.parseInt(downPayment.getText()), monthlyRate,
							Integer.parseInt(noOfMonths.getText()));

					loanOffer = new LoanOffer(interestRate, Integer.parseInt(downPayment.getText()), repayments,
							Integer.parseInt(noOfMonths.getText()), customer, chosenCar, salesman);

					stage.setScene(initConfirmLoan(stage));

				} catch (PoorCreditRatingException e) {
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

		grid.add(calc, 2, 7);

		grid.add(back, 3, 0);
		grid.add(creditLabel, 3, 3);
		grid.add(creditTF, 3, 4);
		grid.add(rate, 3, 5);
		grid.add(rateTF, 3, 6);

		return new Scene(grid);
	}

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
				if (chosenCar.getPrice() < salesman.getLoanLimit())
					loanOffer.setApproved(true);

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

		// System.out.println(controller.getUnapprovedLoans());

		for (LoanOffer lo : offers) {
			HBox details = new HBox();
			details.setSpacing(20);
			Label modelName = new Label("Model: " + lo.getCar().getModel());
			Label price = new Label("Pris: " + lo.getCar().getPrice());
			Button pick = new Button("Se detaljer");

			pick.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (approved) {
						stage.setScene(initPrintLoan(stage, lo));
					} else
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
	 * @Param popUpMessage:
	 * 			Message to display in the pop up window
	 * @Param css:
	 * 			String to set styling on the window - if no styling needed, pass empty String
	 */
	private void initPopUp(String popUpMessage, String css) {

		GridPane grid = new GridPane();
		Label lb = new Label(popUpMessage);

		if (!css.equals(""))
			lb.setStyle(css);

		grid.add(lb, 0, 0);
		gridPaddingSpacing(grid, 10, "");
		Stage popUp = new Stage();
		popUp.setScene(new Scene(grid));

		popUp.show();
	}

	private GridPane initCustomerDetailsGrid(Customer customer) {
		GridPane customerGrid = new GridPane();
		customerGrid.add(new Label("Kunde"), 0, 0);
		customerGrid.add(new Label("navn:"), 0, 1);

		Label customerName = new Label(customer.getName());
		customerName.setMinSize(200, 30);
		customerGrid.add(customerName, 1, 1);

		customerGrid.add(new Label("tlf"), 0, 2);
		customerGrid.add(new Label("" + customer.getPhone()), 1, 2);
		gridPaddingSpacing(customerGrid, 10, "-fx-background-color: #c4c4c4;");

		return customerGrid;
	}

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

	private GridPane initSalesmanDetailsGrid(Salesman salesman) {
		GridPane salesmanGrid = new GridPane();
		salesmanGrid.add(new Label("Sælger"), 0, 0);
		salesmanGrid.add(new Label("Navn:"), 0, 1);
		salesmanGrid.add(new Label(salesman.getName()), 1, 1);
		gridPaddingSpacing(salesmanGrid, 10, " -fx-background-color: #c4c4c4;");

		return salesmanGrid;
	}

	private GridPane initLoanDetailsGrid(LoanOffer loanOffer) {
		GridPane detailsGrid = new GridPane();
		gridPaddingSpacing(detailsGrid, 10, " -fx-background-color: #c4c4c4;");
		detailsGrid.add(new Label("Detaljer"), 0, 0);
		detailsGrid.add(new Label("Udbetaling:"), 0, 1);
		detailsGrid.add(new Label(loanOffer.getDownPayment() + " kr."), 1, 1);
		detailsGrid.add(new Label("Antal ydelser:"), 0, 2);
		detailsGrid.add(new Label("" + loanOffer.getNumberOfMonths()), 1, 2);
		detailsGrid.add(new Label("Afdrag:"), 0, 3);

		// formatér feltet med afdrag så det kun viser 2 decimaler
		DecimalFormat formatter = new DecimalFormat("##.##");
		formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));

		detailsGrid.add(new Label(formatter.format(loanOffer.getRepayments()) + " kr."), 1, 3);
		detailsGrid.add(new Label("ÅOP:"), 0, 4);
		detailsGrid.add(new Label("" + loanOffer.getAnnualCost()), 1, 4);

		return detailsGrid;
	}

	private void gridPaddingSpacing(GridPane grid, int insets, String css) {
		grid.setPadding(new Insets(insets, insets, insets, insets));
		grid.setHgap(insets);
		grid.setVgap(insets);

		if (!css.isEmpty())
			grid.setStyle(css);

	}

	private void checkNumberField(TextField tf, String tfName) {

		char[] array = tf.getText().toCharArray();

		if (array.length == 0) {
			initPopUp("Indtast venligst et telefonnummer", "");
			return;
		}

		for (char ch : array) {
			if (!(ch >= '0' && ch <= '9')) {
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
			// formaterer renten til 2 decimaler
			DecimalFormat formatter = new DecimalFormat("##.##");
			formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
			rateTF.setText(formatter.format(rate));

		} else {
			creditTF.setText("" + customer.getRating());
			// hvis kreditvurderingen er for lav, giv en advarsel til bruger
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
