
//javaFX imports
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;

//other imports
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Scanner;

/**
 * JavaFX GUI Application for PDF Creation
 *
 * @author Dawson Pent
 * @version 0.2
 */
public class App extends Application {

    //class variables
    //primitive variable for size of objects and text
    private final String filepath = "./src/main/resources/";
    private int initPDF;
    //javafx variables
    private Stage stage; //main screen's stage
    private Font normalFont;
    private Font titleFont;
    private final ColorAdjust dark = new ColorAdjust();
    private final ColorAdjust normal = new ColorAdjust();
    //default file directory for PDF
    private File outputDirectory = new File(filepath + "OutputForms/");



    //-Declaring application start
    /**
     * Starting method to instantiate the stage and scenes.
     *
     * @param primaryStage stage to display the music app
     */
    @Override
    public void start(Stage primaryStage) {
        //saving the application's main stage
        stage = primaryStage;

        //updating settings
        getSettings();

        //setting scene as main menu
        stage.setScene(mainMenuCreator());

        //instantiating other variables
        dark.setBrightness(-.6);
        normal.setBrightness(1);

        //instantiating stage
        stage.setResizable(true);
        stage.setTitle("PDF Creator");
        stage.setHeight(700);
        stage.setWidth(800);
        stage.show();
    }
    //maing method to start the application
    public static void main(String[] args) {
        //launching the application
        launch(args);
    }



    //-Declaring application scene creators
    /**
     * Scene creator for the main menu.
     *
     * @return main menu Scene
     */
    private Scene mainMenuCreator() {
        //-creating main menu logo
        //creating Coweta and Fayette images
        Image CowetaImage = new Image((new File(filepath + "Files/CowetaHearing.jpg")).getAbsolutePath());
        ImageView Coweta = new ImageView(CowetaImage);
        Coweta.setPreserveRatio(true);
        Coweta.setFitWidth(.3 * stage.getWidth());
        Image FayetteImage = new Image((new File(filepath + "Files/CowetaHearing.jpg")).getAbsolutePath());
        ImageView Fayette = new ImageView(FayetteImage);
        Fayette.setPreserveRatio(true);
        Fayette.setFitWidth(.3 * stage.getWidth());
        //creating title
        Label title = new Label("PDF Creator");
        title.setFont(titleFont);
        //creating HBox to hold all menu logo items
        HBox header = new HBox(.7 * stage.getWidth());
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(Coweta, title, Fayette);

        //-creating main menu buttons
        //new PDF button
        Button newPDFButton = new Button("New PDF");
        newPDFButton.setScaleX(.15 * stage.getWidth());
        newPDFButton.setScaleY(.15 * stage.getHeight());
        newPDFButton.setFont(normalFont);
        newPDFButton.setOnAction(e -> {
            if (initPDF != -1) {
                stage.setScene(FormCreator(initPDF));
            } else {
                stage.setScene(newPDFCreator());
            }
        });

        //exit button
        Button exitButton = new Button("Exit");
        exitButton.setMinWidth(.15 * stage.getWidth());
        exitButton.setMinHeight(.05 * stage.getHeight());
        exitButton.setFont(normalFont);
        exitButton.setOnAction(e -> stage.close());

        //setting up menu VBox
        VBox menu = new VBox(.3 * stage.getHeight());
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(header, newPDFButton, exitButton);

        //creating Scene itself to return
        return new Scene(menu);
    }

    /**
     * Scene creator for the PDF creator selection screen.
     *
     * @return Scene displaying PDF options
     */
    private Scene newPDFCreator() {
        //title label
        Label title = new Label("Select PDF");
        title.setFont(titleFont);

        //-button grid
        GridPane buttonPane = new GridPane();
        buttonPane.setMaxWidth(.8 * stage.getWidth());

        //choosing POA Form
        Button POAFormButton = new Button("POA Form");
        POAFormButton.setMinWidth(.15 * stage.getWidth());
        POAFormButton.setMinHeight(.05 * stage.getHeight());
        POAFormButton.setFont(normalFont);

        //adding buttons to gridPane
        buttonPane.getChildren().add(POAFormButton);

        //check box for auto selecting
        HBox autoSelectBox = new HBox(10);
        autoSelectBox.setAlignment(Pos.CENTER);
        final CheckBox autoSelect = new CheckBox();
        autoSelect.setMinWidth(.05 * stage.getWidth());
        autoSelect.setMinHeight(.05 * stage.getHeight());
        autoSelect.setSelected(initPDF != -1);
        Label label = new Label("Automatically select PDF in the future");
        label.setFont(normalFont);
        autoSelectBox.getChildren().addAll(autoSelect, label);

        //POA Form button
        POAFormButton.setOnAction(E -> {
            stage.setScene(FormCreator(0));
            setSettings("initPDF", (autoSelect.isSelected()) ? "0" : "-1");
            if (autoSelect.isSelected()) {
                initPDF = 0;
            } else {
                initPDF = -1;
            }
        });

        //back button
        Button backButton = new Button("Back");
        backButton.setMinWidth(.15 * stage.getWidth());
        backButton.setMinHeight(.05 * stage.getHeight());
        backButton.setFont(normalFont);
        backButton.setOnAction(E -> stage.setScene(mainMenuCreator()));

        //overall scene VBox
        VBox formSelect = new VBox(.8 * stage.getHeight());
        formSelect.getChildren().addAll(title, buttonPane, autoSelectBox, backButton);
        formSelect.setAlignment(Pos.CENTER);

        //creating Scene to return
        return new Scene(formSelect);
    }

    /**
     * Method to create a VBox holding all the direct form fields to create.
     *
     * @param elements  2D String array for list of elements
     * @return          VBox consisting of form with fields
     */
    private VBox FormVBoxCreator(final String[][] elements, final String[][] outputs, final String FileName) {
        //creating VBox to return
        VBox form = new VBox(20);
        form.setMaxHeight(.6 * stage.getHeight());
        form.setMaxWidth(.8 * stage.getWidth());

        //creating VBox for inputs
        final VBox inputForm = new VBox(10);

        //creating a VBox for outputs
        final VBox outputDisplay = outputDisplayCreator(outputs);

        //creating input nodes for the form
        for (String[] element : elements) {
            //-creating different input based on defined type
            //creating the label for the field
            Label title = new Label(element[0]);
            title.setFont(normalFont);

            //creating necessary objects
            HBox input = new HBox(10); //HBox to hold
            input.getChildren().add(title);

            //creating the specific field for input based on formats
            String type = element[2];
            if (type.equalsIgnoreCase("Text")) {
                //if the type is text, create a text field
                TextField field = new TextField();
                field.setMaxWidth(250);
                field.setMaxHeight(25);
                field.setFont(normalFont);

                //updating outputs when input is changed
                field.setOnKeyReleased(e -> {
                    //updating outputs
                    updateValues(inputForm, outputDisplay, outputs);
                });

                //adding the field to the specific input HBox
                input.getChildren().add(field);
            } else if (type.equalsIgnoreCase("Dropdown")) {
                //if the type is a combobox/dropdown, create it and add every item in format
                ComboBox<String> field = new ComboBox<String>();
                field.setMaxWidth(250);
                field.setMaxHeight(25);
                for (int i = 3; i < element.length; i++) {
                    //adding every item on the format
                    field.getItems().add(element[i]);
                }

                //updating outputs when input is changed
                field.setOnAction(e -> {
                    //updating outputs
                    updateValues(inputForm, outputDisplay, outputs);
                });

                //adding the field to the specific input HBox
                input.getChildren().add(field);
            } else if (type.equalsIgnoreCase("Number")) {
                //if the type is number, create a text field
                final TextField field = new TextField();
                field.setMaxWidth(250);
                field.setMaxHeight(25);
                field.setFont(normalFont);
                field.setAccessibleHelp("Number Field");

                //updating outputs when input is changed
                field.setOnKeyReleased(e -> {
                    String inputText = field.getText();
                    for (int i = 0; i < inputText.length(); i++) {
                        if (!Character.isDigit(inputText.charAt(i)) && inputText.charAt(i) != '.') {
                            field.undo();
                            formError("Non-number was entered into a number form");
                        }
                    }

                    //updating outputs
                    updateValues(inputForm, outputDisplay, outputs);
                });

                //adding the field to the specific input HBox
                input.getChildren().add(field);
            } else if (type.equalsIgnoreCase("Percent")) {
                //if the type is percent, create a slider with a display label next to it
                final Slider field = new Slider(0, 100, 20);
                field.setMaxWidth(150);
                field.setMaxHeight(25);
                field.setMajorTickUnit(.1);
                field.setMinorTickCount(0);
                final Label display = new Label("20.0%");
                display.setFont(normalFont);

                //whenever the slider is changed, change the display value
                field.valueProperty().addListener((observable, oldValue, newValue) -> {
                    //seeing if an arrow key was pressed
                    double diff = ((Double) oldValue) - ((Double) newValue);
                    if (Math.abs(diff) == 10) {
                        //setting the value to only change by .1 when arrow key is used
                        field.setValue( (diff < 0) ? (Double) oldValue + .1 : (Double) oldValue - .1 );
                    } else {
                        //setting the display value to the new slider value
                        double val = (Double) newValue;
                        display.setText( ( (double) ( (int) (val * 10) ) )/10 + "%");
                    }

                    //updating outputs
                    updateValues(inputForm, outputDisplay, outputs);
                });

                //adding the field to the specific input HBox
                input.getChildren().addAll(field, display);
            }

            //adding the next input HBox to the form
            inputForm.getChildren().add(input);
        }

        //creating a HBox for inputs on left and outputs on right
        HBox ioDisplay = new HBox(40);
        ioDisplay.getChildren().addAll(inputForm, outputDisplay);

        //back button
        Button backButton = new Button("Back");
        backButton.setMinWidth(.15 * stage.getWidth());
        backButton.setMinHeight(.05 * stage.getHeight());
        backButton.setFont(normalFont);
        backButton.setOnAction(E -> {
            if (initPDF < 0) {
                stage.setScene(newPDFCreator());
            } else {
                stage.setScene(mainMenuCreator());
            }
        });

        //creating a submission button
        Button submit = new Button("Submit");
        submit.setFont(normalFont);
        submit.setOnAction(E ->
                pdfCreation(FileName, elements, outputs, inputForm, outputDisplay)
        );

        //creating the directory chooser
        final DirectoryChooser directoryChoice = new DirectoryChooser();
        //if the filepath exists and is a directory, choose it.  If not, create it.
        if (outputDirectory.isDirectory()) {
            directoryChoice.setInitialDirectory(outputDirectory);
        } else {
            directoryChoice.setInitialDirectory(new File(filepath));
        }
        //creating the button to open the directory chooser
        Button directoryChoiceButton = new Button("New Directory");
        directoryChoiceButton.setMaxWidth(150);
        directoryChoiceButton.setFont(normalFont);
        //reacting to the choice and displaying it with a label
        String temp = directoryChoice.getInitialDirectory().getPath();
        String labelText = (temp.length() > 20) ? "... " + temp.substring(temp.length() - 20) : temp;
        final Label directoryLabel = new Label(labelText);
        directoryLabel.setFont(normalFont);
        directoryLabel.setMaxWidth(250);
        //setting up button action
        directoryChoiceButton.setOnAction(e -> {
            //getting new File directory and resetting the initial directory of the directory chooser
            File newFile = directoryChoice.showDialog(stage);

            //ensuring a directory was chosen
            if (newFile != null) {
                outputDirectory = newFile;
                directoryChoice.setInitialDirectory(outputDirectory);

                //setting the new directory in the settings
                setSettings("Directory", outputDirectory.getPath());

                //resetting label
                String temp1 = outputDirectory.getPath();
                String labelText1 = (temp1.length() > 20) ? "... " + temp1.substring(temp1.length() - 20) : temp1;
                directoryLabel.setText(labelText1);
            }
        });


        //creating a submission HBox to hold back button, submit button, and file chooser
        HBox submitBox = new HBox(20);
        submitBox.setAlignment(Pos.CENTER);
        submitBox.getChildren().addAll(backButton, submit, directoryChoiceButton, directoryLabel);

        form.getChildren().addAll(ioDisplay, submitBox);

        //updating output values to display possible values
        updateValues(inputForm, outputDisplay, outputs);

        //returning created VBox
        return form;
    }
    /**
     * Method to create the pdf when the submit button is hit.
     *
     * @param FileName The file name of the pdf being created
     * @param elements The elements of the form to be filled in
     * @param outputs The outputs of the form calculations
     * @param inputForm The vbox where the inputs are stored
     * @param outputDisplay The box where the outputs are displayed
     */
    private void pdfCreation(String FileName, String[][] elements, String[][] outputs, VBox inputForm, VBox outputDisplay) {
        //creating a boolean to check for success
        boolean success = true;

        //-creating the PDF based on inputs
        //gathering necessary information
        //getting the form source filepath
        String src = filepath + "BaseForms/" + FileName;
        String dest = outputDirectory.getAbsolutePath();
        if (!(new File(dest)).isDirectory()) {
            //if the destination is a file instead of a directory, throw an error
            createPDFError("Given destination is a file, not a directory");
            success = false;
        }
        //-gathering values
        String[][] values = new String[elements.length + outputs.length][]; //values 2D array
        TextInputDialog textDialog = new TextInputDialog("");
        textDialog.setTitle("File Name");
        textDialog.setHeaderText("Enter PDF File Name");
        textDialog.showAndWait();
        textDialog.close();
        String filename = textDialog.getResult();
        if (filename == null) {
            success = false;
        } else if ((new File(filename)).exists()) {
            //throwing an error if a file already exists with that name
            createPDFError("File already exists with that name.");
            success = false;
        }
        int i = 0; //value index
        ListIterator<Node> iterator = inputForm.getChildren().listIterator();
        //gathering inputs
        if (success)
            for (String[] element : elements) {
                //getting the next control element in the input form
                Control next = (Control) ( (HBox) iterator.next() ).getChildren().get(1);
                String value;

                //Depending on the control element's type, grab a different result
                if (next.getClass() == TextField.class) {
                    //creating a temporary variable to hold the next TextField object
                    TextField nextText = (TextField) next;
                    //if the TextField isn't empty or null, see if it is a Number Field or not
                    value = nextText.getText();
                } else if (next.getClass() == ComboBox.class) {
                    //creating a temporary variable to hold the next ComboBox object
                    ComboBox<String> nextCombo = (ComboBox) next;
                    value = nextCombo.getValue(); //grabbing the chosen ComboBox value
                } else if (next.getClass() == Slider.class) {
                    //creating a temporary variable to hold the next Slider object
                    Slider nextSlider = (Slider) next;
                    //setting the current input to the slider's value
                    value = nextSlider.getValue() + "";
                } else {
                    //if the element in the form is unknown, throw an error
                    System.out.println("Error");
                    formError("Invalid input type.");
                    success = false;
                    break;
                }

                //setting the next item in the values 2D array as the element ID in front and desired value in back
                values[i++] = new String[] {element[1], value};
            }
        //adding outputs to the values array
        iterator = outputDisplay.getChildren().listIterator();
        for (String[] output : outputs) {
            values[i++] = new String[] {output[1], ((Label) ((StackPane) ((VBox) iterator.next()).getChildren().get(1)).getChildren().get(1)).getText()};
        }

        //if the values were grabbed correctly, create the PDF
        if (success) {
            //filling in the PDF with the gathered information
            Message message = new Message(false);
            new Filler(dest + "/" + filename +
                    ((filename.substring(filename.length()-3).equals(".pdf")) ? "" : ".pdf"),
                    src, message, values
            );

            //ensuring PDF production was a success
            if (message.hasMessage()) {
                //if we got an error message back, throw the error message out
                createPDFError(message.getMessage());
            } else {
                //setting stage to PDF display
                stage.setScene(mainMenuCreator());
            }
        }
    }
    /**
     * Updating the output display's values.
     *
     * @param inputForm     VBox reference to the input portion of the form
     * @param outputForm    VBox reference to the output portion of the form
     * @param outputs       2D String array from the form format
     */
    private void updateValues(VBox inputForm, VBox outputForm, String[][] outputs) {
        //creating a String array
        Object[] inputs = new Object[inputForm.getChildren().size()];

        //-Getting all the values given so far
        //creating an iterator to iterate over the children in the Input Form
        ListIterator<Node> iterator = inputForm.getChildren().listIterator();
        if (iterator.hasNext())
            for (int i = 0; iterator.hasNext(); i++) {
                //getting the next control element in the input form
                Control next = (Control) ( (HBox) iterator.next() ).getChildren().get(1);

                //Depending on the control element's type, grab a different result
                if (next.getClass() == TextField.class) {
                    //creating a temporary variable to hold the next TextField object
                    TextField nextText = (TextField) next;
                    //if the TextField isn't empty or null, see if it is a Number Field or not
                    if (nextText.getText() != null && !Objects.equals(nextText.getText(), "")) {
                        //if the accessibility help isn't null, it is the number field
                        if (nextText.getAccessibleHelp() != null) {
                            //number field, grab a Double
                            if (nextText.getText().length() == 0) {
                                inputs[i] = null;
                                continue;
                            }
                            if (nextText.getText() != null && !Objects.equals(nextText.getText(), "") && nextText.getText().charAt(0) != '.') {
                                inputs[i] = Double.valueOf(nextText.getText());
                            }
                        } else {
                            //if there is no accessible help, it is a text field
                            //therefore, you grab the String
                            if (nextText.getText().length() == 0) {
                                inputs[i] = null;
                            } else {
                                inputs[i] = nextText.getText();
                            }
                        }
                    } else {
                        //otherwise if text is null or empty, set input to null
                        inputs[i] = null;
                    }
                } else if (next.getClass() == ComboBox.class) {
                    //creating a temporary variable to hold the next ComboBox object
                    ComboBox<String> nextCombo = (ComboBox) next;
                    String value = nextCombo.getValue(); //grabbing the chosen ComboBox value
                    //if the value is null or empty, set the input to null
                    if (value != null && !value.equals("")) {
                        //otherwise, set the input to the found value
                        inputs[i] = value;
                        continue;
                    } else {
                        //setting input to null if above if is false
                        inputs[i] = null;
                    }
                } else if (next.getClass() == Slider.class) {
                    //creating a temporary variable to hold the next Slider object
                    Slider nextSlider = (Slider) next;
                    //setting the current input to the slider's value
                    inputs[i] = nextSlider.getValue() / 100;
                } else {
                    //if the element in the form is unknown, throw an error
                    formError("Invalid input type.");
                }
            }

        //calculating possible outputs based on given inputs
        //creating an array of doubles to hold outputs
        double[] outputValues = new double[outputs.length];
        //initiating all output values as -1
        Arrays.fill(outputValues, -1);
        //looping through all desired outputs
        for (int i = 0; i < outputs.length; i++) {
            //getting the next String array
            String[] next = outputs[i];

            //getting the first value to manipulate
            double outputNumber = getFormValue(next[2], inputs, outputValues);

            //if this is the only number necessary, set it as the output value
            if (next.length == 3) {
                outputValues[i] = outputNumber;
            } else {
                //variable to see if the operation should continue
                boolean continueOperation = true;
                char operation = next[3].charAt(0);
                int z = 4;

                //if a new operation character is entered, reset the operation variable
                while (continueOperation) {
                    //doing the action specified by the operation character
                    switch(operation) {
                        case '+':
                            continueOperation = false; //instantiating continue to false in case the operation isn't changed
                            //adding all values mentioned to the output Number
                            for (int j = z; j < next.length; j++) {
                                //checking if the next String is an operator
                                if (next[j].length() == 1) {
                                    //change the operation value
                                    operation = next[j].charAt(0);
                                    continueOperation = true;
                                    z = ++j;
                                    break;
                                }

                                //if the number is negative, break from the loop to keep it negative to show the number is impossible
                                if (outputNumber < 0) break;

                                //performing the operation with the next character
                                double formVal = getFormValue(next[j], inputs, outputValues);
                                //if next formVal is negative, set outputNumber to negative
                                outputNumber = (formVal < 0) ? -1 : outputNumber + formVal;
                            }
                            break;
                        case '-':
                            continueOperation = false; //instantiating continue to false in case the operation isn't changed
                            //adding all values mentioned to the output Number
                            for (int j = z; j < next.length; j++) {
                                //checking if the next String is an operator
                                if (next[j].length() == 1) {
                                    //change the operation value
                                    operation = next[j].charAt(0);
                                    continueOperation = true;
                                    z = ++j;
                                    break;
                                }

                                //if the number is negative, break from the loop to keep it negative to show the number is impossible
                                if (outputNumber < 0) break;

                                //performing the operation with the next character
                                double formVal = getFormValue(next[j], inputs, outputValues);
                                //if next formVal is negative, set outputNumber to negative
                                outputNumber = (formVal < 0) ? -1 : outputNumber - formVal;
                            }
                            break;
                        case '*':
                            continueOperation = false; //instantiating continue to false in case the operation isn't changed
                            //adding all values mentioned to the output Number
                            for (int j = z; j < next.length; j++) {
                                //checking if the next String is an operator
                                if (next[j].length() == 1) {
                                    //change the operation value
                                    operation = next[j].charAt(0);
                                    continueOperation = true;
                                    z = ++j;
                                    break;
                                }

                                //if the number is negative, break from the loop to keep it negative to show the number is impossible
                                if (outputNumber < 0) break;

                                //performing the operation with the next character
                                double formVal = getFormValue(next[j], inputs, outputValues);
                                //if next formVal is negative, set outputNumber to negative
                                outputNumber = (formVal < 0) ? -1 : outputNumber * formVal;
                            }
                            break;
                        case '/':
                            continueOperation = false; //instantiating continue to false in case the operation isn't changed
                            //adding all values mentioned to the output Number
                            for (int j = z; j < next.length; j++) {
                                //checking if the next String is an operator
                                if (next[j].length() == 1) {
                                    //change the operation value
                                    operation = next[j].charAt(0);
                                    continueOperation = true;
                                    z = ++j;
                                    break;
                                }

                                //if the number is negative, break from the loop to keep it negative to show the number is impossible
                                if (outputNumber < 0) break;

                                //performing the operation with the next character
                                double formVal = getFormValue(next[j], inputs, outputValues);
                                //if next formVal is negative, set outputNumber to negative
                                outputNumber = (formVal < 0) ? -1 : outputNumber / formVal;
                            }
                            break;
                    }
                }

                //setting the output number to the corresponding position on the outputValues array
                //if the value is negative, set the output to be -1 because that means atleast 1 value can't be created yet
                outputValues[i] = (outputNumber < 0) ? -1 : outputNumber;
            }
        }

        //updating output display text values
        //declaring an iterator to iterate over the children in the output display
        iterator = outputForm.getChildren().listIterator();
        for (int i = 0; iterator.hasNext(); i++) {
            //grabbing each individual VBox for each output item
            VBox next = (VBox) iterator.next();

            //grabbing the Stack Pane in the VBox
            StackPane pane = (StackPane) next.getChildren().get(1);

            //grabbing the number display and changing its text to the new number values calculated
            Label display = (Label) pane.getChildren().get(1);
            display.setText( (outputValues[i] < 0) ? "" : String.format("%.2f", outputValues[i]) );
        }
    }
    /**
     * Getting a desired value from the form based on a desired value String.
     *
     * @param desiredValue  String in the form of 'I' or 'O' for input/output then the number on the FormFormats from 0 to etc
     * @param inputs        Object array from the form inputs
     * @param outputs       double array for outputs calculated so far
     * @return value of the form value at a certain index
     */
    private double getFormValue(String desiredValue, Object[] inputs, double[] outputs) {
        //if the desired value starts with an I, it is an input
        //getting the desired number
        int num = Integer.parseInt("" + desiredValue.charAt(1));
        if (desiredValue.charAt(0) == 'I') {
            //checking if the desired input is filled out
            if (inputs[num] != null) {
                //returning the desired input number
                return (Double) inputs[num];
            }
        } else {
            //checking if the desired output is filled out
            if (outputs[num] != -1) {
                //returning the desired output number
                return outputs[num];
            }
        }

        //if the value hasn't been returned yet, return -1 for value not found
        return -1;
    }
    /**
     * Given a 2D array of Strings, produces a display of outputs.
     *
     * @param outputs   2D String array (empty outputs should be set as anything but a numerical value
     * @return          VBox with labels and a gray box containing values
     */
    private VBox outputDisplayCreator(String[][] outputs) {
        //creating the output VBox to return
        VBox outputVBox = new VBox(10);

        for (String[] output : outputs) {
            //creating the output HBox for the specific output
            VBox outputHBox = new VBox(1);

            //creating the output's label
            Label label = new Label(output[0]);
            label.setFont(normalFont);

            //creating the output box
            StackPane outputBox = new StackPane();
            //creating items for output box
            Rectangle box = new Rectangle(); //background box
            box.setWidth(250);
            box.setHeight(25);
            box.setFill(Color.DIMGRAY);
            box.setStroke(Color.FLORALWHITE);
            Label text = new Label(); //text displaying output value
            text.setFont(normalFont);
            text.setTextFill(Color.WHITE);
            try {
                //if the next value is a Double, set the text to it
                double val = Double.parseDouble(output[2]);
                text.setText(String.format("%.2f", val));
            } catch(Exception e) {
                //otherwise, do nothing
            }
            //adding the items to the output box
            outputBox.getChildren().addAll(box, text);

            //adding the output label and box to the HBox
            outputHBox.getChildren().addAll(label, outputBox);

            //adding the HBox to the overall VBox
            outputVBox.getChildren().add(outputHBox);
        }

        //returning the VBox
        return outputVBox;
    }
    /**
     * Overall Form Scene creator dependent on the scene selected in the New PDF Creator Scene.
     *
     * @param formNum   which form has been selected to create
     * @return          the Form Scene
     */
    private Scene FormCreator(int formNum) {
        //creating the Form VBox
        VBox formVBox = new VBox(40);
        formVBox.setAlignment(Pos.CENTER);

        //getting the specified form's format
        String[][] format = FormFormats.getForm(formNum);

        //creating necessary components
        //creating the title
        Label formTitle = new Label(format[0][0]);
        formTitle.setFont(titleFont);
        //-creating form inputs VBox
        //gathering form elements
        int formLength = Integer.parseInt(format[1][0]);
        String[][] formElements = new String[formLength][];
        System.arraycopy(format, 2, formElements, 0, formLength);

        //gathering form outputs
        int formNumOutputs = Integer.parseInt(format[2 + formLength][0]);
        String[][] formOutputs = new String[formNumOutputs][];
        for (int i = 0; i < formNumOutputs; i++) {
            formOutputs[i] = format[i + 3 + formLength];
        }
        //creating VBox
        VBox formInputs = FormVBoxCreator(formElements, formOutputs, format[3 + formLength + formNumOutputs][0]);
        //creating the back button
        Button back = new Button("< Back");
        back.setFont(normalFont);
        back.setOnAction(e -> {
            //changing current Scene back to PDF Creator selection scene
            stage.setScene(newPDFCreator());
        });

        //returning the created scene
        formVBox.getChildren().addAll(formTitle, formInputs, back);
        return new Scene(formVBox);
    }



    //-Declaring control methods
    /**
     * Method to grab the settings variables from the saved options file.
     */
    private void getSettings() {
        try {
            //going through the settings file
            File settingsFile = new File(filepath + "Files/Settings.txt");

            //if the file doesn't exist, throw a startup error
            if (!settingsFile.exists()) {
                throw new Exception("Settings File not found");
            }

            //scanning through the settings file
            Scanner scan = new Scanner(settingsFile);
            for (int i = 0; scan.hasNextLine(); i++) {
                //grabbing the next settings input
                String input = scan.nextLine().trim();
                input = input.substring(input.indexOf(';') + 1);

                //depending on the line, instantiate a value for the program
                switch (i) {
                    case 0:
                        //instantiating initial PDF creator selected
                        initPDF = Integer.parseInt(input);
                        break;
                    case 1:
                        outputDirectory = new File( (input.equals("null")) ? filepath + "OutputForms" : input );
                }
            }
            scan.close();

            //changing values based on settings
            normalFont = Font.font("Arial", FontWeight.LIGHT, (int) (15 * stage.getHeight()/700));
            titleFont = Font.font("Arial", FontWeight.BOLD, (int) (35 * stage.getHeight()/700));
        } catch (Exception e) {
            applicationError("Settings file incorrectly scanned: " + e.getMessage());
            stage.close();
        }
    }
    /**
     * Method to set the settings in the saved settings file.
     */
    private void setSettings(String setting, String change) {
        try {
            //going through the settings file
            File settingsFile = new File(filepath + "Files/Settings.txt");
            Scanner scan = new Scanner(settingsFile);

            //if the file doesn't exist, throw a startup error
            if (!settingsFile.exists()) {
                throw new Exception("Settings File not found");
            }

            //scanning through the settings file
            String[] output = new String[100];
            for (int i = 0; scan.hasNextLine(); i++) {
                //grabbing the next settings input
                String line = scan.nextLine().trim();

                //depending on the line, instantiate a value for the program
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == ';') {
                        //if the specific setting is the setting wanting to be changed, set it to the new value
                        output[i] = (line.substring(0,j).equalsIgnoreCase(setting))
                                ? setting + ";" + change //setting to new value
                                : line; //setting to old value if its the wrong setting
                    }
                }
            }
            scan.close();

            //writing to the new file
            PrintWriter newSettingsFile = new PrintWriter(settingsFile);
            for (int i = 0; output[i] != null; i++) {
                //adding line to the new settings file
                newSettingsFile.println(output[i]);
            }
            newSettingsFile.close();
        } catch (Exception e) {
            applicationError("Settings file incorrectly scanned: " + e.getMessage()
                    + "\nLocal message: " + e.getLocalizedMessage()
                    + "\nCause: " + e.getCause());
            stage.close();
        }
    }



    //-Application Error Messages
    /**
     * General method for creating an alert message displayed in front of the application for the user.
     *
     * @param msg       String main message to display under the header (specific info goes here)
     * @param header    String header message (general issue/alert)
     * @param type      AlertType type of alert to display
     * @param wait      Boolean whether to wait after displaying the alert or not
     */
    private void alert(String msg, String header, AlertType type, boolean wait) {
        Alert a = new Alert(type);
        a.setContentText(msg);
        a.setTitle("PDF Creator Alert");
        a.setHeaderText(header);
        if (wait) {
            a.showAndWait();
        } else {
            a.show();
        }
    }

    //specific error displays
    private void createPDFError(String msg) {
        alert(msg, "PDF Production Error", AlertType.ERROR, true);
    }
    private void formError(String msg) {
        alert(msg, "Form Error", AlertType.ERROR, true);
    }
    private void applicationError(String msg) {
        alert(msg, "Application Backend Error", AlertType.ERROR, true);
    }
}