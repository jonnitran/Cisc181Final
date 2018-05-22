package pkgApp.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import javafx.beans.value.*;

import pkgApp.RetirementApp;
import pkgCore.Retirement;

public class RetirementController implements Initializable {

	private RetirementApp mainApp = null;
	@FXML
	private TextField txtSaveEachMonth;
	@FXML
	private TextField txtYearsToWork;
	@FXML
	private TextField txtAnnualReturnWorking;
	@FXML
	private TextField txtWhatYouNeedToSave;
	@FXML
	private TextField txtYearsRetired;
	@FXML
	private TextField txtAnnualReturnRetired;
	@FXML
	private TextField txtRequiredIncome;
	@FXML
	private TextField txtMonthlySSI;

	private HashMap<TextField, String> hmTextFieldRegEx = new HashMap<TextField, String>();

	public RetirementApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(RetirementApp mainApp) {
		this.mainApp = mainApp;
	}

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Adding an entry in the hashmap for each TextField control I want to validate
		// with a regular expression
		// "\\d*?" - means any decimal number
		// "\\d*(\\.\\d*)?" means any decimal, then optionally a period (.), then
		// decmial
		hmTextFieldRegEx.put(txtYearsToWork, "[1-3]?\\d|40" );
		hmTextFieldRegEx.put(txtAnnualReturnWorking, "\\d?(\\.\\d*!10(\\.0*)?");
		hmTextFieldRegEx.put(txtAnnualReturnRetired, "\\d?(\\.\\d*!10(\\.0*)?");
		hmTextFieldRegEx.put(txtYearsRetired, "1?\\d|20" );
		hmTextFieldRegEx.put(txtRequiredIncome, "[2642-10000]?\\d|10000");
		hmTextFieldRegEx.put(txtMonthlySSI, "[0-2642]?\\d|2642");
		
		Iterator it = hmTextFieldRegEx.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TextField txtField = (TextField) pair.getKey();
			String strRegEx = (String) pair.getValue();

			txtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
					// If newPropertyValue = true, then the field HAS FOCUS
					// If newPropertyValue = false, then field HAS LOST FOCUS
					if (!newPropertyValue) {
						if (!txtField.getText().matches(strRegEx)) {
							txtField.setText("");
							txtField.requestFocus();
						}
					}
				}
			});
		}
	}

	@FXML
	public void btnClear(ActionEvent event) {
		System.out.println("Clear pressed");

		// disable read-only controls
		txtSaveEachMonth.setDisable(true);
		txtWhatYouNeedToSave.setDisable(true);

		// Clear, enable txtYearsToWork
		txtYearsToWork.clear();
		txtYearsToWork.setDisable(false);
		
		txtMonthlySSI.clear();
		txtMonthlySSI.setDisable(false);
		
		txtRequiredIncome.clear();
		txtRequiredIncome.setDisable(false);
		
		txtAnnualReturnRetired.clear();
		txtAnnualReturnRetired.setDisable(false);
		
		txtYearsRetired.clear();
		txtYearsRetired.setDisable(false);
		
		txtAnnualReturnWorking.clear();
		txtAnnualReturnWorking.setDisable(false);
		
	}

	@FXML
	public void btnCalculate() {

		System.out.println("calculating");

		txtSaveEachMonth.setDisable(false);
		txtWhatYouNeedToSave.setDisable(false);

		int YearsToWork = Integer.parseInt(txtYearsToWork.getText());
		int YearsRetired = Integer.parseInt(txtYearsRetired.getText());
		int RequiredIncome = Integer.parseInt(txtRequiredIncome.getText());
		double AnnualReturnRetired = Double.parseDouble(txtAnnualReturnRetired.getText());
		double AnnualReturnWorking = Double.parseDouble(txtAnnualReturnWorking.getText());
		double MonthlySSI = Double.parseDouble(txtMonthlySSI.getText());
		
		Retirement r = new Retirement(YearsToWork, AnnualReturnWorking, YearsRetired, AnnualReturnRetired, RequiredIncome, MonthlySSI);
		
		double SaveEachMonth = r.MonthlySavings();
		double AmountToSave = r.TotalAmountToSave();
		
		Double.toString(SaveEachMonth);
		Double.toString(AmountToSave);
		
		txtSaveEachMonth.setText(String.valueOf(SaveEachMonth));
		txtSaveEachMonth.setText(String.valueOf(AmountToSave));
				
		
	}
}
