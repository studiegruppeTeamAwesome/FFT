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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FFSGui extends Application implements Observer {

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
		// TODO forbindelse med db
		// customer = new Customer();
		// customer.setAdress("adresse");
		// customer.setCPR("cpr");
		// customer.setName("navn");
		// customer.setPhone(123);
		// customer.setRating(Rating.D);

		// chosenCar = new Car();
		// chosenCar.setModel("model");
		// chosenCar.setPrice(10);

		// salesman = new Salesman();
		// salesman.setName("Claus");

		stage.setScene(initStartScreen(stage));
		stage.show();

	}

	private void gridPaddingSpacingBackground(GridPane grid, int insets, Stage stage) {
		grid.setPadding(new Insets(insets, insets, insets, insets));
		grid.setHgap(insets);
		grid.setVgap(insets);
		// stage.getIcons().add(new Image("resource/ferrari-wallpaper.jpg"));

		Image img = new Image("resource/ferrari-wallpaper.jpg");
		BackgroundSize size = new BackgroundSize(1024, 640, true, true, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background background = new Background(backgroundImage);
		grid.setBackground(background);

	}

	private Scene initStartScreen(Stage stage) {

		VBox box = new VBox();
		box.setPadding(new Insets(10, 10, 10, 10));
		box.setSpacing(10);

		Button newLoan = new Button("Ny l�neaftale");
		Button approveLoan = new Button("Godkend l�neaftale");
		Button printLoan = new Button("Print l�neaftale");
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

		Button lookUp = new Button("Sl� op");
		TextField phone = new TextField();
		lookUpCustomerGrid.add(lookUp, 0, 3);
		lookUpCustomerGrid.add(phone, 0, 2);

		lookUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// TODO this is when the customer is supposed to be initialized -
				// delete the tests from start method

				customer = controller.getCustomerByPhone(Integer.parseInt(phone.getText()));
				System.out.println(customer.getPhone());
				grid.add(initCustomerInfo(stage), 0, 1);

				stage.sizeToScene();
			}
		});

		return new Scene(grid);

	}

	private GridPane initLookUpCustomer(Stage stage) {

		GridPane grid = new GridPane();
		gridPaddingSpacingBackground(grid, 10, stage);

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

		Label topLabel = new Label("Kunden eksisterer i databasen" + "\n" + "Bekr�ft kundeoplysninger");
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

		Label prompt = new Label("Opret nyt l�netilbud");

		ComboBox<Car> cars = new ComboBox<Car>();

		Label priceLabel = new Label("Pris:");
		Label price = new Label();
		Label downPaymentLabel = new Label("Udbetaling:");
		TextField downPayment = new TextField();
		Label noOfMonthsLabel = new Label("Antal m�neder:");
		TextField noOfMonths = new TextField();

		Button calc = new Button("Beregn");

		Button back = new Button("Tilbage");
		GridPane.setHalignment(back, HPos.RIGHT);
		Label creditLabel = new Label("Kreditv�rdighed");
		creditTF = new TextField();
		// TODO tr�d

		Label rate = new Label("Nuv�rende rente");
		rateTF = new TextField();
		// TODO tr�d

		// TODO forbindelse til database
		cars.getItems().addAll(controller.getAllCars());
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

				try {
					double interestRate = controller.calculateInterestRate(customer.getRating(),
							Double.parseDouble(rateTF.getText()), Integer.parseInt(downPayment.getText()),
							Integer.parseInt(noOfMonths.getText()), chosenCar.getPrice());
					System.out.println(interestRate);

					double monthlyRate = controller.calculateMonthlyRate(interestRate);

					double repayments = controller.calculateRepayments(chosenCar,
							Integer.parseInt(downPayment.getText()), monthlyRate,
							Integer.parseInt(noOfMonths.getText()));

					System.out.println(monthlyRate);

					loanOffer = new LoanOffer(interestRate, Integer.parseInt(downPayment.getText()), repayments,
							Integer.parseInt(noOfMonths.getText()), customer, chosenCar, salesman);

					stage.setScene(initConfirmLoan(stage));

				} catch (PoorCreditRatingException e) {
					GridPane grid2 = new GridPane();

					// TODO getMessage metoden
					Label label = new Label(e.getMessage());
					label.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
					grid2.add(label, 0, 0);
					gridPaddingSpacingBackground(grid2, 10, stage);
					grid2.setAlignment(Pos.CENTER);

					Stage stage2 = new Stage();
					stage2.setScene(new Scene(grid2, 200, 150));
					stage2.show();
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
		gridPaddingSpacing(grid, 10);
		Label prompt = new Label("Bekr�ft oplysninger");
		grid.add(prompt, 0, 0);
		grid.add(initCustomerDetailsGrid(customer), 0, 1);
		grid.add(initCarDetailsGrid(chosenCar), 0, 2);
		grid.add(initSalesmanDetailsGrid(salesman), 0, 3);
		grid.add(initLoanDetailsGrid(loanOffer), 0, 4);

		Button back = new Button("Tilbage");
		back.setAlignment(Pos.TOP_RIGHT);
		Button confirm = new Button("Bekr�ft");
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
				if (controller.saveLoanOffer(loanOffer)) {
					GridPane grid2 = new GridPane();
					grid2.add(new Label("l�n gemt"), 0, 0);
					// gridPaddingSpacingBackground(grid2, 10, stage);

					Stage stage2 = new Stage();
					stage2.setScene(new Scene(grid2));
					stage.setScene(initStartScreen(stage));
					stage2.show();

				} else {
					GridPane grid2 = new GridPane();
					grid2.add(new Label("l�n ikke gemt!"), 0, 0);
					// gridPaddingSpacingBackground(grid2, 10, stage);

					Stage stage2 = new Stage();
					stage2.setScene(new Scene(grid2));
					stage2.show();
				}
			}
		});

		return new Scene(grid);
	}

	private Scene initLoansOverview(Stage stage, boolean approved) {

		GridPane grid = new GridPane();

		Label prompt = new Label("V�lg et tilbud");
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
			Label modelName = new Label(lo.getCar().getModel());
			Label price = new Label("" + lo.getCar().getPrice());
			Button pick = new Button("Se detaljer");

			pick.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (approved) {
						stage.setScene(initPrintLoan(stage, lo));
					System.out.println(lo.getCostumer());
					}else
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
		gridPaddingSpacing(grid, 10);
		Label prompt = new Label("Godkend l�netilbud");
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
					initPopUp("l�n godkendt");
					stage.setScene(initLoansOverview(stage, false));
				} else {
					stage.setScene(initStartScreen(stage));
					initPopUp("l�n ikke godkendt!");
				}
			}
		});

		return new Scene(grid);
	}

	private Scene initPrintLoan(Stage stage, LoanOffer chosenLoanOffer) {
		
		GridPane grid = new GridPane();
		gridPaddingSpacing(grid, 10);
		Label prompt = new Label("Print l�netilbud");
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
				initPopUp("l�netilbud eksporteret til csv");
			}
		});
		
		return new Scene(grid);
	}
	
	private void initPopUp(String popUpMessage) {
		
		GridPane grid = new GridPane();
		grid.add(new Label(popUpMessage), 0, 0);
		gridPaddingSpacing(grid, 10);
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
		// gridPaddingSpacing(customerGrid, 10);
		customerGrid.setGridLinesVisible(true);

		// customerGrid.setStyle("-fx-background-color: lightgray;");
		// customerGrid.setGridLinesVisible(true);
		return customerGrid;
	}

	private GridPane initCarDetailsGrid(Car car) {
		GridPane carGrid = new GridPane();
		carGrid.add(new Label("Bil"), 0, 0);
		carGrid.add(new Label("Model:"), 0, 1);
		carGrid.add(new Label(car.getModel()), 1, 1);
		carGrid.add(new Label("Pris:"), 0, 2);
		carGrid.add(new Label("" + car.getPrice()), 1, 2);
		gridPaddingSpacing(carGrid, 10);

		return carGrid;
	}

	private GridPane initSalesmanDetailsGrid(Salesman salesman) {
		GridPane salesmanGrid = new GridPane();
		salesmanGrid.add(new Label("S�lger"), 0, 0);
		salesmanGrid.add(new Label("Navn:"), 0, 1);
		salesmanGrid.add(new Label(salesman.getName()), 1, 1);
		gridPaddingSpacing(salesmanGrid, 10);

		return salesmanGrid;
	}

	private GridPane initLoanDetailsGrid(LoanOffer loanOffer) {
		GridPane detailsGrid = new GridPane();
		detailsGrid.add(new Label("Detaljer"), 0, 0);
		detailsGrid.add(new Label("Udbetaling:"), 0, 1);
		detailsGrid.add(new Label(loanOffer.getDownPayment() + " kr."), 1, 1);
		detailsGrid.add(new Label("Antal ydelser:"), 0, 2);
		detailsGrid.add(new Label("" + loanOffer.getNumberOfMonths()), 1, 2);
		detailsGrid.add(new Label("Afdrag:"), 0, 3);
		detailsGrid.add(new Label(loanOffer.getRepayments() + " kr."), 1, 3);
		detailsGrid.add(new Label("�OP:"), 0, 4);
		detailsGrid.add(new Label("" + loanOffer.getAnnualCost()), 1, 4);
		gridPaddingSpacing(detailsGrid, 10);

		return detailsGrid;
	}

	private void gridPaddingSpacing(GridPane grid, int insets) {
		grid.setPadding(new Insets(insets, insets, insets, insets));
		grid.setHgap(insets);
		grid.setVgap(insets);
	}

	@Override
	public void update(Observable sub, Object obj) {

		if (sub instanceof BankThread) {
			double rate = (double) obj;
			// formaterer renten til 2 decimaler
			DecimalFormat formatter = new DecimalFormat("##.##");
			formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
			rateTF.setText(formatter.format(rate));
			System.out.println("rateTF set to " + rate);

		} else {
			creditTF.setText("" + customer.getRating());
			System.out.println("Rating set to " + customer.getRating());
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
