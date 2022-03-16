package com.geleves.app.views.list;


import com.geleves.app.data.entity.Classe;
import com.geleves.app.data.entity.Cours;
import com.geleves.app.data.entity.Enseignant;
import com.geleves.app.data.entity.Niveau;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.LinkedList;
import java.util.List;

import org.vaadin.gatanaso.MultiselectComboBox;

public class EnseignantForm extends FormLayout {
	private Enseignant enseignant;

  TextField prenom = new TextField("Prenom");
  TextField nom = new TextField("Nom");
  EmailField email = new EmailField("Email");
  TextField contact = new TextField("Contact");
  MultiselectComboBox<Cours> cours = new MultiselectComboBox("Cours dispensés par ce professeur:");
  MultiselectComboBox<Classe> classes = new MultiselectComboBox("Classes enseignées par ce professeur:");
  MultiselectComboBox<Niveau> niveaux = new MultiselectComboBox("Niveaux affiliés à ce professeur:");
  /*List<Cours> cours;
  List<Classe> classes;
  List<Niveau> niveaux;*/
  //UnorderedList cours_ = new UnorderedList(), classes_ = new UnorderedList(), niveaux_ = new UnorderedList();
  //Details coursList, classesList, niveauxList;
  Binder<Enseignant> binder = new BeanValidationBinder<>(Enseignant.class);

  Button save = new Button("Sauvegarder");
  Button delete = new Button("Supprimer");
  Button close = new Button("Annuler");

  public EnseignantForm(List<Cours> cours_, List<Niveau> niveaux_, List<Classe> classes_) {
	
    addClassName("contact-form");
    binder.bindInstanceFields(this);
    binder.readBean(enseignant);
    
    cours.setItems(cours_);
    cours.setSizeFull();
    
    niveaux.setItems(niveaux_);
    niveaux.setSizeFull();
    
    classes.setItems(classes_);
    classes.setSizeFull();
    
    add(nom,
        prenom,
        contact,
        email,
        cours,
        classes,
        niveaux,
        createButtonsLayout()); 
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, enseignant)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));


    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void getLists() {
	  
	  
	 /* this.cours = new LinkedList<>();
	  this.classes = new LinkedList<>();
	  this.niveaux = new LinkedList<>();
		
	  for(Cours cour: cours)
		  cours_.add(new ListItem(cour.toString()));
	    
	  for(Classe classe: classes)
		  classes_.add(new ListItem(classe.toString()));
	  for(Niveau niveau: niveaux)
		  niveaux_.add(new ListItem(niveau.toString()));
	  
	  coursList = new Details("Cours Dispensés", cours_);
	  classesList = new Details("Classes Affiliées", classes_);
	  niveauxList = new Details("Niveaux Affiliés", niveaux_);
	    
	  return new VerticalLayout(coursList, classesList, niveauxList);*/
	    
  }
  public void setEnseignant(Enseignant enseignant) {
    this.enseignant = enseignant;
    binder.readBean(enseignant);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(enseignant);
      fireEvent(new SaveEvent(this, enseignant));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class EnseignantFormEvent extends ComponentEvent<EnseignantForm> {
    private Enseignant enseignant;

    protected EnseignantFormEvent(EnseignantForm source, Enseignant enseignant) {
      super(source, false);
      this.enseignant = enseignant;
    }

    public Enseignant getEnseignant() {
      return enseignant;
    }
  }

  public static class SaveEvent extends EnseignantFormEvent {
    SaveEvent(EnseignantForm source, Enseignant enseignant) {
      super(source, enseignant);
    }
  }

  public static class DeleteEvent extends EnseignantFormEvent {
    DeleteEvent(EnseignantForm source, Enseignant enseignant) {
      super(source, enseignant);
    }

  }

  public static class CloseEvent extends EnseignantFormEvent {
    CloseEvent(EnseignantForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}