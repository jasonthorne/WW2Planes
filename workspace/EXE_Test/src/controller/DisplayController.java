package controller;

public class DisplayController {
	
	@FXML
    private StackPane rootSP;

    @FXML
    private AnchorPane bosyAP;

    @FXML
    private JFXListView<?> eventsLV;

    @FXML
    private Label eventsLbl;

    @FXML
    void initialize() {
        assert rootSP != null : "fx:id=\"rootSP\" was not injected: check your FXML file 'display.fxml'.";
        assert bosyAP != null : "fx:id=\"bosyAP\" was not injected: check your FXML file 'display.fxml'.";
        assert eventsLV != null : "fx:id=\"eventsLV\" was not injected: check your FXML file 'display.fxml'.";
        assert eventsLbl != null : "fx:id=\"eventsLbl\" was not injected: check your FXML file 'display.fxml'.";

    }

}
