package com.geleves.app.views.list;

import com.geleves.app.data.entity.Enseignant;
import com.geleves.app.data.service.GelevesService;
import com.geleves.app.views.MainLayout;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value="enseignants", layout = MainLayout.class)
@PageTitle("Enseignants | Gélèves")
@PermitAll

public class EnseignantsListView extends VerticalLayout {
    Grid<Enseignant> grid = new Grid<>(Enseignant.class);
    TextField filterText = new TextField();
    EnseignantForm form;
    GelevesService service;
    ConfirmDialog warningDialog;

    public EnseignantsListView(GelevesService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new EnseignantForm();
        form.setWidth("25em");
        form.addListener(EnseignantForm.SaveEvent.class, this::saveEnseignant);
        form.addListener(EnseignantForm.DeleteEvent.class, this::deleteEnseignant);
        form.addListener(EnseignantForm.CloseEvent.class, e -> closeEditor());

        FlexLayout content = new FlexLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setFlexShrink(0, form);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event ->
            editEnseignant(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("nom", "prenom", "contact", "email", "nombreDeCours", "nombreDeClasses");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
    	filterText.setWidth("24em");
        filterText.setPlaceholder("Rechercher un enseignant par son nom..");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEleveButton = new Button("Ajouter un enseignant");
        addEleveButton.addClickListener(click -> addEnseignant());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEleveButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveEnseignant(EnseignantForm.SaveEvent event) {
        service.saveEnseignant(event.getEnseignant());
        updateList();
        closeEditor();
        Notification notification = Notification.show("Enseignant enregistré!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void deleteEnseignant(EnseignantForm.DeleteEvent event) {
    	warningDialog = new ConfirmDialog();
    	if(event.getEnseignant().getNombreDeClasses()!= 0 || event.getEnseignant().getNombreDeCours()!= 0) {
    		
    		warningDialog.setHeader("Attention!");
    		warningDialog.setText(
    		  new Html("<p>L'enseignant(e) que vous voulez supprimer, a au moins un cours ou une classe en charge."
    		  		+ "<br>Par consequent vous ne pouvez pas le/la supprimer.</p>")
    		);

    		warningDialog.setConfirmText("OK");
    		
    		warningDialog.open();
    		return;
    	}
    	
    	warningDialog.setHeader("Confirmer La Suppression!");
    	warningDialog.setText(new Html("<p>Vous &ecirc;tes sur le point de supprimer <b>"+event.getEnseignant().toString()
    			+"</b> de la liste des enseignants. <br>Voulez-vous vraiment supprimer le parent?</p>"));

    	warningDialog.setCancelable(true);
    	warningDialog.setCancelText("Annuler");

    	warningDialog.setConfirmText("Supprimer");
    	
    	warningDialog.addConfirmListener(e -> {
    		service.deleteEnseignant(event.getEnseignant());
            updateList();
            closeEditor();
            Notification notification = Notification.show("Enseignant supprimé!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            
    	});
    	warningDialog.open();
    	
        
    }

    public void editEnseignant(Enseignant enseignant) {
        if (enseignant == null) {
            closeEditor();
        } else {
            form.setEnseignant(enseignant);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addEnseignant() {
        grid.asSingleSelect().clear();
        editEnseignant(new Enseignant());
    }

    private void closeEditor() {
        form.setEnseignant(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllEnseignants(filterText.getValue()));
    }


}
