package view;



import logic.Car;
import logic.Customer;
import logic.FFSController;
import logic.FacadeController;
import logic.Salesmen;
import javafx.application.Application;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FFSGui extends Application {

	Customer customer;
	Car chosenCar;
	Salesmen salesman;
	FFSController controller = new FFSController();
	
	@Override
	public void start(Stage stage) throws Exception {
		
		// TODO forbindelse med db?
		customer = new Customer();
		customer.setAdress("adresse");
		customer.setCPR("cpr");
		customer.setName("navn");
		customer.setPhone(123);
		
		chosenCar = new Car();
		chosenCar.setName("model");
		chosenCar.setPrice(10);
		
		salesman = new Salesmen();
		salesman.setName("Claus");
		
		stage.setScene(initStartScreen(stage));
		stage.show();
		
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
		
		
		Scene scene = new Scene(box);
		
		// TODO noget med bruger og knappen der 
		
		return scene;
	}
	
	private Scene initCustomerScene(Stage stage) {
		GridPane grid = new GridPane();
		
		// mockup 1
		GridPane lookUpCustomerGrid = initLookUpCustomer(stage);
		
		grid.add(lookUpCustomerGrid, 0, 0);
		
		Button lookUp = new Button("Slå op");
		lookUpCustomerGrid.add(lookUp, 0, 3);
		lookUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				grid.add(initCustomerInfo(stage), 0, 1);
				
				stage.sizeToScene();
			}
		});
		

		return new Scene(grid);
		
	}
	
	private GridPane initLookUpCustomer(Stage stage) {
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		Label prompt1 = new Label("Angiv kundeoplysninger");
		Button back = new Button("Tilbage");
		Label prompt2 = new Label("Kundens tlf.");
		TextField phone = new TextField();
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initStartScreen(stage));
			}
		});
		
		grid.add(prompt1, 0, 0);
		grid.add(back, 1, 0);
		grid.add(prompt2, 0, 1);
		grid.add(phone, 0, 2);
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
		
		ComboBox<Car> car = new ComboBox<Car>();
		// TODO forbindelse til database
		
		Label priceLabel = new Label("Pris:");
		Label price = new Label();
		Label downPaymentLabel = new Label("Udbetaling");
		TextField downPayment = new TextField();
		Label noOfMonthsLabel = new Label("Antal måneder");
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
		
		car.getItems().addAll(controller.getAllCars());
		car.valueProperty().addListener(new ChangeListener<Car>() {
			@Override
			public void changed(ObservableValue<? extends Car> arg0, Car previous, Car chosen) {
				chosenCar = chosen;
			}
		});
		
		calc.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initConfirmLoan(stage));
			}
		});
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(initCustomerScene(stage));
			}
		});
		
		
		grid.add(prompt, 0, 0, 3, 1);
		grid.add(car, 0, 1, 2, 1);
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
		Label model = new Label(chosenCar.getName());
		Label priceLabel = new Label("Pris:");
		Label price = new Label("" + chosenCar.getPrice());
		Label salesmanLabel = new Label("Sælger");
		Label salesmanNameLabel = new Label("Navn:");
		Label salesmanName = new Label(salesman.getName());
		Label details = new Label("Detaljer");
		Label downpaymentLabel = new Label("Udbetaling:");
		Label downpayments = new Label();//TODO
		Label noOfPaymentsLabel = new Label("Antal ydelser:");
		Label noOfPayments = new Label();//TODO
		Label dateLabel = new Label("Startdato");
		Label date = new Label();//TODO
		Label repaymentLabel = new Label("Afdrag");
		Label repayment = new Label();//TODO
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
