package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.edition.*;
import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.DoubleStringConverter;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateEditionController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private Spinner<Integer> pagesSpinner;

    @FXML
    private Spinner<Double> priceSpinner;

    @FXML
    private ChoiceBox<String> paperSizeChoiceBox;

    @FXML
    private ChoiceBox<String> paperTypeChoiceBox;

    @FXML
    private Label addFieldsLabel;

    @FXML
    private GridPane bookGridPane;

    @FXML
    private GridPane comicsGridPane;

    @FXML
    private GridPane newspaperGridPane;

    @FXML
    private GridPane posterGridPane;

    @FXML
    private TextField bookAuthorTextField;

    @FXML
    private TextField bookGenreTextField;

    @FXML
    private TextField bookISBNTextField;

    @FXML
    private TextField bookPublisherTextField;

    @FXML
    private TextField comicAuthorTextField;

    @FXML
    private TextField comicGenreTextField;

    @FXML
    private TextField comicsISBNTextField;

    @FXML
    private TextField comicsPublisherTextField;

    @FXML
    private TextField comicsIllustratorTextField;

    @FXML
    private DatePicker newspaperPublicationDateDatePicker;

    @FXML
    private TextField newspaperPublisherTextField;

    @FXML
    private Spinner<Double> posterWidthSpinner;

    @FXML
    private Spinner<Double> posterHeightSpinner;

    @FXML
    private TextField posterEventTextField;

    @FXML
    private Button editEditionButton;

    @FXML
    protected void onEditionTypeChange() {
        String selectedType = typeChoiceBox.getValue();

        if (selectedType == null) {
            addFieldsLabel.setVisible(false);
            return;
        }

        addFieldsLabel.setVisible(true);
        bookGridPane.setVisible(false);
        comicsGridPane.setVisible(false);
        newspaperGridPane.setVisible(false);
        posterGridPane.setVisible(false);

        switch (selectedType) {
            case "Book":
                bookGridPane.setVisible(true);
                break;

            case "Comics":
                comicsGridPane.setVisible(true);
                break;

            case "Newspaper":
                newspaperGridPane.setVisible(true);
                break;

            case "Poster":
                posterGridPane.setVisible(true);
                break;

            default:
                break;
        }
    }

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();

        if (sceneAndDataMng.getSelectedEdition() != null) {
            sceneAndDataMng.switchPane(event, "modify-edition-view.fxml");
        }
        else {
            sceneAndDataMng.switchPane(event, "list-edition-view.fxml");
        }
    }

    @FXML
    protected void onClickEditButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            if (sceneAndDataMng.getSelectedEdition() != null) {
                Edition edition = sceneAndDataMng.getSelectedEdition();
                edition.setTitle(titleTextField.getText());
                edition.setAmountOfPages(pagesSpinner.getValue());
                edition.setPrice(BigDecimal.valueOf(priceSpinner.getValue()));
                edition.setPaperSize(PaperSize.valueOf(paperSizeChoiceBox.getValue().toUpperCase()));
                edition.setPaperType(PaperType.valueOf(paperTypeChoiceBox.getValue().toUpperCase()));

                switch (edition.editionType()) {
                    case "Book":
                        Book book = (Book) edition;
                        book.setAuthor(bookAuthorTextField.getText());
                        book.setGenre(bookGenreTextField.getText());
                        book.setIsbn(bookISBNTextField.getText());
                        book.setPublisher(bookPublisherTextField.getText());
                        break;

                    case "Comics":
                        Comics comics = (Comics) edition;
                        comics.setAuthor(comicAuthorTextField.getText());
                        comics.setGenre(comicGenreTextField.getText());
                        comics.setIsbn(comicsISBNTextField.getText());
                        comics.setPublisher(comicsPublisherTextField.getText());
                        comics.setIllustrator(comicsIllustratorTextField.getText());
                        break;

                    case "Newspaper":
                        Newspaper newspaper = (Newspaper) edition;
                        LocalDate localDate = newspaperPublicationDateDatePicker.getValue();
                        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                        Date date = Date.from(instant);
                        newspaper.setPublicationDate(date);
                        newspaper.setPublisher(newspaperPublisherTextField.getText());
                        break;

                    case "Poster":
                        Poster poster = (Poster) edition;
                        poster.setWidth(posterWidthSpinner.getValue());
                        poster.setHeight(posterHeightSpinner.getValue());
                        poster.setEvent(posterEventTextField.getText());
                        break;

                    default:
                        break;
                }
                sceneAndDataMng.switchPane(event, "modify-edition-view.fxml");
            }
            else {
                switch (typeChoiceBox.getValue()) {
                    case "Book":
                        Edition book = new Book(
                                titleTextField.getText(),
                                pagesSpinner.getValue(),
                                PaperSize.valueOf(paperSizeChoiceBox.getValue().toUpperCase()),
                                PaperType.valueOf(paperTypeChoiceBox.getValue().toUpperCase()),
                                BigDecimal.valueOf(priceSpinner.getValue()),
                                bookAuthorTextField.getText(),
                                bookGenreTextField.getText(),
                                bookISBNTextField.getText(),
                                bookPublisherTextField.getText()
                        );
                        selectedPrintingHouse.addEditionToPrint(book);
                        break;

                    case "Comics":
                        Edition comics = new Comics(
                                titleTextField.getText(),
                                pagesSpinner.getValue(),
                                PaperSize.valueOf(paperSizeChoiceBox.getValue().toUpperCase()),
                                PaperType.valueOf(paperTypeChoiceBox.getValue().toUpperCase()),
                                BigDecimal.valueOf(priceSpinner.getValue()),
                                comicAuthorTextField.getText(),
                                comicGenreTextField.getText(),
                                comicsISBNTextField.getText(),
                                comicsPublisherTextField.getText(),
                                comicsIllustratorTextField.getText()
                        );
                        selectedPrintingHouse.addEditionToPrint(comics);
                        break;

                    case "Newspaper":
                        LocalDate localDate = newspaperPublicationDateDatePicker.getValue();
                        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                        Date date = Date.from(instant);

                        Edition newspaper = new Newspaper(
                                titleTextField.getText(),
                                pagesSpinner.getValue(),
                                PaperSize.valueOf(paperSizeChoiceBox.getValue().toUpperCase()),
                                PaperType.valueOf(paperTypeChoiceBox.getValue().toUpperCase()),
                                BigDecimal.valueOf(priceSpinner.getValue()),
                                date,
                                newspaperPublisherTextField.getText(),
                                new ArrayList<>()
                        );
                        selectedPrintingHouse.addEditionToPrint(newspaper);
                        break;

                    case "Poster":
                        Edition poster = new Poster(
                                titleTextField.getText(),
                                pagesSpinner.getValue(),
                                PaperSize.valueOf(paperSizeChoiceBox.getValue().toUpperCase()),
                                PaperType.valueOf(paperTypeChoiceBox.getValue().toUpperCase()),
                                BigDecimal.valueOf(priceSpinner.getValue()),
                                posterWidthSpinner.getValue(),
                                posterHeightSpinner.getValue(),
                                posterEventTextField.getText()
                        );
                        selectedPrintingHouse.addEditionToPrint(poster);
                        break;

                    default:
                        break;
                }
                sceneAndDataMng.switchPane(event, "list-edition-view.fxml");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            typeChoiceBox.getItems().addAll("Book", "Comics", "Newspaper", "Poster");
            paperSizeChoiceBox.getItems().addAll("A1", "A2", "A3", "A4", "A5");
            paperTypeChoiceBox.getItems().addAll("Glossy", "Normal", "Newsprint");

            pagesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 5));
            priceSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1000.0, 20, 0.1));
            priceSpinner.getValueFactory().setConverter(DoubleStringConverter.getConverter());

            posterWidthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1000.0, 20, 0.5));
            posterWidthSpinner.getValueFactory().setConverter(DoubleStringConverter.getConverter());
            posterHeightSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1000.0, 60, 0.5));
            posterHeightSpinner.getValueFactory().setConverter(DoubleStringConverter.getConverter());

            if (sceneAndDataMng.getSelectedEdition() != null) {
                actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Edit Edition:");
                editEditionButton.setText("Edit Edition");

                titleTextField.setText(sceneAndDataMng.getSelectedEdition().getTitle());
                typeChoiceBox.setValue(sceneAndDataMng.getSelectedEdition().editionType());
                typeChoiceBox.setDisable(true);
                pagesSpinner.getValueFactory().setValue(sceneAndDataMng.getSelectedEdition().getAmountOfPages());
                priceSpinner.getValueFactory().setValue(sceneAndDataMng.getSelectedEdition().getPrice().doubleValue());
                paperSizeChoiceBox.setValue(sceneAndDataMng.getSelectedEdition().getPaperSize().toString());
                paperTypeChoiceBox.setValue(sceneAndDataMng.getSelectedEdition().getPaperType().toString());

                switch (sceneAndDataMng.getSelectedEdition().editionType()) {
                    case "Book":
                        Book book = (Book) sceneAndDataMng.getSelectedEdition();
                        bookAuthorTextField.setText(book.getAuthor());
                        bookGenreTextField.setText(book.getGenre());
                        bookISBNTextField.setText(book.getIsbn());
                        bookPublisherTextField.setText(book.getPublisher());
                        break;

                    case "Comics":
                        Comics comics = (Comics) sceneAndDataMng.getSelectedEdition();
                        comicAuthorTextField.setText(comics.getAuthor());
                        comicGenreTextField.setText(comics.getGenre());
                        comicsISBNTextField.setText(comics.getIsbn());
                        comicsPublisherTextField.setText(comics.getPublisher());
                        comicsIllustratorTextField.setText(comics.getIllustrator());
                        break;

                    case "Newspaper":
                        Newspaper newspaper = (Newspaper) sceneAndDataMng.getSelectedEdition();
                        Instant instant = Instant.ofEpochMilli(newspaper.getPublicationDate().getTime());
                        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
                        newspaperPublicationDateDatePicker.setValue(localDate);
                        newspaperPublisherTextField.setText(newspaper.getPublisher());
                        break;

                    case "Poster":
                        Poster poster = (Poster) sceneAndDataMng.getSelectedEdition();
                        posterWidthSpinner.getValueFactory().setValue(poster.getWidth());
                        posterHeightSpinner.getValueFactory().setValue(poster.getHeight());
                        posterEventTextField.setText(poster.getEvent());
                        break;

                    default:
                        break;
                }
            }
            else {
                actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Add Edition:");
                editEditionButton.setText("Add Edition");
                typeChoiceBox.setDisable(false);

                typeChoiceBox.setValue("Book");
                paperSizeChoiceBox.setValue("A4");
                paperTypeChoiceBox.setValue("Normal");
            }

            onEditionTypeChange();
        }
    }
}
