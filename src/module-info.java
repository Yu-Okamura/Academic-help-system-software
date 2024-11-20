/**
 * 
 */
/**
 * 
 */
module ProjectPhase3 {
	
	   requires java.sql;
	   requires json;
	   requires javafx.fxml;
	   requires javafx.controls;

	   exports application;  // Export the application package
	   opens application to javafx.fxml;  // Allow javafx.fxml to access it at runtime
}