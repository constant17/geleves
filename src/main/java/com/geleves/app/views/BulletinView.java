package com.geleves.app.views;

import java.util.List;

import com.geleves.app.data.entity.Eleve;
import com.geleves.app.data.entity.Etablissement;
import com.geleves.app.data.entity.Note;
import com.geleves.app.data.service.GelevesService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.shared.Registration;

public class BulletinView extends VerticalLayout{
	private Eleve eleve;
	private List<Note> eleveNotes;
	private GelevesService service;
	private HorizontalLayout header;
	private VerticalLayout headerInfo;
	private Label schoolName;
	private Image schoolIcon;
	private Label schoolInfo;
	private Label docTitle;
	private VerticalLayout studentInfo;
	private Etablissement school;

	Label nom = new Label();
	Label prenom = new Label();
	Label matricule = new Label();
	
	Button save = new Button("Sauvegarder PDF");
	Button print = new Button("Imprimer");
	Button close = new Button("Annuler");

	public BulletinView(Etablissement school, List<Note> notes)  {
		this.school = school;
		this.eleveNotes = notes;
		this.studentInfo = new VerticalLayout();
		
	    this.setPadding(false);
	    this.add(
	    	getBulletinContent(),
	        createButtonsLayout()); 
	  }

	private HorizontalLayout setHeader() {
		
		schoolIcon = new Image(school.getLogoPath(), "Logo");
		schoolIcon.setClassName("etablisement-logo");
		
		schoolName = new Label(school.getNom());
		schoolName.setClassName("school-name");
		
		schoolInfo = new Label(getEtablissementInfo());
		
		docTitle = new Label("Bulletin de notes");
		docTitle.setClassName("bulletin-title");
		
		VerticalLayout schoolNameInfo = new VerticalLayout(schoolName,  schoolInfo);
		header = new HorizontalLayout(schoolIcon, schoolNameInfo);
		header.getStyle().set("border", "1px solid grey");
		header.setSizeFull();
		header.setHeight("10em");
		if(eleve == null)
			return header;
		HorizontalLayout infoEleve = new HorizontalLayout();
		infoEleve.add(new Label("Nom: "+eleve.getNom()));
		infoEleve.add(new Label("Prenom: "+eleve.getPrenom()));
		infoEleve.add(new Label("Matricule: "+eleve.getMatricule()));
		studentInfo.add(infoEleve);
		schoolNameInfo.add(studentInfo);
		return header;
		
	}
	
	private VerticalLayout getBulletinContent() {
		
		VerticalLayout bulletinContent = new VerticalLayout();
		bulletinContent.add(setHeader());
		if(eleve == null || eleveNotes == null)
			return bulletinContent;
		Grid<Note> noteGrid = new Grid<Note>();
		noteGrid.addColumn(Note::getMatiere).setHeader("Matieres");
		noteGrid.addColumn(Note::toString).setHeader("Notes");
		noteGrid.addColumn(Note::getCoefficientToString).setHeader("Coefficient");
		noteGrid.addColumn(Note::getTotalPoints).setHeader("Points totaux");
		//noteGrid.addColumn(Class::getNom).setHeader("Rang");
		noteGrid.addColumn(Note::getEnseignant).setHeader("Enseignant");
		noteGrid.addColumn(Note::getCommentaire).setHeader("Appreciations");
		
		noteGrid.setItems(eleveNotes);
		bulletinContent.add(noteGrid);
		bulletinContent.getStyle().set("border", "1px solid black");
		return bulletinContent;
	}
	private String getEtablissementInfo() {
		return "Adresse:"+school.getAdresse() + "  *  BP: "
				+ school.getBP() + "  *  Tel: "
				+ school.getContact() + "  *  Email: "
				+ school.getEmail() + "   ";
	}
	
	public void setEleve(Eleve eleve) {
	    this.eleve = eleve;
	}
	
	public void setNotes(List<Note> notes) {
		this.eleveNotes = notes;
		refreshBulletin();
	}
	
	private void refreshBulletin() {
		
		/*ProgressBar progressBar = new ProgressBar();
		progressBar.setIndeterminate(true);

		Div progressBarLabel = new Div();
		progressBarLabel.setText("Generating report...");

		add(progressBarLabel, progressBar);
		*/
		this.removeAll();
		this.add(
		    	getBulletinContent(),
		        createButtonsLayout());
	}
	private HorizontalLayout createButtonsLayout() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	    print.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
	    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

	    save.addClickShortcut(Key.ENTER);
	    close.addClickShortcut(Key.ESCAPE);

	    //save.addClickListener(event -> validateAndSave());
	    //delete.addClickListener(event -> fireEvent(new DeleteEvent(this, eleve)));
	    //close.addClickListener(event -> fireEvent(new CloseEvent(this)));


	    return new HorizontalLayout(save, print, close); 
	  }
	
	// Events
	  public static abstract class BulletinViewEvent extends ComponentEvent<BulletinView> {
	    private Eleve eleve;

	    protected BulletinViewEvent(BulletinView source, Eleve eleve) {
	      super(source, false);
	      this.eleve = eleve;
	    }

	    public Eleve getEleve() {
	      return eleve;
	    }
	  }

	  public static class SaveEvent extends BulletinViewEvent {
	    SaveEvent(BulletinView source, Eleve eleve) {
	      super(source, eleve);
	    }
	  }
	  
	  public static class CloseEvent extends BulletinViewEvent {
		    CloseEvent(BulletinView source) {
		      super(source, null);
		}
	  }
	  
	  public static class PrintEvent extends BulletinViewEvent {
		    PrintEvent(BulletinView source, Eleve eleve) {
		      super(source, eleve);
		}
	  }
	  
	  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
              ComponentEventListener<T> listener) {
		  return getEventBus().addListener(eventType, listener);
	}
}
