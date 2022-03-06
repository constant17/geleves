package com.geleves.app.views.list;


import com.geleves.app.data.entity.Classe;
import com.geleves.app.data.entity.Cours;
import com.geleves.app.data.entity.Enseignant;
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
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.LinkedList;
import java.util.List;

public class EnseignantForm extends FormLayout {
	private Enseignant enseignant;

  TextField prenom = new TextField("Prenom");
  TextField nom = new TextField("Nom");
  EmailField email = new EmailField("Email");
  TextField contact = new TextField("Contact");
  List<Cours> cours_ = new LinkedList<>();
  List<Classe> classes_ = new LinkedList<>();
  UnorderedList cours = new UnorderedList(), classes = new UnorderedList();
  Details coursList, classesList;
  Binder<Enseignant> binder = new BeanValidationBinder<>(Enseignant.class);

  Button save = new Button("Sauvegarder");
  Button delete = new Button("Supprimer");
  Button close = new Button("Annuler");

  public EnseignantForm() {
    addClassName("contact-form");
    binder.bindInstanceFields(this);
    
    for(Cours cour: cours_)
    	cours.add(new ListItem(cour.toString()));
    
    for(Classe classe: classes_)
    	classes.add(new ListItem(classe.toString()));
    
    coursList = new Details("Cours Dispensés", cours);
    classesList = new Details("Classes Affiliées", classes);
    
    add(nom,
        prenom,
        contact,
        email,
        cours,
        classesList,
        coursList,
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