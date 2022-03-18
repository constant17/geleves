package com.geleves.app.views.list;

import com.geleves.app.data.entity.Parent;
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
@Route(value="parents", layout = MainLayout.class)
@PageTitle("Parents | Gélèves")
@PermitAll

public class ParentsListView extends VerticalLayout {
    Grid<Parent> grid = new Grid<>(Parent.class);
    TextField filterText = new TextField();
    ParentForm form;
    GelevesService service;
    ConfirmDialog warningDialog;

    public ParentsListView(GelevesService service) {
    	MainLayout.tabs.setSelectedIndex(MainLayout.PARENTS_TAB);
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new ParentForm(service.findAllEleves(""));
        form.setWidth("25em");
        form.addListener(ParentForm.SaveEvent.class, this::saveParent);
        form.addListener(ParentForm.DeleteEvent.class, this::deleteParent);
        form.addListener(ParentForm.CloseEvent.class, e -> closeEditor());

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
            editParent(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("nom", "prenom", "contact", "address", "email", "nombreDEnfants");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
    	filterText.setWidth("24em");
        filterText.setPlaceholder("Rechercher un parent d'élève par son nom..");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEleveButton = new Button("Ajouter un parent d'élève");
        addEleveButton.addClickListener(click -> addParent());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEleveButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveParent(ParentForm.SaveEvent event) {
        service.saveParent(event.getParent());
        updateList();
        closeEditor();
        Notification notification = Notification.show("Parent enregistré!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void deleteParent(ParentForm.DeleteEvent event) {
    	warningDialog = new ConfirmDialog();
    	if(event.getParent().getNombreDEnfants() != 0) {
    		
    		warningDialog.setHeader("Attention!");
    		warningDialog.setText(
    		  new Html("<p>Le parent d'élève que vous voulez supprimer, a au moins un enfant inscrit et actif. "
    		  		+ "<br>Par consequent vous ne pouvez pas le/la supprimer.</p>")
    		);

    		warningDialog.setConfirmText("OK");
    		
    		warningDialog.open();
    		return;
    	}
    	
    	warningDialog.setHeader("Confirmer La Suppression!");
    	warningDialog.setText(new Html("<p>Vous &ecirc;tes sur le point de supprimer <b>"+event.getParent().toString()
    			+"</b> de la liste des parents d'&eacute;l&egrave;ves inscrits. <br>Voulez-vous vraiment supprimer le parent?</p>"));

    	warningDialog.setCancelable(true);
    	warningDialog.setCancelText("Annuler");

    	warningDialog.setConfirmText("Supprimer");
    	
    	warningDialog.addConfirmListener(e -> {
    		service.deleteParent(event.getParent());
            updateList();
            closeEditor();
            Notification notification = Notification.show("Parent supprimé!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            
    	});
    	warningDialog.open();
    	
        
    }

    public void editParent(Parent parent) {
        if (parent == null) {
            closeEditor();
        } else {
            form.setParent(parent);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addParent() {
        grid.asSingleSelect().clear();
        editParent(new Parent());
    }

    private void closeEditor() {
        form.setParent(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllParents(filterText.getValue()));
    }


}
