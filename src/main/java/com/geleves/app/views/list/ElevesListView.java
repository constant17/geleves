package com.geleves.app.views.list;

import com.geleves.app.data.entity.Eleve;
import com.geleves.app.data.service.GelevesService;
import com.geleves.app.views.MainLayout;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
@Route(value="", layout = MainLayout.class)
@PageTitle("Eleves | Geleves")
@PermitAll

public class ElevesListView extends VerticalLayout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid<Eleve> grid = new Grid<>(Eleve.class);
    TextField filterText = new TextField();
    EleveForm form;
    GelevesService service;
    ConfirmDialog warningDialog;

    public ElevesListView(GelevesService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new EleveForm(service.findAllParents(""));
        form.setWidth("25em");
        form.addListener(EleveForm.SaveEvent.class, this::saveEleve);
        form.addListener(EleveForm.DeleteEvent.class, this::deleteEleve);
        form.addListener(EleveForm.CloseEvent.class, e -> closeEditor());

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
            editEleve(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("nom", "prenom", "addresse", "dateDeNaissance", "niveau", "statut");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Rechercher un élève nom..");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEleveButton = new Button("Ajouter un élève");
        addEleveButton.addClickListener(click -> addEleve());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEleveButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveEleve(EleveForm.SaveEvent event) {
        service.saveEleve(event.getEleve());
        updateList();
        closeEditor();
        Notification notification = Notification.show("Changement enregistre!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void deleteEleve(EleveForm.DeleteEvent event) {
    	
    	warningDialog = new ConfirmDialog();
    	
    	warningDialog.setHeader("Confirmer La Suppression!");
    	warningDialog.setText(new Html("<p>Vous &ecirc;tes sur le point de supprimer <b>"+event.getEleve().toString()
    			+"</b> de la liste des &eacute;l&egrave;ves inscrits. <br>Voulez-vous vraiment supprimer cet &eacute;l&egrave;ve?</p>"));

    	warningDialog.setCancelable(true);
    	warningDialog.setCancelText("Annuler");

    	warningDialog.setConfirmText("Supprimer");
    	
    	warningDialog.addConfirmListener(e -> {
    		service.deleteEleve(event.getEleve());
            updateList();
            closeEditor();
            Notification notification = Notification.show("Eleve supprime!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            
    	});
    	warningDialog.open();
        
    }

    public void editEleve(Eleve eleve) {
        if (eleve == null) {
            closeEditor();
        } else {
            form.setEleve(eleve);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addEleve() {
        grid.asSingleSelect().clear();
        editEleve(new Eleve());
    }

    private void closeEditor() {
        form.setEleve(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllEleves(filterText.getValue()));
    }


}
