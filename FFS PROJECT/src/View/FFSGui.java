package View;



import Logic.Cars;
import Logic.Customer;
import Logic.Salesmen;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FFSGui extends Application {

	Customer customer;
	Cars car;
	Salesmen salesman;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		customer = new Customer();
		customer.setAdress("adresse");
		customer.setCPR("cpr");
		customer.setName("navn");
		customer.setPhone(123);
		
		car = new Cars();
		car.setName("model");
		car.setPrice(10);
		
		salesman = new Salesmen();
		salesman.setName("Claus");
		
		stage.setScene(initConfirmLoan());
		stage.show();
		
	}
	
	
	private Scene initStartScreen() {
		
		VBox box = new VBox();
		box.setPadding(new Insets(10,10,10,10));
		box.setSpacing(10);
		
		Button newLoan = new Button("Ny låneaftale");
		Button approveLoan = new Button("Godkend låneaftale");
		box.getChildren().add(newLoan);
		box.getChildren().add(approveLoan);
		
		Scene scene = new Scene(box);
		
		// TODO noget med bruger og knappen der 
		
		return scene;
	}
	
	private Scene initLookUpCustomer() {
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		Label prompt1 = new Label("Angiv kundeoplysninger");
		Button back = new Button("Tilbage");
		Label prompt2 = new Label("Kundens tlf.");
		TextField phone = new TextField();
		Button lookUp = new Button("Slå op");
		
		grid.add(prompt1, 0, 0);
		grid.add(back, 1, 0);
		grid.add(prompt2, 0, 1);
		grid.add(phone, 0, 2);
		grid.add(lookUp, 0, 3);
		
		Scene scene = new Scene(grid);
		return scene ;
	}
	
	private Scene initCustomerInfo() {
		
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
		
		grid.add(topLabel, 0, 0, 2,	1);
		grid.add(nameLabel, 0, 1);
		grid.add(addressLabel, 0, 2);
		grid.add(cprLabel, 0, 3);
		grid.add(createLoan, 0, 4);
		grid.add(name, 1, 1);
		grid.add(address, 1, 2);
		grid.add(cpr, 1, 3);
		grid.add(error, 1, 4);
		
		Scene scene = new Scene(grid);
		return scene ;
	}
	
	private Scene initCreateLoan() {
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		Label prompt = new Label("Opret nyt lånetilbud");
		
		ComboBox car = new ComboBox();
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
	
	private Scene initConfirmLoan() {
		
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
		Label model = new Label(car.getName());
		Label priceLabel = new Label("Pris:");
		Label price = new Label("" + car.getPrice());
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
