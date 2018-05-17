package view;



import logic.PoorCreditRatingException;
import logic.Car;
import logic.Customer;
import logic.FFSController;
import logic.FacadeController;
import logic.LoanOffer;
import logic.Salesman;

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

public class FFSGui extends Application {

	Customer customer;
	Car chosenCar;
	FacadeController controller = new FFSController();
	LoanOffer loanOffer;
	Salesman salesman = controller.getSalesmenByName(System.getProperty("user.name"));
	
	@Override
	public void start(Stage stage) throws Exception {
		
		// TODO forbindelse med db
//		customer = new Customer();
//		customer.setAdress("adresse");
//		customer.setCPR("cpr");
//		customer.setName("navn");
//		customer.setPhone(123);
//		customer.setRating(Rating.D);
		
		chosenCar = new Car();
		chosenCar.setModel("model");
		chosenCar.setPrice(10);
		
		salesman = new Salesman();
		salesman.setName("Claus");
		
		loanOffer = new LoanOffer();
		
		stage.setScene(initStartScreen(stage));
		stage.show();
		
	}
	
	private void gridPaddingSpacingBackground(GridPane grid, int insets, Stage stage) {
		grid.setPadding(new Insets(insets,insets,insets,insets));
		grid.setHgap(insets);
		grid.setVgap(insets);
//		stage.getIcons().add(new Image("resource/ferrari-wallpaper.jpg"));

		Image img = new Image("resource/ferrari-wallpaper.jpg");
		BackgroundSize size = new BackgroundSize(1024, 640, true, true, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background background = new Background(backgroundImage);
		grid.setBackground(background);
		
		
		
	}
	
	
	
	private Scene initStartScreen(Stage stage) {
		
		VBox box = new VBox();
		box.setPadding(new Insets(10,10,10,10));
		box.setSpacing(10);
		
		Button newLoan = new Button("Ny låneaftale");
		Button approveLoan = new Button("Godkend låneaftale");
		box.getChildren().add(newLoan);
		box.getChildren().add(approveLoan);
		
		newLoan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initCustomerScene(stage));
			}
		});
		
		approveLoan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				GridPane grid2 = new GridPane();
				grid2.add(new Label("endnu ikke implementeret"), 0, 0);
				gridPaddingSpacingBackground(grid2, 10, stage);
				
				Stage stage2 = new Stage();
				stage2.setScene(new Scene(grid2));
				stage2.show();
			}
		});
		
		Scene scene = new Scene(box); 
		
		return scene;
	}
	
	private Scene initCustomerScene(Stage stage) {
		GridPane grid = new GridPane();
		
		// mockup 1
		GridPane lookUpCustomerGrid = initLookUpCustomer(stage);
		
		grid.add(lookUpCustomerGrid, 0, 0);
		
		Button lookUp = new Button("Slå op");
		TextField phone = new TextField();
		lookUpCustomerGrid.add(lookUp, 0, 3);
		lookUpCustomerGrid.add(phone, 0, 2);
		
		
		lookUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				// TODO this is when the customer is supposed to be initialized - 
				// delete the tests from start method
				customer = controller.getCustomerByPhone(Integer.parseInt(phone.getText()));
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
		
		return grid ;
	}
	
	private GridPane initCustomerInfo(Stage stage) {
		
		GridPane grid = new GridPane();
		
		grid.setPadding(new Insets(10,10,10,10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		
		Label topLabel = new Label("Kunden eksisterer i databasen" + 
		"\n" + "Bekræft kundeoplysninger");
		Label nameLabel = new Label("Navn:");
		Label addressLabel = new Label("Adresse");
		Label cprLabel = new Label("cpr:");
		
		Label name = new Label(customer.getName());
		Label address = new Label(customer.getAdress());
		Label cpr = new Label(customer.getCPR());
		
		Button createLoan = new Button("Opret Loan");
		Label error = new Label("((Fejl))");
		
		createLoan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initCreateLoan(stage));
			}
		});
		
		grid.add(topLabel, 0, 0, 2,	1);
		grid.add(nameLabel, 0, 1);
		grid.add(addressLabel, 0, 2);
		grid.add(cprLabel, 0, 3);
		grid.add(createLoan, 0, 4);
		grid.add(name, 1, 1);
		grid.add(address, 1, 2);
		grid.add(cpr, 1, 3);
		grid.add(error, 1, 4);
		
		return grid;
	}
	
	private Scene initCreateLoan(Stage stage) {
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
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
		TextField creditTF = new TextField();
		// TODO tråd
		
		Label rate = new Label("Nuværende rente");
		TextField rateTF = new TextField();
		// TODO tråd

		// TODO forbindelse til database
		cars.getItems().addAll(controller.getAllCars());
		cars.valueProperty().addListener(new ChangeListener<Car>() {
		@Override
		public void changed(ObservableValue<? extends Car> arg0, Car previous, Car chosen) {
			chosenCar = chosen;
		}
		});
		
		calc.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					controller.calculateInterestRate(customer.getRating(), Double.parseDouble(rateTF.getText()), 
							Integer.parseInt(downPayment.getText()), Integer.parseInt(noOfMonths.getText()), chosenCar.getPrice());
					// TODO tjek if the calculation went successfully?
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
		
		
		Scene scene = new Scene(grid);
		return scene ;
	}
	
	private Scene initConfirmLoan(Stage stage) {
		
		GridPane grid = new GridPane();
		
		grid.setPadding(new Insets(10,10,10,10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		Label prompt = new Label("Bekræft oplysninger");
		Label customerLabel = new Label("Kunde");
		Label nameLabel = new Label("navn:");
		Label name = new Label(customer.getName());
		Label phoneLabel = new Label("tlf");
		Label phone = new Label("" + customer.getPhone());
		Label carLabel = new Label("Bil");
		Label modelLabel = new Label("Model:");
		Label model = new Label(chosenCar.getModel());
		Label priceLabel = new Label("Pris:");
		Label price = new Label("" + chosenCar.getPrice());
		Label salesmanLabel = new Label("Sælger");
		Label salesmanNameLabel = new Label("Navn:");
		Label salesmanName = new Label(salesman.getName());
		Label details = new Label("Detaljer");
		Label downpaymentLabel = new Label("Udbetaling:");
		Label downpayments = new Label("" + loanOffer.getDownPayment());
		Label noOfPaymentsLabel = new Label("Antal ydelser:");
		Label noOfPayments = new Label();//TODO
		Label dateLabel = new Label("Startdato:");
		Label date = new Label("" + loanOffer.getDate());
		Label repaymentLabel = new Label("Afdrag:");
		Label repayment = new Label("" + loanOffer.getRepayments());
		Label annualCostLabel = new Label("ÅOP:");
		Label annualCost = new Label();//TODO
		Button back = new Button("Tilbage");
		Button confirm = new Button("Bekræft");
		
		// TODO fix this thing
		dateLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
		
		grid.add(prompt, 0, 0, 4, 1);
		grid.add(customerLabel, 0, 1);
		grid.add(nameLabel, 0, 2);
		grid.add(carLabel, 0, 3);
		grid.add(modelLabel, 0, 4);
		grid.add(salesmanLabel, 0, 5);
		grid.add(salesmanNameLabel, 0, 6);
		grid.add(details, 0, 7);
		grid.add(downpaymentLabel, 0, 8);
		grid.add(noOfPaymentsLabel, 0, 9);
		grid.add(dateLabel, 0, 10);
		grid.add(repaymentLabel, 0, 11);
		grid.add(annualCostLabel, 0, 12);
		
		grid.add(name, 1, 2);
		grid.add(model, 1, 4);
		grid.add(salesmanName, 1, 6);
		grid.add(downpayments, 1, 8);
		grid.add(noOfPayments, 1, 9);
		grid.add(date, 1, 10);
		grid.add(repayment, 1, 11);
		grid.add(annualCost, 1, 12);
		
		grid.add(phoneLabel, 2, 2);
		grid.add(priceLabel, 2, 4);
		
		grid.add(phone, 3, 2);
		grid.add(price, 3, 4);
		
		grid.add(back, 4, 0);
		grid.add(confirm, 4, 12);
		
		
		Scene scene = new Scene(grid);
		return scene ;
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
